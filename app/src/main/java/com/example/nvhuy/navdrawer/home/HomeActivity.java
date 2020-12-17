package com.example.nvhuy.navdrawer.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;
import com.example.nvhuy.navdrawer.Adapter.RecyclerViewAdapter;
import com.example.nvhuy.navdrawer.Adapter.ViewPagerAdapter;
import com.example.nvhuy.navdrawer.MainActivity;
import com.example.nvhuy.navdrawer.R;
import com.example.nvhuy.navdrawer.models.Fruit;
import com.example.nvhuy.navdrawer.models.MyDatabaseHelper;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    RecyclerView recyclerView_allProduct;
    RecyclerViewAdapter adapter_allProduct;
    MyDatabaseHelper dbHelper;
    ArrayList<Fruit> items;

    ViewPager viewPager;
    ViewPagerAdapter mViewPagerAdapter;
    private Handler handler;
    private int delay = 2000;
    private int page = 0;
    Runnable runnable = new Runnable() {
        public void run() {
            if (mViewPagerAdapter.getCount() == page) {
                page = 0;
            } else {
                page++;
            }
            viewPager.setCurrentItem(page, true);
            handler.postDelayed(this, delay);
        }
    };
    int[] images = {R.mipmap.facebook, R.mipmap.google,R.mipmap.twitter};// image auto slide

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //toolbar
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        //Navigator
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //todo dbHelper
        dbHelper = new MyDatabaseHelper(this);
        dbHelper.createDefaultProductsIfNeed();

        //todo recyclerView allProduct
        items = new ArrayList<>();
        items = dbHelper.getAllProduct();

        //fakeData(items);
        recyclerView_allProduct = findViewById(R.id.rv_allProduct);
        adapter_allProduct = new RecyclerViewAdapter(items);
        recyclerView_allProduct.setAdapter(adapter_allProduct);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView_allProduct);
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
        recyclerView_allProduct.setLayoutManager(gridLayoutManager);

        //todo set up viewpager auto slide
        handler = new Handler();
        viewPager = findViewById(R.id.viewpager);
        mViewPagerAdapter = new ViewPagerAdapter(HomeActivity.this, images);
        viewPager.setAdapter(mViewPagerAdapter);



    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, delay);
        adapter_allProduct.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

}
