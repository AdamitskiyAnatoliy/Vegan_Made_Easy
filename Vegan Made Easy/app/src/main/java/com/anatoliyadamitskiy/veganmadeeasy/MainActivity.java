package com.anatoliyadamitskiy.veganmadeeasy;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eccyan.widget.SpinningTabStrip;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.special.ResideMenu.ResideMenuItem;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.tabs)
    SpinningTabStrip tabs;
    @InjectView(R.id.pager)
    ViewPager pager;

    private MyPagerAdapter adapter;
    private Drawable oldBackground = null;
    private int currentColor;
    private SystemBarTintManager mTintManager;

    ResideMenu resideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(
                getResources().getDrawable(R.mipmap.ic_action_nav));


        // create our manager instance after the content view is set
        mTintManager = new SystemBarTintManager(this);
        // enable status bar tint
        mTintManager.setStatusBarTintEnabled(true);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        changeColor(getResources().getColor(R.color.green));

        tabs.setOnTabReselectedListener(new SpinningTabStrip.OnTabReselectedListener() {
            @Override
            public void onTabReselected(int position) {
                //Toast.makeText(MainActivity.this, "Tab reselected: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        // Navigation
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.blueberries);
        resideMenu.attachToActivity(this);

        // create menu items;
        String titles[] = { "Home", "Favorites", "Local Stores", "Shopping List", "Settings" };
        int icon[] = { R.mipmap.ic_action_home, R.mipmap.ic_action_fav, R.mipmap.ic_action_stores,
                R.mipmap.ic_action_list , R.mipmap.ic_action_settings};

        for (int i = 0; i < titles.length; i++){
            com.special.ResideMenu.ResideMenuItem item = new ResideMenuItem(this, icon[i], titles[i]);
            item.setOnClickListener(this);
            resideMenu.addMenuItem(item,  ResideMenu.DIRECTION_LEFT); // or  ResideMenu.DIRECTION_RIGHT
        }


        ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
            @Override
            public void openMenu() {
                //Toast.makeText(getApplicationContext(), "Menu is opened!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void closeMenu() {
                //Toast.makeText(getApplicationContext(), "Menu is closed!", Toast.LENGTH_SHORT).show();
            }
        };

        resideMenu.setMenuListener(menuListener);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        LinearLayout ignored_view = (LinearLayout) findViewById(R.id.mainLayout);
        resideMenu.addIgnoredView(ignored_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //case R.id.action_contact:
                //QuickContactFragment.newInstance().show(getSupportFragmentManager(), "QuickContactFragment");
                //return true;
            case android.R.id.home:
                //Toast.makeText(this, "Menu Tapped", Toast.LENGTH_SHORT).show();

                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT); // or ResideMenu.DIRECTION_RIGHT
                //resideMenu.closeMenu();

        }
        return super.onOptionsItemSelected(item);
    }

    private void changeColor(int newColor) {
        tabs.setBackgroundColor(newColor);
        mTintManager.setTintColor(newColor);
        // change ActionBar color just if an ActionBar is available
        Drawable colorDrawable = new ColorDrawable(newColor);
        Drawable bottomDrawable = new ColorDrawable(getResources().getColor(android.R.color.transparent));
        LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});
        if (oldBackground == null) {
            getSupportActionBar().setBackgroundDrawable(ld);
        } else {
            TransitionDrawable td = new TransitionDrawable(new Drawable[]{oldBackground, ld});
            getSupportActionBar().setBackgroundDrawable(td);
            td.startTransition(200);
        }

        oldBackground = ld;
        currentColor = newColor;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentColor", currentColor);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentColor = savedInstanceState.getInt("currentColor");
        changeColor(currentColor);
    }

    @Override
    public void onClick(View view) {


    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"Categories", "Home", "Top Paid", "Top Free", "Top Grossing", "Top New Paid",
                "Top New Free", "All"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            return SuperAwesomeCardFragment.newInstance(position);
        }
    }
}
