package com.estrada.darlin.appdominospizza;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ofer_navDrawerActivity extends AppCompatActivity {

    private int idpromo;
    private String nom_promo,desc_promo;

    private String name, password, email;
    private String[] opciones = new String[] {"Pagina principal","Mi perfil", "Nuestro Menu", "Cerrar Sesion"};
    private DrawerLayout drawerLayout;
    private ListView listView;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofer_nav_drawer);

        /////////////////mandar datos////////////////////

        Bundle extras = getIntent().getExtras();

        //name = extras.getString("usuario");
        //password = extras.getString("password");
        //email = extras.getString("email");

        idpromo = extras.getInt("idpromo");
        nom_promo = extras.getString("nom_promo");
        desc_promo = extras.getString("desc_promo");

        ImageView imagen = (ImageView) findViewById(R.id.iImagen_promo);
        imagen.setImageResource(idpromo);

        TextView nombre = (TextView) findViewById(R.id.tNom_promo);
        nombre.setText(nom_promo);
        TextView descripcion = (TextView) findViewById(R.id.tDesc_promo);
        descripcion.setText(desc_promo);

        /////////////////////navigation Drawer///////////////////////////

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.contenedorPrincipal_4);
        listView = (ListView) findViewById(R.id.menuIzq_4);

        listView.setAdapter(new ArrayAdapter<String>(getSupportActionBar().getThemedContext(),
                android.R.layout.simple_list_item_1, opciones));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i){
                    case(0):
                        Intent intent = new Intent(ofer_navDrawerActivity.this, MainActivity.class);
                        //intent.putExtra("usuario",name);
                        //intent.putExtra("password", password);
                        //intent.putExtra("email",email);
                        startActivity(intent);
                        finish();
                        break;
                    case(1):
                        Intent intent2 = new Intent(ofer_navDrawerActivity.this, PerfilActivity.class);
                        //intent2.putExtra("usuario",name);
                        //intent2.putExtra("password", password);
                        //intent2.putExtra("email",email);
                        startActivity(intent2);
                        finish();
                        break;
                    case(2):
                        Intent intent3 = new Intent(ofer_navDrawerActivity.this, ProductosActivity.class);
                        //intent3.putExtra("usuario",name);
                        //intent3.putExtra("password", password);
                        //intent3.putExtra("email",email);
                        startActivity(intent3);
                        finish();
                        break;
                    case(3):
                        Intent intent4 = new Intent(ofer_navDrawerActivity.this, LogginActivity.class);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mMainActivity:
                Intent intent = new Intent(this, MainActivity.class);
                //intent.putExtra("usuario",name);
                //intent.putExtra("password",password);
                //intent.putExtra("email",email);
                startActivity(intent);
                break;
            case R.id.mMiperfil:
                Intent intent2 = new Intent(this, PerfilActivity.class);
                //intent2.putExtra("usuario",name);
                //intent2.putExtra("password",password);
                //intent2.putExtra("email",email);
                startActivity(intent2);
                break;
            case R.id.mProductosActivity:
                Intent intent3 = new Intent(this, ProductosActivity.class);
                //intent3.putExtra("usuario",name);
                //intent3.putExtra("password", password);
                //intent3.putExtra("email",email);
                startActivity(intent3);
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.LEFT);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_promos, menu);
        return true;
    }
}
