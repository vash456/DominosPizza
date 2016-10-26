package com.estrada.darlin.appdominospizza;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

/**
 * Created by DARLIN on 18/10/2016.
 */
public class DominosSQLiteHelper extends SQLiteOpenHelper {
    private static final String DATA_BASE_HOME = "DominosBD";
    private static final int DATA_VERSION = 1;

    private static final String CREATE_TABLE_USUARIOS = "CREATE TABLE DatosUsuarios (" +
            "id         INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "nombre     TEXT,"+
            "password   TEXT,"+
            "correo     TEXT)";
    private static final String CREATE_TABLE_PIZZAS = "CREATE TABLE TablaPizzas (" +
            "id         INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "idImagen   INTEGER,"+
            "producto     TEXT,"+
            "descripcion   TEXT,"+
            "precio     TEXT)";
    private static final String CREATE_TABLE_ENTRANTES = "CREATE TABLE TablaEntrantes (" +
            "id         INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "idImagen   INTEGER,"+
            "producto     TEXT,"+
            "descripcion   TEXT,"+
            "precio     TEXT)";
    private static final String CREATE_TABLE_POSTRES = "CREATE TABLE TablaPostres (" +
            "id         INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "idImagen   INTEGER,"+
            "producto     TEXT,"+
            "descripcion   TEXT,"+
            "precio     TEXT)";
    private static final String CREATE_TABLE_BEBIDAS = "CREATE TABLE TablaBebidas (" +
            "id         INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "idImagen   INTEGER,"+
            "producto     TEXT,"+
            "descripcion   TEXT,"+
            "precio     TEXT)";
    private static final String CREATE_TABLE_PRODUCTOS = "CREATE TABLE TablaProductos (" +
            "id         INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "idImagen   INTEGER,"+
            "producto     TEXT,"+
            "descripcion   TEXT,"+
            "precio     TEXT)";
    private static final String CREATE_TABLE_FAVORITOS = "CREATE TABLE TablaFavoritos (" +
            "idFavorito  INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "idUsuario   INTEGER,"+
            "idProducto  INTEGER)";


    public DominosSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onOpen(SQLiteDatabase sqLiteDatabase) {
        super.onOpen(sqLiteDatabase);
        if (!sqLiteDatabase.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                sqLiteDatabase.setForeignKeyConstraintsEnabled(true);
            } else {
                sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON");
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USUARIOS);
        sqLiteDatabase.execSQL(CREATE_TABLE_PIZZAS);
        sqLiteDatabase.execSQL(CREATE_TABLE_ENTRANTES);
        sqLiteDatabase.execSQL(CREATE_TABLE_POSTRES);
        sqLiteDatabase.execSQL(CREATE_TABLE_BEBIDAS);
        sqLiteDatabase.execSQL(CREATE_TABLE_PRODUCTOS);
        sqLiteDatabase.execSQL(CREATE_TABLE_FAVORITOS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IS EXISTS DatosUsuarios");
        sqLiteDatabase.execSQL("DROP TABLE IS EXISTS TablaPizzas");
        sqLiteDatabase.execSQL("DROP TABLE IS EXISTS TablaEntrantes");
        sqLiteDatabase.execSQL("DROP TABLE IS EXISTS TablaPostres");
        sqLiteDatabase.execSQL("DROP TABLE IS EXISTS TablaBebidas");
        sqLiteDatabase.execSQL("DROP TABLE IS EXISTS TablaProductos");
        sqLiteDatabase.execSQL("DROP TABLE IS EXISTS TablaFavoritos");
        sqLiteDatabase.execSQL(CREATE_TABLE_USUARIOS);
        sqLiteDatabase.execSQL(CREATE_TABLE_PIZZAS);
        sqLiteDatabase.execSQL(CREATE_TABLE_ENTRANTES);
        sqLiteDatabase.execSQL(CREATE_TABLE_POSTRES);
        sqLiteDatabase.execSQL(CREATE_TABLE_BEBIDAS);
        sqLiteDatabase.execSQL(CREATE_TABLE_PRODUCTOS);
        sqLiteDatabase.execSQL(CREATE_TABLE_FAVORITOS);
    }


}
