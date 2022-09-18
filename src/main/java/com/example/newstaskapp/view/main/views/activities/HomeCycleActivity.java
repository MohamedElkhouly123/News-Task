package com.example.newstaskapp.view.main.views.activities;

import static com.example.newstaskapp.view.main.utils.HelperMethod.setStatusBar;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.example.newstaskapp.R;
import com.example.newstaskapp.databinding.ActivityHomeCycleBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;


public class HomeCycleActivity extends BaseActivityKotlin {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeCycleBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeCycleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setStatusBar(this,R.color.blue);


    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home_cycle, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home_cycle);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}