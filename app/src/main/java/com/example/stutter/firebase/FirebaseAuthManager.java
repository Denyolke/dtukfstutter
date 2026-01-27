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

    /**
     * Update user stats in Firestore
     */
    public void updateUserStats(String userId, int newXP, int newStreak, int completedLessons) {
        mFirestore.collection("users").document(userId)
                .update(
                        "totalXP", newXP,
                        "streak", newStreak,
                        "completedLessons", completedLessons
                )
                .addOnFailureListener(e -> {
                    System.err.println("Error updating stats: " + e.getMessage());
                });
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
