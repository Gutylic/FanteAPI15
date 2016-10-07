package com.unprofesorya.fante;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Actividad0 extends AppCompatActivity implements View.OnClickListener {

    Button bt1;
    Button bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad0);

        SharedPreferences pref = getSharedPreferences("Datos", Context.MODE_PRIVATE);
        Boolean Pagina = pref.getBoolean("dato3",false);

        if(Pagina == true)
        {
            Intent intent = new Intent(this,Actividad4.class);
            startActivity(intent);
        }

        bt1 = (Button) findViewById(R.id.boton1);
        bt1.setOnClickListener(this);

        bt2 = (Button) findViewById(R.id.boton2);
        bt2.setOnClickListener(this);
    }

    public void onClick (View v){

        if (v== bt1) {
            Toast.makeText(getApplicationContext(),"Te esperamos pronto...",Toast.LENGTH_SHORT).show();
            finish();
        }
        if (v== bt2){
           Intent intent = new Intent(this,Actividad1.class);
           startActivity(intent);
        }
    }
}

