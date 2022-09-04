package com.edward.assignment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.edward.assignment.ui.course.CourseFragment;
import com.edward.assignment.ui.maps.MapsFragment;
import com.edward.assignment.ui.news.NewsFragment;
import com.edward.assignment.ui.social.SocialFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.edward.assignment.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private View view;

    private static final float END_SCALE = 0.7f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        if (savedInstanceState == null) { //default fragment
            getSupportFragmentManager().
                    beginTransaction().replace(R.id.nav_host_fragment_content_main, new CourseFragment()).addToBackStack(null).commit();
        }

        com.edward.assignment.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navigationView = binding.navView;
        navigationView.getMenu().getItem(0).setChecked(true);

        drawer = binding.drawerLayout;
        view = findViewById(R.id.app_bar_main);
        drawer.setScrimColor(Color.TRANSPARENT);
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

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_course, R.id.nav_maps, R.id.nav_news, R.id.nav_social)
                .setOpenableLayout(drawer)
                .build();


        navigationView.setNavigationItemSelectedListener(item -> {
            onSectionAttached(item);
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

    @SuppressLint("NonConstantResourceId")
    public void onSectionAttached(MenuItem item) {
        int id = item.getItemId();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = null;
        switch (id) {
            case R.id.nav_course: {
                fragment = new CourseFragment();
                break;
            }
            case R.id.nav_maps: {
                fragment = new MapsFragment();
                break;
            }
            case R.id.nav_news: {
                fragment = new NewsFragment();
                break;
            }
            case R.id.nav_social: {
                fragment = new SocialFragment();
                break;
            }
            default:
                Toast.makeText(this, "Error Fragment Transaction", Toast.LENGTH_SHORT).show();
        }
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, Objects.requireNonNull(fragment), null);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        drawer.closeDrawers();
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

}