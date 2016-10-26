package com.estrada.darlin.appdominospizza;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class PizzasFragment extends ListFragment implements AdapterView.OnItemClickListener {

    /*Productos[] datos= new Productos[]{
            new Productos(R.drawable.pizza_colombiana,"Pizzas 2x1","Participan pizzas de masa original, " +
                    "orilla rellena de queso y crunchy.\nValida solo por el fin de semana.","1000000",1),
            new Productos(R.drawable.hawaiana,"Gratis Arequipe Rolls","Haz tu " +
                    "pedido desde nuestra app y por la compra de una pizza grande te damos " +
                    "arequipe rolls gratis.","100",2),
            new Productos(R.drawable.fiesta_pepperoni,"Descuento de 50 porciento",
                    "Pide una pizza grande masa original de 1 a 9 ingredientes y llevate la " +
                            "segunda al 50% de descuento.","100",3),
            new Productos(R.drawable.honolulu,"Pizza de sartèn a 159$","Pide Pizza " +
                    "de sarten de 2 a 4 ingredientes a tan solo 159$.\nPromo valida solo por " +
                    "internet y app","100",4),
            new Productos(R.drawable.vegetariana,"Pizza de sartèn a 189$","Pizza " +
                    "de sarten de 1 ingredientes màs papotas y canelazo bites a tan solo 189$","100",5)};
*/
    Productos[] datos = new Productos[6];
    DominosSQLiteHelper tablasDominos;
    SQLiteDatabase dbDominos;
    Cursor c;
    SharedPreferences prefs;

    private String name;
    private int idFavorito,idUsuario,idProductos;



    public PizzasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        prefs = this.getActivity().getSharedPreferences("preferencia", Context.MODE_PRIVATE);
        name = String.valueOf(prefs.getString("var_name","Nombre no definido"));

        tablasDominos = new DominosSQLiteHelper(getContext(), "DominosBD", null, 1);
        dbDominos = tablasDominos.getWritableDatabase();


        idProductos = 100;

        for(int i=0;i<6;i++) {
            c = dbDominos.rawQuery("SELECT * FROM TablaProductos WHERE id='"+(idProductos+i)+"'",null);
            if (c.moveToFirst()){
                datos[i] = new Productos(c.getInt(1),c.getString(2),c.getString(3),c.getString(4),c.getInt(0));
            }

        }

        /*c = dbDominos.query("TablaPizzas",null,null,null,null,null,null);
        //c.moveToFirst();
        int i=0;
        while(c.moveToNext()) {

            datos[i] = new Productos(c.getInt(1),c.getString(2),c.getString(3),c.getString(4),c.getInt(0));
            i++;

        }*/

        //Toast.makeText(getActivity(), "cuenta filas = "+c.getCount(), Toast.LENGTH_SHORT).show();

        Adapter adaptador = new Adapter(getContext(), datos);

        setListAdapter(adaptador);

        return super.onCreateView(inflater,container,savedInstanceState);

    }

    public void onStart(){
        super.onStart();
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        getListView().setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        /*switch (position){
            case 0:
                Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
                break;
            case 1:
                createAndShowAlertDialog();
                break;
            default:
                Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
                break;

        }*/


    }

    class Adapter extends ArrayAdapter<Productos> {
        public Adapter(Context context, Productos[] datos) {
            super(context, R.layout.productos_estruct, datos);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            final View item = inflater.inflate(R.layout.productos_estruct, null);

            ImageView imagen = (ImageView) item.findViewById(R.id.iImagenProd);
            imagen.setImageResource(datos[position].getIdImagen());

            TextView nombre = (TextView) item.findViewById(R.id.tName);
            nombre.setText(datos[position].getNombre());
            TextView descripcion = (TextView) item.findViewById(R.id.tDescrip);
            descripcion.setText(datos[position].getDescripcion());
            //descripcion.setText("Click para mas informacion.");
            TextView precio = (TextView) item.findViewById(R.id.tPrecio);
            precio.append(datos[position].getPrecio());

            c = dbDominos.rawQuery("SELECT * FROM DatosUsuarios WHERE nombre='"
                    +name+"'",null);
            if(c.moveToFirst()){
                idUsuario = c.getInt(0);
            }

            c = dbDominos.rawQuery("SELECT * FROM TablaFavoritos WHERE idUsuario='"+idUsuario+
                    "' AND idProducto='"+datos[position].getIdProducto()+"'",null);

            final ImageButton botonfavorito = (ImageButton) item.findViewById(R.id.Imagenbutton);
            if (c.moveToFirst()){
                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.favoritos_buttom_on);
                botonfavorito.setImageBitmap(bmp);
            }
            botonfavorito.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    c = dbDominos.rawQuery("SELECT * FROM TablaFavoritos WHERE idUsuario='" +
                            idUsuario+"' AND idProducto='" +datos[position].getIdProducto()+"'",null);

                    if (c.moveToFirst()){
                        idFavorito = c.getInt(0);
                        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.favoritos_buttom_off);
                        botonfavorito.setImageBitmap(bmp);
                        dbDominos.delete("TablaFavoritos","idFavorito='"+idFavorito+"'",null);
                    }else {
                        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.favoritos_buttom_on);
                        botonfavorito.setImageBitmap(bmp);
                        dbDominos.execSQL("INSERT INTO TablaFavoritos VALUES(null, '"+idUsuario+
                                "', '"+datos[position].getIdProducto()+"')");
                        Toast.makeText(getActivity(), "Agregado a Favoritos", Toast.LENGTH_SHORT).show();
                    }



                }

            });

            return (item);
        }
    }

    private void createAndShowAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("My Title");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getActivity(), "cancelar", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
