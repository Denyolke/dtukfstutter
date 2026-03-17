package com.example.stutter.ui;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.stutter.MainActivity;
import com.example.stutter.R;
import com.example.stutter.firebase.FirebaseAuthManager;
import com.example.stutter.model.UserProfile;
import com.example.stutter.util.ThemeManager;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseUser;

public class SettingsFragment extends Fragment {

    public SettingsFragment() {
        super(R.layout.fragment_settings);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        FirebaseAuthManager authManager = FirebaseAuthManager.getInstance();
        FirebaseUser user = authManager.getCurrentUser();

        View           btnBack          = v.findViewById(R.id.btnSettingsBack);
        LinearLayout   rowPfp           = v.findViewById(R.id.rowProfilePicture);
        LinearLayout   rowUsername      = v.findViewById(R.id.rowChangeUsername);
        android.widget.TextView tvCurrentUsername = v.findViewById(R.id.tvCurrentUsername);
        SwitchMaterial switchDark       = v.findViewById(R.id.switchDarkMode);

        // ── Back ─────────────────────────────────────────────────────────────
        btnBack.setOnClickListener(x ->
                ((MainActivity) requireActivity()).replace(new ProfileFragment(), false));

        // ── Set current username label ────────────────────────────────────────
        if (user != null) {
            authManager.getUserProfile(user.getUid(), new FirebaseAuthManager.OnUserProfileListener() {
                @Override
                public void onSuccess(UserProfile profile) {
                    if (isAdded()) tvCurrentUsername.setText(profile.username);
                }
                @Override
                public void onError(String errorMessage) {
                    if (isAdded()) tvCurrentUsername.setText("—");
                }
            });
        }

        // ── Dark mode switch ──────────────────────────────────────────────────
        switchDark.setChecked(ThemeManager.isDarkMode(requireContext()));
        switchDark.setOnCheckedChangeListener((btn, isChecked) -> {
            ThemeManager.setDarkMode(requireContext(), isChecked);
            // AppCompatDelegate recreates the activity automatically
        });

        // ── Change profile picture ────────────────────────────────────────────
        rowPfp.setOnClickListener(x -> showProfilePictureDialog(authManager, user));

        // ── Change username ───────────────────────────────────────────────────
        rowUsername.setOnClickListener(x -> showChangeUsernameDialog(authManager, user, tvCurrentUsername));
    }

    // ── Profile picture dialog (reuses existing dialog layout) ───────────────

    private void showProfilePictureDialog(FirebaseAuthManager authManager, FirebaseUser user) {
        if (user == null) return;

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_profile_picture, null);

        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setTitle("Choose Profile Picture")
                .setView(dialogView)
                .setNegativeButton("Cancel", (d, w) -> d.dismiss())
                .create();

        for (int i = 1; i <= 6; i++) {
            final int pfp = i;
            int resId = getResources().getIdentifier("ivPfp" + i, "id", requireContext().getPackageName());
            View img = dialogView.findViewById(resId);
            if (img != null) {
                img.setOnClickListener(v -> {
                    authManager.updateUserProfilePicture(
                            user.getUid(), pfp,
                            new FirebaseAuthManager.OnAuthListener() {
                                @Override
                                public void onSuccess(String message) {
                                    if (!isAdded()) return;
                                    Toast.makeText(requireContext(),
                                            "Profile picture updated", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                                @Override
                                public void onError(String errorMessage) {
                                    if (!isAdded()) return;
                                    Toast.makeText(requireContext(),
                                            "Failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            });
                });
            }
        }

        dialog.show();
    }

    // ── Change username dialog ────────────────────────────────────────────────

    private void showChangeUsernameDialog(FirebaseAuthManager authManager,
                                          FirebaseUser user,
                                          android.widget.TextView tvCurrentUsername) {
        if (user == null) return;

        EditText input = new EditText(requireContext());
        input.setHint("New username");
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        int pad = (int) (16 * getResources().getDisplayMetrics().density);
        input.setPadding(pad, pad, pad, pad);

        new AlertDialog.Builder(requireContext())
                .setTitle("Change Username")
                .setView(input)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newName = input.getText().toString().trim();
                    if (newName.isEmpty()) {
                        Toast.makeText(requireContext(),
                                "Username cannot be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    authManager.updateUsername(
                            user.getUid(), newName,
                            new FirebaseAuthManager.OnAuthListener() {
                                @Override
                                public void onSuccess(String message) {
                                    if (!isAdded()) return;
                                    tvCurrentUsername.setText(newName);
                                    Toast.makeText(requireContext(),
                                            "Username updated!", Toast.LENGTH_SHORT).show();
                                }
                                @Override
                                public void onError(String errorMessage) {
                                    if (!isAdded()) return;
                                    Toast.makeText(requireContext(),
                                            "Failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            });
                })
                .setNegativeButton("Cancel", (d, w) -> d.dismiss())
                .show();
    }
}
