package com.estrada.darlin.appdominospizza;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
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
import android.widget.TextView;

public class PerfilActivity extends AppCompatActivity {

    TextView t_name, t_password, t_email;
    private String name, password, email;

    private String[] opciones = new String[] {"Pagina Principal", "Nuestro Menu", "Cerrar Sesion"};
    private DrawerLayout drawerLayout;
    private ListView listView;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        t_name = (TextView) findViewById(R.id.t_name);
        t_password = (TextView) findViewById(R.id.t_password);
        t_email = (TextView) findViewById(R.id.t_email);

        Bundle extras = getIntent().getExtras();//para recibir datos

        name = extras.getString("usuario");
        password = extras.getString("password");
        email = extras.getString("email");

        t_name.setText(name);
        t_password.setText(password);
        t_email.setText(email);

        /////////////////////navigation Drawer///////////////////////////

        ActionBar actionBar = getSupportActionBar();
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
                        intent.putExtra("usuario",name);
                        intent.putExtra("password", password);
                        intent.putExtra("email",email);
                        startActivity(intent);
                        finish();
                        break;
                    case(1):
                        Intent intent2 = new Intent(PerfilActivity.this, ProductosActivity.class);
                        intent2.putExtra("usuario",name);
                        intent2.putExtra("password", password);
                        intent2.putExtra("email",email);
                        startActivity(intent2);
                        finish();
                        break;
                    case(2):
                        Intent intent3 = new Intent(PerfilActivity.this, LogginActivity.class);
                        intent3.putExtra("usuario",name);
                        intent3.putExtra("password", password);
                        intent3.putExtra("email",email);
                        intent3.putExtra("sesion","cerrada");
                        startActivity(intent3);
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

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
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_perfil, menu);
        return true;
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
