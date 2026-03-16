package com.example.stutter.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.stutter.R;
import com.example.stutter.firebase.FirebaseAuthManager;
import com.example.stutter.model.UserProfile;
import com.google.firebase.auth.FirebaseUser;

public class ShopFragment extends Fragment {

    public ShopFragment() {
        super(R.layout.fragment_shop);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        FirebaseAuthManager authManager = FirebaseAuthManager.getInstance();
        FirebaseUser user = authManager.getCurrentUser();

        if (user == null) return;

        TextView tvCoins          = v.findViewById(R.id.tvCoins);
        TextView tvBoosterCount   = v.findViewById(R.id.tvBoosterCount);
        TextView tvBoosterStatus  = v.findViewById(R.id.tvBoosterStatus);
        Button   btnBuy           = v.findViewById(R.id.btnBuyDoubler);
        Button   btnActivate      = v.findViewById(R.id.btnActivateDoubler);

        // Load fresh data from Firestore
        loadProfile(user.getUid(), authManager, tvCoins, tvBoosterCount, tvBoosterStatus);

        // ── Buy XP Doubler ──────────────────────────────────────────────────
        btnBuy.setOnClickListener(x -> {
            btnBuy.setEnabled(false);
            authManager.buyXpDoubler(user.getUid(), new FirebaseAuthManager.OnAuthListener() {
                @Override public void onSuccess(String msg) {
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
                    loadProfile(user.getUid(), authManager,
                            tvCoins, tvBoosterCount, tvBoosterStatus);
                    btnBuy.setEnabled(true);
                }
                @Override public void onError(String err) {
                    Toast.makeText(requireContext(), err, Toast.LENGTH_SHORT).show();
                    btnBuy.setEnabled(true);
                }
            });
        });

        // ── Activate XP Doubler ─────────────────────────────────────────────
        btnActivate.setOnClickListener(x -> {
            btnActivate.setEnabled(false);
            authManager.activateXpDoubler(user.getUid(), new FirebaseAuthManager.OnAuthListener() {
                @Override public void onSuccess(String msg) {
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
                    loadProfile(user.getUid(), authManager,
                            tvCoins, tvBoosterCount, tvBoosterStatus);
                    btnActivate.setEnabled(true);
                }
                @Override public void onError(String err) {
                    Toast.makeText(requireContext(), err, Toast.LENGTH_SHORT).show();
                    btnActivate.setEnabled(true);
                }
            });
        });
    }

    private void loadProfile(String uid, FirebaseAuthManager auth,
                             TextView tvCoins, TextView tvBoosterCount,
                             TextView tvBoosterStatus) {
        auth.getUserProfile(uid, new FirebaseAuthManager.OnUserProfileListener() {
            @Override public void onSuccess(UserProfile p) {
                tvCoins.setText("🪙 " + p.coins + " Coins");
                tvBoosterCount.setText("x" + p.xpBoosterCount);
                boolean active = p.activeXpMultiplier > 1.0;
                tvBoosterStatus.setText(active ? "⚡ Active — next quiz XP is doubled!" : "Inactive");
                tvBoosterStatus.setTextColor(active ? 0xFF10B981 : 0xFF6B7280);
            }
            @Override public void onError(String err) {
                Toast.makeText(requireContext(),
                        "Failed to load shop: " + err, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
