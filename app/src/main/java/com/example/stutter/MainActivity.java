package com.example.stutter;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.stutter.ui.AppViewModel;
import com.example.stutter.ui.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private AppViewModel vm;
    private LinearLayout btnSettingsContainer;
    private LinearLayout btnHomeContainer;
    private LinearLayout btnProfileContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vm = new ViewModelProvider(this).get(AppViewModel.class);
        vm.load();

        // Setup bottom nav buttons
        btnSettingsContainer = findViewById(R.id.btnSettingsContainer);
        btnHomeContainer = findViewById(R.id.btnHomeContainer);
        btnProfileContainer = findViewById(R.id.btnProfileContainer);

        // Wire up bottom nav click listeners
        btnSettingsContainer.setOnClickListener(v -> {
            // Settings not implemented yet
            android.widget.Toast.makeText(this, "Settings screen not implemented yet", android.widget.Toast.LENGTH_SHORT).show();
        });

        btnHomeContainer.setOnClickListener(v -> {
            // Go to home
            replace(new HomeFragment(), false);
        });

        btnProfileContainer.setOnClickListener(v -> {
            // Profile not implemented yet
            android.widget.Toast.makeText(this, "Profile screen not implemented yet", android.widget.Toast.LENGTH_SHORT).show();
        });

        // Load home fragment on startup
        if (savedInstanceState == null) {
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
}
