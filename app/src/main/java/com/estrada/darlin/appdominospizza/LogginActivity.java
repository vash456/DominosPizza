package com.estrada.darlin.appdominospizza;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogginActivity extends AppCompatActivity implements View.OnClickListener {

    Button b_entrar, b_registro;
    EditText et_usuario, et_password;

    private String user;
    private String password;
    private String email;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggin);

        b_registro = (Button) findViewById(R.id.b_registro);
        b_entrar = (Button) findViewById(R.id.b_entrar);
        et_usuario = (EditText) findViewById(R.id.et_usuario);
        et_password = (EditText) findViewById(R.id.et_password);


        ////////////////////////////esta parte es solo para recibir
       /* Bundle extras = getIntent().getExtras();//para mandar datos

        String user = extras.getString("usuario");
        String contrasena = extras.getString("contrasena");
        /////////////////////////

        Toast.makeText(this, "user: "+user+"contrasena: "+contrasena,Toast.LENGTH_SHORT).show();

        *///1

        b_registro.setOnClickListener(this);//si aparece error seleccionar en la segunda opcion
        b_entrar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.b_registro:
                Intent intent = new Intent(this, RegistroActivity.class);
                //startActivity(intent);//1
                startActivityForResult(intent, 1234);//2
                break;

            case R.id.b_entrar:
                if (flag == true){
                    if (TextUtils.isEmpty(et_usuario.getText().toString())) {
                        et_usuario.setError("Este campo no puede estar vacio");
                        return;
                    }
                    if (TextUtils.isEmpty(et_password.getText().toString())) {
                        et_password.setError("Este campo no puede estar vacio");
                        return;
                    }
                    if (user.equals(et_usuario.getText().toString())){
                        if (password.equals(et_password.getText().toString())){
                            Intent intent2 = new Intent(this, MainActivity.class);
                            intent2.putExtra("usuario", user);
                            intent2.putExtra("email", email);
                            startActivity(intent2);
                            finish();//2
                        }else {
                            Toast.makeText(this, "Usuario o contraseña incorrecta.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }else {
                        Toast.makeText(this, "Usuario o contraseña incorrecta.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }else Toast.makeText(this, "No hay usuarios registrados",Toast.LENGTH_SHORT).show();

                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//2
        if (requestCode == 1234 && resultCode == RESULT_OK){
            user = data.getExtras().getString("usuario");
            password = data.getExtras().getString("contrasena");
            email = data.getExtras().getString("email");

            flag = true;
            /////////////////////////

            Log.d("user",user);//para mostrar datos en consola android
            Log.d("contraseña",password);

            Toast.makeText(this, "Usuario registrado exitosamente.",Toast.LENGTH_SHORT).show();
        }

        if (requestCode==1234 && resultCode == RESULT_CANCELED){
            Log.d("mensaje","no se cargaron datos");
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
