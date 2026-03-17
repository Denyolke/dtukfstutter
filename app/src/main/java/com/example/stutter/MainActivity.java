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
import com.example.stutter.util.ThemeManager;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private AppViewModel vm;

    private LinearLayout btnLeaderboardContainer;
    private LinearLayout btnShopContainer;
    private LinearLayout btnHomeContainer;
    private LinearLayout btnQuestsContainer;
    private LinearLayout btnProfileContainer;

    private ImageView ivLeaderboard, ivShop, ivHome, ivQuests, ivProfile;
    private TextView  tvLeaderboardLabel, tvShopLabel, tvHomeLabel,
                      tvQuestsLabel, tvProfileLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Apply saved theme BEFORE setContentView so it takes effect immediately
        ThemeManager.applyTheme(this);

        super.onCreate(savedInstanceState);

        FirebaseAuthManager authManager = FirebaseAuthManager.getInstance();
        FirebaseUser user = authManager.getCurrentUser();

        if (user == null) { finish(); return; }

        setContentView(R.layout.activity_main);

        vm = new ViewModelProvider(this).get(AppViewModel.class);
        vm.load();

        btnLeaderboardContainer = findViewById(R.id.btnLeaderboardContainer);
        btnShopContainer        = findViewById(R.id.btnShopContainer);
        btnHomeContainer        = findViewById(R.id.btnHomeContainer);
        btnQuestsContainer      = findViewById(R.id.btnQuestsContainer);
        btnProfileContainer     = findViewById(R.id.btnProfileContainer);

        ivLeaderboard = findViewById(R.id.ivLeaderboard);
        ivShop        = findViewById(R.id.ivShop);
        ivHome        = findViewById(R.id.ivHome);
        ivQuests      = findViewById(R.id.ivQuests);
        ivProfile     = findViewById(R.id.ivProfile);

        tvLeaderboardLabel = findViewById(R.id.tvLeaderboardLabel);
        tvShopLabel        = findViewById(R.id.tvShopLabel);
        tvHomeLabel        = findViewById(R.id.tvHomeLabel);
        tvQuestsLabel      = findViewById(R.id.tvQuestsLabel);
        tvProfileLabel     = findViewById(R.id.tvProfileLabel);

        btnLeaderboardContainer.setOnClickListener(v -> { setSelectedTab(Tab.LEADERBOARD); replace(new LeaderboardFragment(), false); });
        btnShopContainer.setOnClickListener(v ->        { setSelectedTab(Tab.SHOP);        replace(new ShopFragment(), false); });
        btnHomeContainer.setOnClickListener(v ->        { setSelectedTab(Tab.HOME);        replace(new HomeFragment(), false); });
        btnQuestsContainer.setOnClickListener(v ->      { setSelectedTab(Tab.QUESTS);      replace(new QuestsFragment(), false); });
        btnProfileContainer.setOnClickListener(v ->     { setSelectedTab(Tab.PROFILE);     replace(new ProfileFragment(), false); });

        if (savedInstanceState == null) {
            setSelectedTab(Tab.HOME);
            replace(new HomeFragment(), false);
        }
    }

    public void replace(Fragment f, boolean addToBackStack) {
        if (f == null) return;
        var tx = getSupportFragmentManager().beginTransaction().replace(R.id.container, f);
        if (addToBackStack) tx.addToBackStack(null);
        tx.commit();
    }

    private enum Tab { LEADERBOARD, SHOP, HOME, QUESTS, PROFILE }

    private void setSelectedTab(Tab selected) {
        resetAllTabs();
        LinearLayout container;
        ImageView    icon;
        TextView     label;
        switch (selected) {
            case LEADERBOARD: container = btnLeaderboardContainer; icon = ivLeaderboard; label = tvLeaderboardLabel; break;
            case SHOP:        container = btnShopContainer;        icon = ivShop;        label = tvShopLabel;        break;
            case QUESTS:      container = btnQuestsContainer;      icon = ivQuests;      label = tvQuestsLabel;      break;
            case PROFILE:     container = btnProfileContainer;     icon = ivProfile;     label = tvProfileLabel;     break;
            default:          container = btnHomeContainer;        icon = ivHome;        label = tvHomeLabel;        break;
        }
        container.setBackgroundResource(R.drawable.nav_item_active_bg);
        icon.setColorFilter(0xFF2563EB);
        label.setTextColor(0xFF2563EB);
    }

    private void resetAllTabs() {
        LinearLayout[] containers = { btnLeaderboardContainer, btnShopContainer, btnHomeContainer, btnQuestsContainer, btnProfileContainer };
        ImageView[]    icons      = { ivLeaderboard, ivShop, ivHome, ivQuests, ivProfile };
        TextView[]     labels     = { tvLeaderboardLabel, tvShopLabel, tvHomeLabel, tvQuestsLabel, tvProfileLabel };
        for (LinearLayout c : containers) c.setBackgroundResource(R.drawable.nav_item_inactive_bg);
        for (ImageView    i : icons)      i.setColorFilter(0xFF9CA3AF);
        for (TextView     l : labels)     l.setTextColor(0xFF9CA3AF);
    }
}
