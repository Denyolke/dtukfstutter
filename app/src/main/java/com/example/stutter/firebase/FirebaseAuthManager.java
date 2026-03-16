package com.example.stutter.firebase;

import com.example.stutter.model.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class FirebaseAuthManager {

    private static FirebaseAuthManager instance;
    private final FirebaseAuth mAuth;
    private final FirebaseFirestore mFirestore;

    public static final int COINS_PER_QUIZ  = 10;
    public static final int XP_DOUBLER_COST = 100;

    // CET timezone — all streak dates use this
    private static final TimeZone CET = TimeZone.getTimeZone("Europe/Paris");

    private FirebaseAuthManager() {
        mAuth      = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
    }

    public static FirebaseAuthManager getInstance() {
        if (instance == null) instance = new FirebaseAuthManager();
        return instance;
    }

    // ── Auth ─────────────────────────────────────────────────────────────────

    public void registerUser(String email, String password, String username,
                             OnAuthListener listener) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null)
                            createUserProfile(user.getUid(), email, username, listener);
                    } else {
                        listener.onError(task.getException() != null
                                ? task.getException().getMessage() : "Registration failed");
                    }
                });
    }

    public void loginUser(String email, String password, OnAuthListener listener) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) listener.onSuccess("Login successful");
                    else listener.onError(task.getException() != null
                            ? task.getException().getMessage() : "Login failed");
                });
    }

    public void logout()                 { mAuth.signOut(); }
    public FirebaseUser getCurrentUser() { return mAuth.getCurrentUser(); }
    public boolean isUserLoggedIn()      { return mAuth.getCurrentUser() != null; }

    // ── Profile ──────────────────────────────────────────────────────────────

    private void createUserProfile(String userId, String email, String username,
                                   OnAuthListener listener) {
        UserProfile profile             = new UserProfile(userId, username, email, 0, 0, 0);
        profile.lastActivityDate        = System.currentTimeMillis();
        profile.coins                   = 0;
        profile.xpBoosterCount          = 0;
        profile.activeXpMultiplier      = 1.0;
        profile.lastQuestDate           = "";
        // Mark today as covered so the first quiz starts streak = 1
        profile.lastCoveredDate         = todayStringCET();

        mFirestore.collection("users").document(userId)
                .set(profile)
                .addOnSuccessListener(v -> listener.onSuccess("User profile created"))
                .addOnFailureListener(e -> listener.onError(e.getMessage()));
    }

    public void getUserProfile(String userId, OnUserProfileListener listener) {
        mFirestore.collection("users").document(userId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        if (doc.exists()) listener.onSuccess(doc.toObject(UserProfile.class));
                        else              listener.onError("User profile not found");
                    } else {
                        listener.onError(task.getException() != null
                                ? task.getException().getMessage() : "Failed to fetch profile");
                    }
                });
    }

    public void updateUserProfilePicture(String userId, int pfp, OnAuthListener listener) {
        mFirestore.collection("users").document(userId)
                .update("pfp", pfp)
                .addOnSuccessListener(u -> listener.onSuccess("Profile picture updated"))
                .addOnFailureListener(e -> listener.onError(e.getMessage()));
    }

    // ── Quiz completion: XP + streak + coins ─────────────────────────────────

    /**
     * Streak rules (all dates in CET):
     *
     *  lastCoveredDate == today
     *      → Already completed a quiz today. Streak unchanged.
     *
     *  lastCoveredDate == yesterday
     *      → Active yesterday, active again today → streak + 1
     *
     *  lastCoveredDate is older OR empty (new account)
     *      → Missed at least one midnight → streak resets to 1
     *
     * lastCoveredDate is always updated to today after any quiz.
     */
    public void updateUserStatsOnQuizCompletion(String userId, int baseXpToAdd,
                                                OnCoinsUpdateListener listener) {
        mFirestore.collection("users").document(userId).get()
                .addOnSuccessListener(doc -> {
                    if (!doc.exists()) return;

                    int    currentXP        = safeInt(doc, "totalXP");
                    int    currentCompleted = safeInt(doc, "completedLessons");
                    int    currentCoins     = safeInt(doc, "coins");
                    int    currentStreak    = safeInt(doc, "streak");
                    String lastCovered      = safeStr(doc, "lastCoveredDate");

                    Double mult       = doc.getDouble("activeXpMultiplier");
                    double multiplier = (mult != null && mult >= 1.0) ? mult : 1.0;

                    String today     = todayStringCET();
                    String yesterday = yesterdayStringCET();

                    // Streak decision
                    int newStreak;
                    if (today.equals(lastCovered)) {
                        // Already covered today — no change
                        newStreak = Math.max(currentStreak, 1);
                    } else if (yesterday.equals(lastCovered)) {
                        // Covered yesterday, completing today → extend streak
                        newStreak = currentStreak + 1;
                    } else {
                        // Gap detected — reset
                        newStreak = 1;
                    }

                    int actualXP    = (int) (baseXpToAdd * multiplier);
                    int coinsEarned = COINS_PER_QUIZ;
                    int newCoins    = currentCoins + coinsEarned;

                    mFirestore.collection("users").document(userId)
                            .update(
                                    "totalXP",            currentXP + actualXP,
                                    "completedLessons",   currentCompleted + 1,
                                    "lastActivityDate",   System.currentTimeMillis(),
                                    "streak",             newStreak,
                                    "coins",              newCoins,
                                    "lastCoveredDate",    today,
                                    "activeXpMultiplier", 1.0
                            )
                            .addOnSuccessListener(u -> {
                                if (listener != null)
                                    listener.onSuccess(newCoins, coinsEarned);
                            })
                            .addOnFailureListener(e -> {
                                if (listener != null) listener.onError(e.getMessage());
                            });
                });
    }

    /** Backward-compatible overload. */
    public void updateUserStatsOnQuizCompletion(String userId, int xpToAdd) {
        updateUserStatsOnQuizCompletion(userId, xpToAdd, null);
    }

    // ── Economy ──────────────────────────────────────────────────────────────

    public void buyXpDoubler(String userId, OnAuthListener listener) {
        mFirestore.collection("users").document(userId).get()
                .addOnSuccessListener(doc -> {
                    if (!doc.exists()) { listener.onError("Profile not found"); return; }
                    int coins   = safeInt(doc, "coins");
                    int booster = safeInt(doc, "xpBoosterCount");
                    if (coins < XP_DOUBLER_COST) {
                        listener.onError("Not enough coins! You need " + XP_DOUBLER_COST + " coins.");
                        return;
                    }
                    mFirestore.collection("users").document(userId)
                            .update("coins", coins - XP_DOUBLER_COST,
                                    "xpBoosterCount", booster + 1)
                            .addOnSuccessListener(u -> listener.onSuccess("XP Doubler purchased!"))
                            .addOnFailureListener(e -> listener.onError(e.getMessage()));
                })
                .addOnFailureListener(e -> listener.onError(e.getMessage()));
    }

    public void activateXpDoubler(String userId, OnAuthListener listener) {
        mFirestore.collection("users").document(userId).get()
                .addOnSuccessListener(doc -> {
                    if (!doc.exists()) { listener.onError("Profile not found"); return; }
                    int booster = safeInt(doc, "xpBoosterCount");
                    if (booster <= 0) {
                        listener.onError("No XP Doublers available!");
                        return;
                    }
                    mFirestore.collection("users").document(userId)
                            .update("xpBoosterCount",     booster - 1,
                                    "activeXpMultiplier", 2.0)
                            .addOnSuccessListener(u -> listener.onSuccess(
                                    "XP Doubler activated! Next quiz gives 2× XP."))
                            .addOnFailureListener(e -> listener.onError(e.getMessage()));
                })
                .addOnFailureListener(e -> listener.onError(e.getMessage()));
    }

    public void awardCoins(String userId, int amount, OnAuthListener listener) {
        mFirestore.collection("users").document(userId).get()
                .addOnSuccessListener(doc -> {
                    if (!doc.exists()) return;
                    int current = safeInt(doc, "coins");
                    mFirestore.collection("users").document(userId)
                            .update("coins", current + amount)
                            .addOnSuccessListener(u -> {
                                if (listener != null)
                                    listener.onSuccess("+" + amount + " coins awarded");
                            })
                            .addOnFailureListener(e -> {
                                if (listener != null) listener.onError(e.getMessage());
                            });
                });
    }

    // ── CET date helpers ─────────────────────────────────────────────────────

    public static String todayStringCET() {
        return formatDateCET(new Date());
    }

    public static String yesterdayStringCET() {
        Calendar cal = Calendar.getInstance(CET);
        cal.add(Calendar.DATE, -1);
        return formatDateCET(cal.getTime());
    }

    private static String formatDateCET(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        sdf.setTimeZone(CET);
        return sdf.format(date);
    }

    // ── Null-safe Firestore helpers ──────────────────────────────────────────

    private static int safeInt(DocumentSnapshot doc, String field) {
        Long v = doc.getLong(field);
        return v != null ? v.intValue() : 0;
    }

    private static String safeStr(DocumentSnapshot doc, String field) {
        String v = doc.getString(field);
        return v != null ? v : "";
    }

    // ── Interfaces ───────────────────────────────────────────────────────────

    public interface OnAuthListener {
        void onSuccess(String message);
        void onError(String errorMessage);
    }

    public interface OnUserProfileListener {
        void onSuccess(UserProfile profile);
        void onError(String errorMessage);
    }

    public interface OnCoinsUpdateListener {
        void onSuccess(int newTotal, int coinsEarned);
        void onError(String error);
    }
}
