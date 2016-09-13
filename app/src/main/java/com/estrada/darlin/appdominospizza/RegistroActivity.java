package com.estrada.darlin.appdominospizza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistroActivity extends AppCompatActivity {

    private Button b_aceptar, b_cancelar;
    private EditText et_usuario, et_password1, et_password2, et_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        b_aceptar = (Button) findViewById(R.id.b_aceptar);
        b_cancelar = (Button) findViewById(R.id.b_cancelar);
        et_usuario = (EditText) findViewById(R.id.et_usuario);
        et_password1 = (EditText) findViewById(R.id.et_password1);
        et_password2 = (EditText) findViewById(R.id.et_password2);
        et_email = (EditText) findViewById(R.id.et_email);

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

                //Intent intent = new Intent(getApplication(), LogginActivity.class);//1
                Intent intent = new Intent();//2
                //Intent intent = new Intent(getApplicationContext(), LogginActivity.class); //otra forma
                intent.putExtra("usuario", et_usuario.getText().toString());
                intent.putExtra("contrasena", et_password1.getText().toString());
                intent.putExtra("email", et_email.getText().toString());
                //startActivity(intent);//1
                setResult(RESULT_OK,intent);//2
                finish();//2
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
