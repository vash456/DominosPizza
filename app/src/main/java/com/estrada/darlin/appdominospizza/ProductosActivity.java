package com.estrada.darlin.appdominospizza;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ProductosActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private String name, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        Bundle extras = getIntent().getExtras();

        name = extras.getString("usuario");
        email = extras.getString("email");

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.TabListener tabListener = new ActionBar.TabListener(){
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        };

        ActionBar.Tab tab = actionBar.newTab().setText("Pizzas").setTabListener(tabListener);
        actionBar.addTab (tab);
        tab = actionBar.newTab().setText("Entrantes").setTabListener(tabListener);
        actionBar.addTab (tab);
        tab = actionBar.newTab().setText("Postres").setTabListener(tabListener);
        actionBar.addTab (tab);

        tab = actionBar.newTab().setText("Bebidas").setTabListener(tabListener);
        actionBar.addTab (tab);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                getSupportActionBar().setSelectedNavigationItem(position);

            }
        });

    }

    public class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return new PizzasFragment();
                case 1: return new EntrantesFragment();
                case 2: return new PostresFragment();
                case 3: return new BebidasFragment();
                default: return null;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_productos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.mMainActivity:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("usuario",name);
                intent.putExtra("email",email);
                startActivity(intent);
                break;
            case R.id.mMiperfil:
                Intent intent2 = new Intent(this, PerfilActivity.class);
                intent2.putExtra("usuario",name);
                intent2.putExtra("email",email);
                startActivity(intent2);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
