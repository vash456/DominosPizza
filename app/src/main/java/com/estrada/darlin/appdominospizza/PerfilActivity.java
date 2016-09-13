package com.estrada.darlin.appdominospizza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class PerfilActivity extends AppCompatActivity {

    TextView t_name, t_email;
    private String name, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        t_name = (TextView) findViewById(R.id.t_name);
        t_email = (TextView) findViewById(R.id.t_email);

        Bundle extras = getIntent().getExtras();//para mandar datos

        name = extras.getString("usuario");
        email = extras.getString("email");

        t_name.setText(name);
        t_email.setText(email);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_perfil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.mMainActivity:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("usuario",name);
                intent.putExtra("email",email);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
