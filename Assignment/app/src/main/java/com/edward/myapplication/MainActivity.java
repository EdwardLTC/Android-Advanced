package com.edward.myapplication;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import com.edward.myapplication.ui.course.CourseFragment;
import com.edward.myapplication.ui.maps.MapsFragment;
import com.edward.myapplication.ui.news.NewsFragment;
import com.edward.myapplication.ui.social.SocialFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.edward.myapplication.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationView;
    private View view;

    private static final float END_SCALE = 0.7f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        com.edward.myapplication.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //custom drawer view
        view = findViewById(R.id.app_bar_main);
        navigationView = binding.navView;
        DrawerLayout drawer = binding.drawerLayout;
        drawer.setScrimColor(Color.TRANSPARENT); //set color when drawer
        drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                customOnDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                navigationView.setVisibility(View.GONE);
            }
        });

        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            onSectionAttached(id);
            drawer.closeDrawers();
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void customOnDrawerSlide(View drawerView, float slideOffset) {
        navigationView.setVisibility(slideOffset > 0 ? View.VISIBLE : View.GONE);

        final float diffScaledOffset = slideOffset * (1 - END_SCALE);
        final float offsetScale = 1 - diffScaledOffset;
        view.setScaleX(offsetScale);
        view.setScaleY(offsetScale);

        final float xOffset = drawerView.getWidth() * slideOffset;
        final float xOffsetDiff = view.getWidth() * diffScaledOffset / 2;
        final float xTranslation = xOffset - xOffsetDiff;
        view.setTranslationX(xTranslation);

    }

    @SuppressLint("NonConstantResourceId")
    public void onSectionAttached(int id) {
        FragmentManager fragmentManager = getSupportFragmentManager(); // For AppCompat use getSupportFragmentManager
        Fragment fragment = null;
        switch (id) {
            case R.id.nav_course:
                fragment = new CourseFragment();
                break;
            case R.id.nav_maps:
                fragment = new MapsFragment();
                break;
            case R.id.nav_news:
                fragment = new NewsFragment();
                break;
            case R.id.nav_social:
                fragment = new SocialFragment();
                break;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_content_main, Objects.requireNonNull(fragment))
                .addToBackStack(null)
                .commit();
    }
}