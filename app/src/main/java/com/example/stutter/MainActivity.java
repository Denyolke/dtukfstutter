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
import com.example.stutter.ui.QuestsFragment;
import com.example.stutter.ui.ShopFragment;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private AppViewModel vm;

    // Nav containers
    private LinearLayout btnLeaderboardContainer;
    private LinearLayout btnShopContainer;
    private LinearLayout btnHomeContainer;
    private LinearLayout btnQuestsContainer;
    private LinearLayout btnProfileContainer;

    // Nav icons
    private ImageView ivLeaderboard, ivShop, ivHome, ivQuests, ivProfile;

    // Nav labels
    private TextView tvLeaderboardLabel, tvShopLabel, tvHomeLabel,
                     tvQuestsLabel, tvProfileLabel;

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

        // Bind containers
        btnLeaderboardContainer = findViewById(R.id.btnLeaderboardContainer);
        btnShopContainer        = findViewById(R.id.btnShopContainer);
        btnHomeContainer        = findViewById(R.id.btnHomeContainer);
        btnQuestsContainer      = findViewById(R.id.btnQuestsContainer);
        btnProfileContainer     = findViewById(R.id.btnProfileContainer);

        // Bind icons
        ivLeaderboard = findViewById(R.id.ivLeaderboard);
        ivShop        = findViewById(R.id.ivShop);
        ivHome        = findViewById(R.id.ivHome);
        ivQuests      = findViewById(R.id.ivQuests);
        ivProfile     = findViewById(R.id.ivProfile);

        // Bind labels
        tvLeaderboardLabel = findViewById(R.id.tvLeaderboardLabel);
        tvShopLabel        = findViewById(R.id.tvShopLabel);
        tvHomeLabel        = findViewById(R.id.tvHomeLabel);
        tvQuestsLabel      = findViewById(R.id.tvQuestsLabel);
        tvProfileLabel     = findViewById(R.id.tvProfileLabel);

        // Click listeners
        btnLeaderboardContainer.setOnClickListener(v -> navigateTo(Tab.LEADERBOARD));
        btnShopContainer.setOnClickListener(v        -> navigateTo(Tab.SHOP));
        btnHomeContainer.setOnClickListener(v        -> navigateTo(Tab.HOME));
        btnQuestsContainer.setOnClickListener(v      -> navigateTo(Tab.QUESTS));
        btnProfileContainer.setOnClickListener(v     -> navigateTo(Tab.PROFILE));

        if (savedInstanceState == null) {
            navigateTo(Tab.HOME);
        }
    }

    private void navigateTo(Tab tab) {
        setSelectedTab(tab);
        switch (tab) {
            case LEADERBOARD: replace(new LeaderboardFragment(), false); break;
            case SHOP:        replace(new ShopFragment(),        false); break;
            case HOME:        replace(new HomeFragment(),        false); break;
            case QUESTS:      replace(new QuestsFragment(),      false); break;
            case PROFILE:     replace(new ProfileFragment(),     false); break;
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

    // ── Tab highlight ─────────────────────────────────────────────────────────

    private enum Tab { LEADERBOARD, SHOP, HOME, QUESTS, PROFILE }

    private void setSelectedTab(Tab selected) {
        resetAllTabs();

        LinearLayout activeContainer;
        ImageView    activeIcon;
        TextView     activeLabel;

        switch (selected) {
            case LEADERBOARD:
                activeContainer = btnLeaderboardContainer;
                activeIcon      = ivLeaderboard;
                activeLabel     = tvLeaderboardLabel;
                break;
            case SHOP:
                activeContainer = btnShopContainer;
                activeIcon      = ivShop;
                activeLabel     = tvShopLabel;
                break;
            case QUESTS:
                activeContainer = btnQuestsContainer;
                activeIcon      = ivQuests;
                activeLabel     = tvQuestsLabel;
                break;
            case PROFILE:
                activeContainer = btnProfileContainer;
                activeIcon      = ivProfile;
                activeLabel     = tvProfileLabel;
                break;
            default: // HOME
                activeContainer = btnHomeContainer;
                activeIcon      = ivHome;
                activeLabel     = tvHomeLabel;
                break;
        }

        activeContainer.setBackgroundResource(R.drawable.nav_item_active_bg);
        activeIcon.setColorFilter(0xFF2563EB);
        activeLabel.setTextColor(0xFF2563EB);
    }

    private void resetAllTabs() {
        LinearLayout[] containers = {
            btnLeaderboardContainer, btnShopContainer,
            btnHomeContainer, btnQuestsContainer, btnProfileContainer
        };
        ImageView[] icons = { ivLeaderboard, ivShop, ivHome, ivQuests, ivProfile };
        TextView[]  labels = {
            tvLeaderboardLabel, tvShopLabel,
            tvHomeLabel, tvQuestsLabel, tvProfileLabel
        };

        for (LinearLayout c : containers)
            c.setBackgroundResource(R.drawable.nav_item_inactive_bg);
        for (ImageView i : icons)
            i.setColorFilter(0xFF9CA3AF);
        for (TextView t : labels)
            t.setTextColor(0xFF9CA3AF);
    }
}
