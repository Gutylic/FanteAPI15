package com.unprofesorya.fante;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Actividad8 extends AppCompatActivity implements View.OnClickListener {

    TextView Usuario;
    TextView Plata;
    Button Salir;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad8);

        pref = getSharedPreferences("Datos", Context.MODE_PRIVATE);
        Usuario = (TextView) findViewById(R.id.celular);
        Plata = (TextView) findViewById(R.id.credito);

        Plata.setText("$: " + pref.getString("dato2",""));
        Usuario.setText(pref.getString("dato4",""));

        Salir = (Button) findViewById(R.id.button12);
        Salir.setOnClickListener(this);

    }

    public void onClick(View v){

        AlertDialog.Builder dialogo = new AlertDialog.Builder(Actividad8.this);
        dialogo.setTitle("Mensaje");
        dialogo.setCancelable(false);
        dialogo.setMessage("¿Queres enviarnos tus ejerciciosn ahora?");
        dialogo.setPositiveButton("No, en otro momento", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo, int id) {
                finish();
            }
        });

        dialogo.setNegativeButton("Si, quiero unas respuestas", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo, int id) {

                Intent intent = new Intent(Actividad8.this,Actividad4.class);
                startActivity(intent);
            }
        });
        dialogo.show();

    }

}
