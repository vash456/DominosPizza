package com.estrada.darlin.appdominospizza;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritosFragment extends ListFragment implements AdapterView.OnItemClickListener {

    Productos[] datos;

    DominosSQLiteHelper tablasDominos;
    SQLiteDatabase dbDominos;
    Cursor c;
    SharedPreferences prefs;

    int position1;

    Adapter adaptador;

    private String name;
    private int idUsuario, idFavorito, countProFav = 0;

    TextView t_fav;

    public FavoritosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View v = inflater.inflate(R.layout.fragment_favoritos, container, false);

        prefs = this.getActivity().getSharedPreferences("preferencia", Context.MODE_PRIVATE);
        name = String.valueOf(prefs.getString("var_name","Nombre no definido"));

        tablasDominos = new DominosSQLiteHelper(getContext(), "DominosBD", null, 1);
        dbDominos = tablasDominos.getWritableDatabase();

        //t_fav = (TextView) v.findViewById(R.id.t_Fav);

        c = dbDominos.rawQuery("SELECT * FROM DatosUsuarios WHERE nombre='"
                +name+"'",null);
        if(c.moveToFirst()){
            idUsuario = c.getInt(0);
        }

        c = dbDominos.query("TablaFavoritos",null,"idUsuario='"+idUsuario+"'",null,null,null,null);

        ArrayList<Integer> arrayListIdsProductos = new ArrayList<Integer>();

        while(c.moveToNext()) {
            //t_fav.append(c.getString(0) + "|"+c.getString(1) + "|"+c.getString(2)
            //        +"|"+c.getString(3)+"|"+c.getString(4)+"\n");
            //t_fav.append(c.getString(0) + "|"+c.getString(1) + "|"+c.getString(2)+"\n");

            arrayListIdsProductos.add(c.getInt(2));
            countProFav++;
        }

        Collections.sort(arrayListIdsProductos);//ordena de menor a mayor los elementos del array

        //int[] idsProductos = new int[countProFav];
        datos = new Productos[countProFav];

        /*c = dbDominos.query("TablaFavoritos",null,"idUsuario='"+idUsuario+"'",null,null,null,null);

        int count=0;
        while(c.moveToNext()) {
            idsProductos[count] = c.getInt(2);
            count++;
        }*/

        for(int i=0;i<countProFav;i++) {
            c = dbDominos.rawQuery("SELECT * FROM TablaProductos WHERE id='"+arrayListIdsProductos.get(i)+"'",null);
            if (c.moveToFirst()){
                datos[i] = new Productos(c.getInt(1),c.getString(2),c.getString(3),c.getString(4),c.getInt(0));
            }

        }



        adaptador = new Adapter(getContext(), datos);

        setListAdapter(adaptador);

        //return v;
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
                createAndShowAlertDialog();
                break;
            case 1:
                //createAndShowAlertDialog();
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
                        adaptador.notifyDataSetChanged();
                        //Toast.makeText(getActivity(), "Eliminado de Favoritos", Toast.LENGTH_SHORT).show();
                        //createAndShowAlertDialog();
                    }else {
                        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.favoritos_buttom_on);
                        botonfavorito.setImageBitmap(bmp);
                        dbDominos.execSQL("INSERT INTO TablaFavoritos VALUES(null, '"+idUsuario+
                                "', '"+datos[position].getIdProducto()+"')");
                        //Toast.makeText(getActivity(), "Agregado a Favoritos", Toast.LENGTH_SHORT).show();
                    }



                }

            });

            return (item);
        }

    }

    private void createAndShowAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("¿Desea eliminar de favoritos?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getActivity(), "Eliminado de favoritos", Toast.LENGTH_SHORT).show();
                //Object toRemove = adaptador.getItemViewType(position1);
                //Intent intent = new Intent(getContext(), PerfilActivity.class);
                //intent.putExtra("tabFlag",true);
                //startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Toast.makeText(getActivity(), "cancelar", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
