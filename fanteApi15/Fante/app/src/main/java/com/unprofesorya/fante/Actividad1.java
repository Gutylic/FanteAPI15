package com.unprofesorya.fante;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Actividad1 extends AppCompatActivity implements View.OnClickListener {

    Button bt1;
    Button bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad1);

        bt1 = (Button) findViewById(R.id.boton1);
        bt1.setOnClickListener(this);

        bt2 = (Button) findViewById(R.id.boton2);
        bt2.setOnClickListener(this);

    }

    public void onClick (View v){

        if (v == bt1) {
            Intent intent = new Intent(this,Actividad3.class);
            startActivity(intent);
        }
        if (v == bt2){
            Intent intent = new Intent(this,Actividad2.class);
            startActivity(intent);
        }

    }

}

