package com.example.stutter.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.stutter.R;
import com.example.stutter.firebase.FirebaseAuthManager;
import com.example.stutter.model.UserProfile;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseUser;

public class ShopFragment extends Fragment {

    // IDs match fragment_shop.xml exactly
    private TextView       tvCoins;         // R.id.tvShopCoins
    private TextView       tvBoosterOwned;  // R.id.tvBoosterOwned
    private LinearLayout   bannerBooster;   // R.id.bannerActiveBooster
    private MaterialButton btnBuy;          // R.id.btnBuyDoubler
    private MaterialButton btnUse;          // R.id.btnUseDoubler
    private ProgressBar    pbLoading;       // R.id.pbShopLoading
    private View           scrollShop;      // R.id.scrollShop

    private FirebaseAuthManager authManager;
    private String uid;

    public ShopFragment() {
        super(R.layout.fragment_shop);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        tvCoins       = v.findViewById(R.id.tvShopCoins);
        tvBoosterOwned = v.findViewById(R.id.tvBoosterOwned);
        bannerBooster  = v.findViewById(R.id.bannerActiveBooster);
        btnBuy         = v.findViewById(R.id.btnBuyDoubler);
        btnUse         = v.findViewById(R.id.btnUseDoubler);
        pbLoading      = v.findViewById(R.id.pbShopLoading);
        scrollShop     = v.findViewById(R.id.scrollShop);

        authManager = FirebaseAuthManager.getInstance();
        FirebaseUser user = authManager.getCurrentUser();

        if (user == null) {
            pbLoading.setVisibility(View.GONE);
            Toast.makeText(requireContext(), "Please log in.", Toast.LENGTH_SHORT).show();
            return;
        }

        uid = user.getUid();
        loadProfile();

        btnBuy.setOnClickListener(x -> handleBuy());
        btnUse.setOnClickListener(x -> handleActivate());
    }

    private void loadProfile() {
        pbLoading.setVisibility(View.VISIBLE);
        scrollShop.setVisibility(View.GONE);

        authManager.getUserProfile(uid, new FirebaseAuthManager.OnUserProfileListener() {
            @Override
            public void onSuccess(UserProfile profile) {
                if (!isAdded()) return;
                pbLoading.setVisibility(View.GONE);
                scrollShop.setVisibility(View.VISIBLE);
                bindProfile(profile);
            }

            @Override
            public void onError(String errorMessage) {
                if (!isAdded()) return;
                pbLoading.setVisibility(View.GONE);
                Toast.makeText(requireContext(),
                        "Error loading shop: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bindProfile(UserProfile profile) {
        tvCoins.setText(String.valueOf(profile.coins));
        tvBoosterOwned.setText("Owned: " + profile.xpBoosterCount);

        boolean boosterActive = profile.activeXpMultiplier >= 2.0;
        bannerBooster.setVisibility(boosterActive ? View.VISIBLE : View.GONE);

        btnUse.setEnabled(profile.xpBoosterCount > 0 && !boosterActive);
        btnUse.setAlpha(btnUse.isEnabled() ? 1f : 0.5f);
    }

    //Handle Power-Ups

    private void handleBuy() {
        btnBuy.setEnabled(false);
        authManager.buyXpDoubler(uid, new FirebaseAuthManager.OnAuthListener() {
            @Override
            public void onSuccess(String message) {
                if (!isAdded()) return;
                btnBuy.setEnabled(true);
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                loadProfile();
            }

            @Override
            public void onError(String errorMessage) {
                if (!isAdded()) return;
                btnBuy.setEnabled(true);
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }


    private void handleActivate() {
        btnUse.setEnabled(false);
        authManager.activateXpDoubler(uid, new FirebaseAuthManager.OnAuthListener() {
            @Override
            public void onSuccess(String message) {
                if (!isAdded()) return;
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
                loadProfile();
            }

            @Override
            public void onError(String errorMessage) {
                if (!isAdded()) return;
                btnUse.setEnabled(true);
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}