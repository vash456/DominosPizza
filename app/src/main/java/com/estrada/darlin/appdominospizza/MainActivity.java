package com.estrada.darlin.appdominospizza;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    DominosSQLiteHelper usuarios;
    SQLiteDatabase dbUsuarios;

    private int idpromo;
    private String nom_promo,desc_promo;

    private String name, password, email, tablas;
    private String[] opciones = new String[] {"Pagina principal","Mi perfil", "Nuestro Menu", "Cerrar Sesion"};
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

        usuarios = new DominosSQLiteHelper(this, "DominosBD", null, 1);
        dbUsuarios = usuarios.getWritableDatabase();

        prefs = getSharedPreferences("preferencia", Context.MODE_PRIVATE);
        editor = prefs.edit();
        tablas = String.valueOf(prefs.getString("var_tablas","valor no definido"));

        if (tablas.equals("valor no definido")){
            llenarTablaProductos(dbUsuarios);
            //llenarTablaPizzas(dbUsuarios);
            //llenarTablaEntrantes(dbUsuarios);
            //llenarTablaPostres(dbUsuarios);
            //llenarTablaBebidas(dbUsuarios);
            tablas = "lleno";
            editor.putString("var_tablas",tablas);
            editor.commit();
        }

        //Toast.makeText(this,"onCreate",Toast.LENGTH_LONG).show();

        /////////////////mandar datos////////////////////

        /*Bundle extras = getIntent().getExtras();

        name = extras.getString("usuario");
        password = extras.getString("password");
        email = extras.getString("email");
        */
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
                        //Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        //intent.putExtra("usuario",name);
                        //intent.putExtra("password", password);
                        //intent.putExtra("email",email);
                        //startActivity(intent);
                        //finish();
                        break;
                    case(1):
                        Intent intent2 = new Intent(MainActivity.this, PerfilActivity.class);
                        //intent2.putExtra("usuario",name);
                        //intent2.putExtra("password", password);
                        //intent2.putExtra("email",email);
                        startActivity(intent2);
                        finish();
                        break;
                    case(2):
                        Intent intent3 = new Intent(MainActivity.this, ProductosActivity.class);
                        //intent3.putExtra("usuario",name);
                        //intent3.putExtra("password", password);
                        //intent3.putExtra("email",email);
                        startActivity(intent3);
                        finish();
                        break;
                    case(3):
                        Intent intent4 = new Intent(MainActivity.this, LogginActivity.class);
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
                //intent.putExtra("usuario",name);
                //intent.putExtra("password", password);
               // intent.putExtra("email",email);
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
public void llenarTablaPizzas(SQLiteDatabase sqLiteDatabase) {

    ContentValues values = new ContentValues();

    values.put("idImagen",R.drawable.pizza_colombiana);
    values.put("producto","Pizza Colombiana");
    values.put("descripcion","Cebolla, maíz tierno, tocineta y chorizo.");
    values.put("precio","10000");
    sqLiteDatabase.insert("TablaPizzas",null,values);

    values.put("idImagen",R.drawable.hawaiana);
    values.put("producto","Pizza Hawaiana");
    values.put("descripcion","Extraqueso , Jamón y Piña.");
    values.put("precio","10000");
    sqLiteDatabase.insert("TablaPizzas",null,values);

    values.put("idImagen",R.drawable.fiesta_pepperoni);
    values.put("producto","Fiesta Pepperoni");
    values.put("descripcion","Doble pepperoni y extraqueso.");
    values.put("precio","10000");
    sqLiteDatabase.insert("TablaPizzas",null,values);

    values.put("idImagen",R.drawable.honolulu);
    values.put("producto","Pizza Honolulu");
    values.put("descripcion","Jalapeño, jamón, tocineta y piña.");
    values.put("precio","10000");
    sqLiteDatabase.insert("TablaPizzas",null,values);

    values.put("idImagen",R.drawable.vegetariana);
    values.put("producto","Pizza Vegetariana");
    values.put("descripcion","Cebolla, pimentón, champiñón y tomate.");
    values.put("precio","10000");
    sqLiteDatabase.insert("TablaPizzas",null,values);

    values.put("idImagen",R.drawable.bbq);
    values.put("producto","Pizza BBQ");
    values.put("descripcion","Carne molida, tocineta, maíz tierno, salsa BBQ y cebolla.");
    values.put("precio","10000");
    sqLiteDatabase.insert("TablaPizzas",null,values);

}

    public void llenarTablaEntrantes(SQLiteDatabase sqLiteDatabase) {

        ContentValues values = new ContentValues();

        values.put("idImagen",R.drawable.canela_baitz);
        values.put("producto","Canela Baitz");
        values.put("descripcion","Deliciosos panecillos dulces hechos con la receta única de Domino's, espolvoreados con canela y acompañados de Arequipe.");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaEntrantes",null,values);

        values.put("idImagen",R.drawable.palitos_queso);
        values.put("producto","Palitos de queso");
        values.put("descripcion","Exquisitas barritas de pan cubiertas con queso mozarella gratinado y acompañados de Arequipe.");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaEntrantes",null,values);

        values.put("idImagen",R.drawable.arequipe_rolls);
        values.put("producto","Arequipe Rolls");
        values.put("descripcion","Exquisitos rollos recién horneados, rellenos de arequipe y glaseados con el toque Domino's");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaEntrantes",null,values);

        values.put("idImagen",R.drawable.combo_pollo);
        values.put("producto","Combo Pollo");
        values.put("descripcion","Strippers, Kickers y Alitas");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaEntrantes",null,values);

        values.put("idImagen",R.drawable.patatas_grill);
        values.put("producto","Patatas grill");
        values.put("descripcion","Crujientes patatas horneadas acompañadas de una salsa a elegir.");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaEntrantes",null,values);

        values.put("idImagen",R.drawable.ensaladas_pasta_marisco);
        values.put("producto","Ensalas pastos y mariscos");
        values.put("descripcion","Ensalada Mediterránea de pasta al dente con zanahorias, maiz, guisantes, calamares y gambas.");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaEntrantes",null,values);

    }
    public void llenarTablaPostres(SQLiteDatabase sqLiteDatabase) {

        ContentValues values = new ContentValues();

        values.put("idImagen",R.drawable.brownie_duo);
        values.put("producto","Brownie Duo");
        values.put("descripcion","Dos raciones de Brownie de chocolate.");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaPostres",null,values);

        values.put("idImagen",R.drawable.cookies);
        values.put("producto","Cookies");
        values.put("descripcion","Cookies con pepitas de chocolate.");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaPostres",null,values);

        values.put("idImagen",R.drawable.helado_caramel_chew_chew);
        values.put("producto","Helado de caramelo Chew Chew");
        values.put("descripcion","Crema con salsa y trozos de caramelo al cacao. De 150ml o 500ml");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaPostres",null,values);

        values.put("idImagen",R.drawable.helado_chocolate);
        values.put("producto","Helado de chocolate Chew Chew");
        values.put("descripcion","Chocolate con trozos de Brownie. De 150ml o 500ml");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaPostres",null,values);

        values.put("idImagen",R.drawable.trufas);
        values.put("producto","Trufas");
        values.put("descripcion","Trufas");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaPostres",null,values);

        values.put("idImagen",R.drawable.vulcano_plato);
        values.put("producto","Vulcano de chocolate");
        values.put("descripcion","Delicioso bizcocho de chocolate relleno de chocolate fundido.");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaPostres",null,values);

    }

    public void llenarTablaBebidas(SQLiteDatabase sqLiteDatabase) {

        ContentValues values = new ContentValues();

        values.put("idImagen",R.drawable.pepsi);
        values.put("producto","Pepsi");
        values.put("descripcion","El auténtico sabor de la bebida Pepsi®, una refrescante manera de compartir los mejores momentos.");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaBebidas",null,values);

        values.put("idImagen",R.drawable.colombiana);
        values.put("producto","Colombiana");
        values.put("descripcion","Colombiana");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaBebidas",null,values);

        values.put("idImagen",R.drawable.seven_up);
        values.put("producto","7UP");
        values.put("descripcion","7UP");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaBebidas",null,values);

        values.put("idImagen",R.drawable.manzana);
        values.put("producto","Manzana Postobon");
        values.put("descripcion","Manzana Postobon");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaBebidas",null,values);

        values.put("idImagen",R.drawable.club_colombia);
        values.put("producto","Club Colombia Dorada");
        values.put("descripcion","Prohíbese el expendio de bebidas embriagantes a menores de " +
                "edad Ley 124 de 1994. El exceso de alcohol es perjudicial para la salud Ley 30 de 1986.");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaBebidas",null,values);

        values.put("idImagen",R.drawable.cerveza_aguila);
        values.put("producto","Cerveza Aguila");
        values.put("descripcion","Prohíbese el expendio de bebidas embriagantes a menores de " +
                "edad Ley 124 de 1994. El exceso de alcohol es perjudicial para la salud Ley 30 de 1986.");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaBebidas",null,values);

    }
    public void llenarTablaProductos(SQLiteDatabase sqLiteDatabase) {

        ContentValues values = new ContentValues();
        int i=100;//Pizzas

        values.put("id",i++);
        values.put("idImagen",R.drawable.pizza_colombiana);
        values.put("producto","Pizza Colombiana");
        values.put("descripcion","Cebolla, maíz tierno, tocineta y chorizo.");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaProductos",null,values);

        values.put("id",i++);
        values.put("idImagen",R.drawable.hawaiana);
        values.put("producto","Pizza Hawaiana");
        values.put("descripcion","Extraqueso , Jamón y Piña.");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaProductos",null,values);

        values.put("id",i++);
        values.put("idImagen",R.drawable.fiesta_pepperoni);
        values.put("producto","Fiesta Pepperoni");
        values.put("descripcion","Doble pepperoni y extraqueso.");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaProductos",null,values);

        values.put("id",i++);
        values.put("idImagen",R.drawable.honolulu);
        values.put("producto","Pizza Honolulu");
        values.put("descripcion","Jalapeño, jamón, tocineta y piña.");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaProductos",null,values);

        values.put("id",i++);
        values.put("idImagen",R.drawable.vegetariana);
        values.put("producto","Pizza Vegetariana");
        values.put("descripcion","Cebolla, pimentón, champiñón y tomate.");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaProductos",null,values);

        values.put("id",i++);
        values.put("idImagen",R.drawable.bbq);
        values.put("producto","Pizza BBQ");
        values.put("descripcion","Carne molida, tocineta, maíz tierno, salsa BBQ y cebolla.");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaProductos",null,values);

        i=200;//Entrantes

        values.put("id",i++);
        values.put("idImagen",R.drawable.canela_baitz);
        values.put("producto","Canela Baitz");
        values.put("descripcion","Deliciosos panecillos dulces hechos con la receta única de Domino's, espolvoreados con canela y acompañados de Arequipe.");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaProductos",null,values);

        values.put("id",i++);
        values.put("idImagen",R.drawable.palitos_queso);
        values.put("producto","Palitos de queso");
        values.put("descripcion","Exquisitas barritas de pan cubiertas con queso mozarella gratinado y acompañados de Arequipe.");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaProductos",null,values);

        values.put("id",i++);
        values.put("idImagen",R.drawable.arequipe_rolls);
        values.put("producto","Arequipe Rolls");
        values.put("descripcion","Exquisitos rollos recién horneados, rellenos de arequipe y glaseados con el toque Domino's");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaProductos",null,values);

        values.put("id",i++);
        values.put("idImagen",R.drawable.combo_pollo);
        values.put("producto","Combo Pollo");
        values.put("descripcion","Strippers, Kickers y Alitas");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaProductos",null,values);

        values.put("id",i++);
        values.put("idImagen",R.drawable.patatas_grill);
        values.put("producto","Patatas grill");
        values.put("descripcion","Crujientes patatas horneadas acompañadas de una salsa a elegir.");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaProductos",null,values);

        values.put("id",i++);
        values.put("idImagen",R.drawable.ensaladas_pasta_marisco);
        values.put("producto","Ensalas pastos y mariscos");
        values.put("descripcion","Ensalada Mediterránea de pasta al dente con zanahorias, maiz, guisantes, calamares y gambas.");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaProductos",null,values);

        i=300;//Postres

        values.put("id",i++);
        values.put("idImagen",R.drawable.brownie_duo);
        values.put("producto","Brownie Duo");
        values.put("descripcion","Dos raciones de Brownie de chocolate.");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaProductos",null,values);

        values.put("id",i++);
        values.put("idImagen",R.drawable.cookies);
        values.put("producto","Cookies");
        values.put("descripcion","Cookies con pepitas de chocolate.");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaProductos",null,values);

        values.put("id",i++);
        values.put("idImagen",R.drawable.helado_caramel_chew_chew);
        values.put("producto","Helado de caramelo Chew Chew");
        values.put("descripcion","Crema con salsa y trozos de caramelo al cacao. De 150ml o 500ml");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaProductos",null,values);

        values.put("id",i++);
        values.put("idImagen",R.drawable.helado_chocolate);
        values.put("producto","Helado de chocolate Chew Chew");
        values.put("descripcion","Chocolate con trozos de Brownie. De 150ml o 500ml");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaProductos",null,values);

        values.put("id",i++);
        values.put("idImagen",R.drawable.trufas);
        values.put("producto","Trufas");
        values.put("descripcion","Trufas");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaProductos",null,values);

        values.put("id",i++);
        values.put("idImagen",R.drawable.vulcano_plato);
        values.put("producto","Vulcano de chocolate");
        values.put("descripcion","Delicioso bizcocho de chocolate relleno de chocolate fundido.");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaProductos",null,values);

        i=400;//Bebidas

        values.put("id",i++);
        values.put("idImagen",R.drawable.pepsi);
        values.put("producto","Pepsi");
        values.put("descripcion","El auténtico sabor de la bebida Pepsi®, una refrescante manera de compartir los mejores momentos.");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaProductos",null,values);

        values.put("id",i++);
        values.put("idImagen",R.drawable.colombiana);
        values.put("producto","Colombiana");
        values.put("descripcion","Colombiana");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaProductos",null,values);

        values.put("id",i++);
        values.put("idImagen",R.drawable.seven_up);
        values.put("producto","7UP");
        values.put("descripcion","7UP");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaProductos",null,values);

        values.put("id",i++);
        values.put("idImagen",R.drawable.manzana);
        values.put("producto","Manzana Postobon");
        values.put("descripcion","Manzana Postobon");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaProductos",null,values);

        values.put("id",i++);
        values.put("idImagen",R.drawable.club_colombia);
        values.put("producto","Club Colombia Dorada");
        values.put("descripcion","Prohíbese el expendio de bebidas embriagantes a menores de " +
                "edad Ley 124 de 1994. El exceso de alcohol es perjudicial para la salud Ley 30 de 1986.");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaProductos",null,values);

        values.put("id",i++);
        values.put("idImagen",R.drawable.cerveza_aguila);
        values.put("producto","Cerveza Aguila");
        values.put("descripcion","Prohíbese el expendio de bebidas embriagantes a menores de " +
                "edad Ley 124 de 1994. El exceso de alcohol es perjudicial para la salud Ley 30 de 1986.");
        values.put("precio","10000");
        sqLiteDatabase.insert("TablaProductos",null,values);

    }

}
