package com.estrada.darlin.appdominospizza;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;

import com.estrada.darlin.appdominospizza.R;


public class MainActivity extends AppCompatActivity {

    private int idpromo;
    private String nom_promo,desc_promo;

    private String name, password, email;
    private String[] opciones = new String[] {"Mi perfil", "Nuestro Menu", "Cerrar Sesion"};
    private DrawerLayout drawerLayout;
    private ListView listView;
    ListView listView2;
    private ActionBarDrawerToggle drawerToggle;

    private Promociones[] datos = new Promociones[]{
            new Promociones(R.drawable.promo1_2x1,"Pizzas 2x1","Participan pizzas de masa original, " +
                    "orilla rellena de queso y crunchy.\nValida solo por el fin de semana."),
            new Promociones(R.drawable.promo2_arequipe_rolls,"Gratis Arequipe Rolls","Haz tu " +
                    "pedido desde nuestra app y por la compra de una pizza grande te damos " +
                    "arequipe rolls gratis."),
            new Promociones(R.drawable.promo3_pizza_grande_50por,"Descuento de 50 porciento",
                    "Pide una pizza grande masa original de 1 a 9 ingredientes y llevate la " +
                            "segunda al 50% de descuento."),
            new Promociones(R.drawable.promo4_pizza_sarten,"Pizza de sartèn a 159$","Pide Pizza " +
                    "de sarten de 2 a 4 ingredientes a tan solo 159$.\nPromo valida solo por " +
                    "internet y app"),
            new Promociones(R.drawable.promo5_pizza_sarten_1ing,"Pizza de sartèn a 189$","Pizza " +
                    "de sarten de 1 ingredientes màs papotas y canelazo bites a tan solo 189$")};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toast.makeText(this,"onCreate",Toast.LENGTH_LONG).show();

        /////////////////mandar datos////////////////////

        Bundle extras = getIntent().getExtras();

        name = extras.getString("usuario");
        password = extras.getString("password");
        email = extras.getString("email");

        /////////////////////navigation Drawer///////////////////////////

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.contenedorPrincipal_1);
        listView = (ListView) findViewById(R.id.menuIzq_1);

        listView.setAdapter(new ArrayAdapter<String>(getSupportActionBar().getThemedContext(),
                android.R.layout.simple_list_item_1, opciones));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i){
                    case(0):
                        Intent intent = new Intent(MainActivity.this, PerfilActivity.class);
                        intent.putExtra("usuario",name);
                        intent.putExtra("password", password);
                        intent.putExtra("email",email);
                        startActivity(intent);
                        finish();
                        break;
                    case(1):
                        Intent intent2 = new Intent(MainActivity.this, ProductosActivity.class);
                        intent2.putExtra("usuario",name);
                        intent2.putExtra("password", password);
                        intent2.putExtra("email",email);
                        startActivity(intent2);
                        finish();
                        break;
                    case(2):
                        Intent intent3 = new Intent(MainActivity.this, LogginActivity.class);
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

        Adapter adaptador = new Adapter(this, datos);

        listView2 = (ListView) findViewById(R.id.listview);


        listView2.setAdapter(adaptador);

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ofer_navDrawerActivity.class);

                idpromo = datos[i].getIdImagen();
                nom_promo = datos[i].getNombre();
                desc_promo = datos[i].getDescripcion();

                intent.putExtra("idpromo",idpromo);
                intent.putExtra("nom_promo",nom_promo);
                intent.putExtra("desc_promo", desc_promo);
                intent.putExtra("usuario",name);
                intent.putExtra("password", password);
                intent.putExtra("email",email);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.mMiperfil:
                Intent intent = new Intent(this, PerfilActivity.class);
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
                //Toast.makeText(this, "presiono home",Toast.LENGTH_SHORT).show();
                drawerLayout.openDrawer(Gravity.LEFT);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class Adapter extends ArrayAdapter<Promociones> {
        public Adapter(Context context, Promociones[] datos) {
            super(context, R.layout.promociones_item, datos);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.promociones_item, null);

            ImageView imagen = (ImageView) item.findViewById(R.id.iImagen);
            imagen.setImageResource(datos[position].getIdImagen());

            TextView nombre = (TextView) item.findViewById(R.id.tNombre);
            nombre.setText(datos[position].getNombre());
            TextView descripcion = (TextView) item.findViewById(R.id.tDescripcion);
            //descripcion.setText(datos[position].getDescripcion());
            descripcion.setText("Click para mas informacion.");

            return (item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.mMiperfil:
                Intent intent = new Intent(this, PerfilActivity.class);
                intent.putExtra("usuario",name);
                intent.putExtra("email",email);
                startActivity(intent);
                break;
            case R.id.mProductosActivity:
                Intent intent2 = new Intent(this, ProductosActivity.class);
                intent2.putExtra("usuario",name);
                intent2.putExtra("email",email);
                startActivity(intent2);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
*/
}
