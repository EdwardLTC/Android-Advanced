package com.edward.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.edward.myapplication.databinding.ActivityMainBinding;
import com.edward.myapplication.ui.course.CourseFragment;
import com.edward.myapplication.ui.admin.AdminFragment;
import com.edward.myapplication.ui.maps.MapsFragment;
import com.edward.myapplication.ui.news.NewsFragment;
import com.edward.myapplication.ui.social.SocialFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationView;
    private View view;
    private static final float END_SCALE = 0.7f;
    private final String ROLE = "role";
    private int role ;
    private final int ROLE_ADMIN = 0;
    private final int ROLE_USER= 1;
    private  final  String  USERNAME = "username";
    private String username = "";
    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        role = Integer.parseInt(String.valueOf(getIntent().getIntExtra(ROLE,-1)));
        username = getIntent().getStringExtra(USERNAME);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

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

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.userName);
        TextView navTextView = headerView.findViewById(R.id.textView);
        navUsername.setText(username);
        navTextView.setText("Hello "+ username+", how are u today ?");

        if (role == ROLE_ADMIN ){ //set startDestination if role admin
            NavOptions options = new NavOptions.Builder()
                    .setPopUpTo(R.id.nav_course, true)
                    .build();
            navController.navigate(R.id.roleSwitch, savedInstanceState, options);
            navigationView.getMenu().getItem(0).setTitle("Admin");
            navigationView.getMenu().getItem(0).setIcon(R.drawable.admin);
        }

        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_logout){
                Logout();
                return true;
            }
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
                if (role == 0){
                    fragment = new AdminFragment();
                }else {
                    fragment = new CourseFragment();
                }
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
                .add(R.id.nav_host_fragment_content_main, Objects.requireNonNull(fragment))
                .addToBackStack(null)
                .commit();
    }
    public void Logout() {
        try {
            if (shp == null)
                shp = getSharedPreferences("myPreferences", MODE_PRIVATE);

            shpEditor = shp.edit();
            shpEditor.putString("name", "");
            shpEditor.commit();

            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
            finish();

        } catch (Exception ex) {
            Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}