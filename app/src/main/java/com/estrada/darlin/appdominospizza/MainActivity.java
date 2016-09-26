package com.estrada.darlin.appdominospizza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView t_name;
    private String name, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toast.makeText(this,"onCreate",Toast.LENGTH_LONG).show();

        Bundle extras = getIntent().getExtras();

        t_name = (TextView) findViewById(R.id.t_name);

        name = extras.getString("usuario");
        email = extras.getString("email");
        t_name.setText("Bienvenido "+name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

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

}
