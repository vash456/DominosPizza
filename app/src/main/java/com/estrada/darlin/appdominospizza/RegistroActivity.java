package com.estrada.darlin.appdominospizza;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {

    DominosSQLiteHelper usuarios;
    SQLiteDatabase dbUsuarios;
    ContentValues dataBD;

    private Button b_aceptar, b_cancelar;
    private EditText et_usuario, et_password1, et_password2, et_email;
    private String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        usuarios = new DominosSQLiteHelper(this, "DominosBD", null, 1);
        dbUsuarios = usuarios.getWritableDatabase();

        b_aceptar = (Button) findViewById(R.id.b_aceptar);
        b_cancelar = (Button) findViewById(R.id.b_cancelar);
        et_usuario = (EditText) findViewById(R.id.et_usuario);
        et_password1 = (EditText) findViewById(R.id.et_password1);
        et_password2 = (EditText) findViewById(R.id.et_password2);
        et_email = (EditText) findViewById(R.id.et_email);

        nombre = et_usuario.getText().toString();

        b_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password1 = et_password1.getText().toString();

                if (TextUtils.isEmpty(et_usuario.getText().toString())){
                    et_usuario.setError("Este campo no puede estar vacio");
                    return;
                }
                if (TextUtils.isEmpty(et_password1.getText().toString())){
                    et_password1.setError("Este campo no puede estar vacio");
                    return;
                }
                if (TextUtils.isEmpty(et_password2.getText().toString())){
                    et_password2.setError("Este campo no puede estar vacio");
                    return;
                }
                if (password1.equals(et_password2.getText().toString()) != true){
                    et_password2.setError("La contraseña debe ser la misma");
                    et_password2.setText("");
                    return;
                }
                if (TextUtils.isEmpty(et_email.getText().toString())){
                    et_email.setError("Este campo no puede estar vacio");
                    return;
                }

                //verifica si el usuario estaba ya registrado
                Cursor c = dbUsuarios.rawQuery("SELECT * FROM DatosUsuarios WHERE nombre='"+
                        nombre+"'",null);
                if(c.moveToFirst()){
                    Toast.makeText(getApplicationContext(),"Usuario existente", Toast.LENGTH_SHORT).show();
                    return;
                }

                dataBD = new ContentValues();
                dataBD.put("nombre", et_usuario.getText().toString());
                dataBD.put("password", et_password1.getText().toString());
                dataBD.put("correo", et_email.getText().toString());

                dbUsuarios.insert("DatosUsuarios", null, dataBD);

                Intent intent = new Intent();
                //intent.putExtra("usuario", et_usuario.getText().toString());
                //intent.putExtra("contrasena", et_password1.getText().toString());
                //intent.putExtra("email", et_email.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        b_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED,intent);
                finish();
            }
        });
    }
}
