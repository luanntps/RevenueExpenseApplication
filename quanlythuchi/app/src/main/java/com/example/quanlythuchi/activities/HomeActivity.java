package com.example.quanlythuchi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.adapters.ViewPagerAdapter;
import com.example.quanlythuchi.fragments.CollectFragment;
import com.example.quanlythuchi.fragments.SpendFragment;
import com.example.quanlythuchi.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


public class HomeActivity extends AppCompatActivity {
    BottomNavigationView navigationView;
    DrawerLayout drawerLayout;
    NavigationView navigationView2;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TextView tvName;
    String name;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("KEY_NAME", "Default");
        //
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView2=findViewById(R.id.navigationView2);
        navigationView=findViewById(R.id.bottom_navigation);
        //
        viewPager=findViewById(R.id.vpViewPager);
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        navigationView.getMenu().findItem(R.id.nav_spend).setChecked(true);
                        toolbar.setTitle("Sổ chi");
                        break;
                    case 1:
                        navigationView.getMenu().findItem(R.id.nav_collect).setChecked(true);
                        toolbar.setTitle("Sổ thu");
                        break;
                    case 2:
                        navigationView.getMenu().findItem(R.id.nav_profile).setChecked(true);
                        toolbar.setTitle("Cá nhân");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // CHANGE NAME OF NAVIGATION DRAWER'S HEADER
        View headerView = navigationView2.getHeaderView(0);
        tvName = headerView.findViewById(R.id.tvName);
        tvName.setText(name);
        Toast.makeText(HomeActivity.this, name, Toast.LENGTH_SHORT).show();
        // hide status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // set tool bar
        toolbar = findViewById(R.id.tbToolbar);
        toolbar.setTitle("Sổ chi");
        setSupportActionBar(toolbar);
        //set toggle of tool bar for drawer
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.menu_open,R.string.menu_close);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        //SET EVENT CLICK FOR NAVIGATION DRAWER
        navigationView2.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                Fragment fragment = null;
                switch(item.getItemId()){
                    case R.id.nav_cash_manager:{
                        fragment = new SpendFragment();
                        navigationView.setSelectedItemId(R.id.nav_spend);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        getSupportFragmentManager().beginTransaction().replace(R.id.body_container,fragment).commit();
                        break;}
                    case R.id.nav_collect_type_manager:{
                        Intent intent = new Intent(HomeActivity.this,CollectTypeManagerActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("username", name);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    }
                    case R.id.nav_spend_type_manager:{
                        Intent intent = new Intent(HomeActivity.this,SpendTypeManagerActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("username", name);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    }
                }
                return false;
            }
        });

        ////SET EVENT CLICK FOR NAVIGATION BAR
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch(item.getItemId()){
                    case R.id.nav_spend:{
                        fragment = new SpendFragment();
                        toolbar.setTitle("Sổ chi");
                        viewPager.setCurrentItem(0);
                        break;}
                    case R.id.nav_collect:{
                        fragment = new CollectFragment();
                        viewPager.setCurrentItem(1);
                        toolbar.setTitle("Sổ thu");
                        break;}
                    case R.id.nav_profile:{
                        fragment = new ProfileFragment();
                        viewPager.setCurrentItem(2);
                        toolbar.setTitle("Cá nhân");
                        break;
                    }
                }

                return true;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public String getUsername(){
        return name;
    }
}