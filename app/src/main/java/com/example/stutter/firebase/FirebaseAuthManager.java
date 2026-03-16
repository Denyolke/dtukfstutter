package com.example.stutter.firebase;

import com.example.stutter.model.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

public class FirebaseAuthManager {

    private static FirebaseAuthManager instance;
    private final FirebaseAuth mAuth;
    private final FirebaseFirestore mFirestore;

    private FirebaseAuthManager() {
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
    }

    public static FirebaseAuthManager getInstance() {
        if (instance == null) {
            instance = new FirebaseAuthManager();
        }
        return instance;
    }

    /**
     * Register a new user with email and password
     */
    public void registerUser(String email, String password, String username, OnAuthListener listener) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            createUserProfile(user.getUid(), email, username, listener);
                        }
                    } else {
                        String errorMsg = task.getException() != null ? 
                                task.getException().getMessage() : "Registration failed";
                        listener.onError(errorMsg);
                    }
                });
    }

    /**
     * Login user with email and password
     */
    public void loginUser(String email, String password, OnAuthListener listener) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.onSuccess("Login successful");
                    } else {
                        String errorMsg = task.getException() != null ? 
                                task.getException().getMessage() : "Login failed";
                        listener.onError(errorMsg);
                    }
                });
    }

    /**
     * Logout current user
     */
    public void logout() {
        mAuth.signOut();
    }

    /**
     * Get current logged in user
     */
    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    /**
     * Check if user is logged in
     */
    public boolean isUserLoggedIn() {
        return mAuth.getCurrentUser() != null;
    }

    /**
     * Create user profile in Firestore
     */
    private void createUserProfile(String userId, String email, String username, OnAuthListener listener) {
        UserProfile profile = new UserProfile(userId, username, email, 0, 0, 0);
        profile.lastActivityDate = System.currentTimeMillis(); // Set to now
        
        mFirestore.collection("users").document(userId)
                .set(profile)
                .addOnSuccessListener(aVoid -> listener.onSuccess("User profile created"))
                .addOnFailureListener(e -> listener.onError(e.getMessage()));
    }

    /**
     * Get user profile from Firestore
     */
    public void getUserProfile(String userId, OnUserProfileListener listener) {
        mFirestore.collection("users").document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            UserProfile profile = document.toObject(UserProfile.class);
                            listener.onSuccess(profile);
                        } else {
                            listener.onError("User profile not found");
                        }
                    } else {
                        listener.onError(task.getException() != null ? 
                                task.getException().getMessage() : "Failed to fetch profile");
                    }
                });
    }

    public void updateUserProfilePicture(String userId, int pfp, OnAuthListener listener) {
        mFirestore.collection("users").document(userId)
                .update("pfp", pfp)
                .addOnSuccessListener(unused -> listener.onSuccess("Profile picture updated"))
                .addOnFailureListener(e -> listener.onError(e.getMessage()));
    }

    /**
     * Update user stats: add XP, update streak, increment completed lessons
     * Called when user completes a quiz successfully
     */
    public void updateUserStatsOnQuizCompletion(String userId, int xpToAdd) {
        mFirestore.collection("users").document(userId).get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        // Get current values
                        Long currentXPObj = doc.getLong("totalXP");
                        int currentXP = currentXPObj != null ? currentXPObj.intValue() : 0;
                        
                        Long completedObj = doc.getLong("completedLessons");
                        int currentCompleted = completedObj != null ? completedObj.intValue() : 0;
                        
                        Long lastActivityObj = doc.getLong("lastActivityDate");
                        long lastActivityDate = lastActivityObj != null ? lastActivityObj : 0;
                        
                        Long streakObj = doc.getLong("streak");
                        int currentStreak = streakObj != null ? streakObj.intValue() : 0;
                        
                        // Get current time
                        long currentTime = System.currentTimeMillis();
                        
                        // Calculate streak
                        int newStreak = calculateStreak(lastActivityDate, currentTime, currentStreak);
                        
                        // Update all stats
                        mFirestore.collection("users").document(userId)
                                .update(
                                        "totalXP", currentXP + xpToAdd,
                                        "completedLessons", currentCompleted + 1,
                                        "lastActivityDate", currentTime,
                                        "streak", newStreak
                                )
                                .addOnFailureListener(e -> {
                                    System.err.println("Error updating stats: " + e.getMessage());
                                });
                    }
                });
    }

    /**
     * Calculate streak based on last activity date
     * 
     * Rules:
     * - If last activity was today (same calendar day): streak stays same
     * - If last activity was yesterday: streak increases by 1
     * - If last activity was >24 hours ago: streak resets to 1
     */
    private int calculateStreak(long lastActivityDate, long currentTime, int currentStreak) {
        if (lastActivityDate == 0) {
            // First activity ever
            return 1;
        }
        
        // Get difference in milliseconds
        long diffMs = currentTime - lastActivityDate;
        
        // 24 hours in milliseconds
        long twentyFourHours = 24 * 60 * 60 * 1000L;
        
        if (diffMs < twentyFourHours) {
            // Activity within 24 hours (same day or less than 24 hours)
            // Streak stays the same (already completed today)
            return currentStreak;
        } else if (diffMs < (2 * twentyFourHours)) {
            // Activity between 24-48 hours ago (yesterday)
            // Increment streak
            return currentStreak + 1;
        } else {
            // More than 48 hours ago (missed a day)
            // Reset streak to 1
            return 1;
        }
    }

    /**
     * Callback interface for authentication results
     */
    public interface OnAuthListener {
        void onSuccess(String message);
        void onError(String errorMessage);
    }

    /**
     * Callback interface for user profile retrieval
     */
    public interface OnUserProfileListener {
        void onSuccess(UserProfile profile);
        void onError(String errorMessage);
    }
}
