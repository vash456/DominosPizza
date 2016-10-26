package com.estrada.darlin.appdominospizza;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class PerfilActivity extends AppCompatActivity {

    //SharedPreferences prefs;

    //TextView t_name, t_password, t_email;
    //private String name, password, email;

    private ViewPager mViewPager;

    private String[] opciones = new String[] {"Pagina principal","Mi perfil", "Nuestro Menu", "Cerrar Sesion"};
    private DrawerLayout drawerLayout;
    private ListView listView;
    private ActionBarDrawerToggle drawerToggle;

    //Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //t_name = (TextView) findViewById(R.id.t_name);
        //t_password = (TextView) findViewById(R.id.t_password);
        //t_email = (TextView) findViewById(R.id.t_email);



        //Bundle extras = getIntent().getExtras();//para recibir datos

        /*name = extras.getString("usuario");
        password = extras.getString("password");
        email = extras.getString("email");
        */

        //prefs = getSharedPreferences("preferencia", Context.MODE_PRIVATE);
        //refreshPrefs();
        //t_name.setText(name);
        //t_password.setText(password);
        //t_email.setText(email);

        //////////swipe tabs//////////////////

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager2);
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

        ActionBar.Tab tab = actionBar.newTab().setText("PERFIL").setIcon(R.drawable.perfil_icon).setTabListener(tabListener);
        actionBar.addTab (tab);
        tab = actionBar.newTab().setText("FAVORITOS").setIcon(R.drawable.favorites_icon).setTabListener(tabListener);
        actionBar.addTab (tab);


        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                getSupportActionBar().setSelectedNavigationItem(position);

            }
        });


        /////////////////////navigation Drawer///////////////////////////
        actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.contenedorPrincipal_2);
        listView = (ListView) findViewById(R.id.menuIzq_2);

        listView.setAdapter(new ArrayAdapter<String>(getSupportActionBar().getThemedContext(),
                android.R.layout.simple_list_item_1, opciones));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i){
                    case(0):
                        Intent intent = new Intent(PerfilActivity.this, MainActivity.class);
                        //intent.putExtra("usuario",name);
                        //intent.putExtra("password", password);
                        //intent.putExtra("email",email);
                        startActivity(intent);
                        finish();
                        break;
                    case(1):
                        //Intent intent2 = new Intent(PerfilActivity.this, PerfilActivity.class);
                        //intent2.putExtra("usuario",name);
                        //intent2.putExtra("password", password);
                        //intent2.putExtra("email",email);
                        //startActivity(intent2);
                        //finish();
                        break;
                    case(2):
                        Intent intent3 = new Intent(PerfilActivity.this, ProductosActivity.class);
                        //intent3.putExtra("usuario",name);
                        //intent3.putExtra("password", password);
                        //intent3.putExtra("email",email);
                        startActivity(intent3);
                        finish();
                        break;
                    case(3):
                        Intent intent4 = new Intent(PerfilActivity.this, LogginActivity.class);
                        //intent4.putExtra("usuario",name);
                        //intent4.putExtra("password", password);
                        //intent4.putExtra("email",email);
                        intent4.putExtra("sesion","cerrada");
                        startActivity(intent4);
                        finish();
                        break;
                }

                listView.setItemChecked(i,true);
                drawerLayout.closeDrawer(listView);
            }
        });

        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.abierto, R.string.cerrado);

        drawerLayout.setDrawerListener(drawerToggle);

    }

    public class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0: return new PerfilFragment();
                case 1: return new FavoritosFragment();
                default: return null;
            }

        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.mMainActivity:
                Intent intent = new Intent(this, MainActivity.class);
                //intent.putExtra("usuario",name);
                //intent.putExtra("password", password);
                //intent.putExtra("email",email);
                startActivity(intent);
                break;
            case R.id.mProductosActivity:
                Intent intent2 = new Intent(this, ProductosActivity.class);
                //intent2.putExtra("usuario",name);
                //intent2.putExtra("password", password);
                //intent2.putExtra("email",email);
                startActivity(intent2);
                break;
            case R.id.mCerrarSesion:
                Intent intent3 = new Intent(this, LogginActivity.class);
                //intent3.putExtra("usuario",name);
                //intent3.putExtra("password", password);
                //intent3.putExtra("email",email);
                intent3.putExtra("sesion","cerrada");
                startActivity(intent3);
                finish();
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.LEFT);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_perfil, menu);
        return true;
    }

    /*public void refreshPrefs(){
        name = String.valueOf(prefs.getString("var_name","Nombre no definido"));
        email = String.valueOf(prefs.getString("var_email","Email no definido"));
    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.mMainActivity:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("usuario",name);
                intent.putExtra("password", password);
                intent.putExtra("email",email);
                startActivity(intent);
                break;
            case R.id.mProductosActivity:
                Intent intent2 = new Intent(this, ProductosActivity.class);
                intent2.putExtra("usuario",name);
                intent2.putExtra("password", password);
                intent2.putExtra("email",email);
                startActivity(intent2);
                break;
            case R.id.mCerrarSesion:
                Intent intent3 = new Intent(this, LogginActivity.class);
                intent3.putExtra("usuario",name);
                intent3.putExtra("password", password);
                intent3.putExtra("email",email);
                intent3.putExtra("sesion","cerrada");
                startActivity(intent3);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
*/
}
