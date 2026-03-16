package com.example.stutter;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.stutter.firebase.FirebaseAuthManager;
import com.example.stutter.ui.AppViewModel;
import com.example.stutter.ui.HomeFragment;
import com.example.stutter.ui.LeaderboardFragment;
import com.example.stutter.ui.ProfileFragment;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private AppViewModel vm;

    private LinearLayout btnSettingsContainer;
    private LinearLayout btnHomeContainer;
    private LinearLayout btnProfileContainer;

    private ImageView ivLeaderboard, ivHome, ivProfile;
    private TextView tvLeaderboardLabel, tvHomeLabel, tvProfileLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAuthManager authManager = FirebaseAuthManager.getInstance();
        FirebaseUser user = authManager.getCurrentUser();

        if (user == null) {
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        vm = new ViewModelProvider(this).get(AppViewModel.class);
        vm.load();

        btnSettingsContainer = findViewById(R.id.btnSettingsContainer);
        btnHomeContainer = findViewById(R.id.btnHomeContainer);
        btnProfileContainer = findViewById(R.id.btnProfileContainer);

        ivLeaderboard = findViewById(R.id.ivLeaderboard);
        ivHome = findViewById(R.id.ivHome);
        ivProfile = findViewById(R.id.ivProfile);

        tvLeaderboardLabel = findViewById(R.id.tvLeaderboardLabel);
        tvHomeLabel = findViewById(R.id.tvHomeLabel);
        tvProfileLabel = findViewById(R.id.tvProfileLabel);

        btnSettingsContainer.setOnClickListener(v -> {
            setSelectedTab(Tab.LEADERBOARD);
            replace(new LeaderboardFragment(), false);
        });

        btnHomeContainer.setOnClickListener(v -> {
            setSelectedTab(Tab.HOME);
            replace(new HomeFragment(), false);
        });

        btnProfileContainer.setOnClickListener(v -> {
            setSelectedTab(Tab.PROFILE);
            replace(new ProfileFragment(), false);
        });

        if (savedInstanceState == null) {
            setSelectedTab(Tab.HOME);
            replace(new HomeFragment(), false);
        }
    }

    public void replace(Fragment f, boolean addToBackStack) {
        if (f == null) return;

        var tx = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, f);

        if (addToBackStack) tx.addToBackStack(null);
        tx.commit();
    }

    private enum Tab {
        LEADERBOARD, HOME, PROFILE
    }

    private void setSelectedTab(Tab selectedTab) {
        resetAllTabs();

        switch (selectedTab) {
            case LEADERBOARD:
                btnSettingsContainer.setBackgroundResource(R.drawable.nav_item_active_bg);
                ivLeaderboard.setColorFilter(0xFF2563EB);
                tvLeaderboardLabel.setTextColor(0xFF2563EB);
                break;

            case HOME:
                btnHomeContainer.setBackgroundResource(R.drawable.nav_item_active_bg);
                ivHome.setColorFilter(0xFF2563EB);
                tvHomeLabel.setTextColor(0xFF2563EB);
                break;

            case PROFILE:
                btnProfileContainer.setBackgroundResource(R.drawable.nav_item_active_bg);
                ivProfile.setColorFilter(0xFF2563EB);
                tvProfileLabel.setTextColor(0xFF2563EB);
                break;
        }
    }

    private void resetAllTabs() {
        btnSettingsContainer.setBackgroundResource(R.drawable.nav_item_inactive_bg);
        btnHomeContainer.setBackgroundResource(R.drawable.nav_item_inactive_bg);
        btnProfileContainer.setBackgroundResource(R.drawable.nav_item_inactive_bg);

        ivLeaderboard.setColorFilter(0xFF9CA3AF);
        ivHome.setColorFilter(0xFF9CA3AF);
        ivProfile.setColorFilter(0xFF9CA3AF);

        tvLeaderboardLabel.setTextColor(0xFF9CA3AF);
        tvHomeLabel.setTextColor(0xFF9CA3AF);
        tvProfileLabel.setTextColor(0xFF9CA3AF);
    }
}