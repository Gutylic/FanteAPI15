package com.unprofesorya.fante;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class Actividad5 extends AppCompatActivity implements View.OnClickListener {

    TextView Usuario;
    TextView Plata;
    EditText Codigo;
    EditText Numero;
    Button Recomendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad5);

        Usuario = (TextView) findViewById(R.id.celular);
        Plata = (TextView) findViewById(R.id.credito);

        SharedPreferences pref = getSharedPreferences("Datos", Context.MODE_PRIVATE);

        Codigo = (EditText) findViewById(R.id.editText3);
        Numero = (EditText) findViewById(R.id.editText4);

        Usuario.setText(pref.getString("dato4","0"));
        Plata.setText("$: " + pref.getString("dato2","0"));

        Recomendar = (Button) findViewById(R.id.button5);
        Recomendar.setOnClickListener(this);

    }

    public void onClick(View v){

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(Numero.getWindowToken(), 0);

        String numero = Numero.getText().toString().substring(0,1);

        String codigopais = Codigo.getText().toString().substring(0,1);

        if (numero.compareTo("0") == 0){
            Toast.makeText(getApplicationContext(), "Debes quitar el cero inicial", Toast.LENGTH_SHORT).show();
            return;
        }

        if (codigopais.compareTo("+") != 0){
            Toast.makeText(getApplicationContext(), "Debes poner un + en el código del país", Toast.LENGTH_SHORT).show();
            return;
        }


        if (Numero.length() <= 9){
            Toast.makeText(getApplicationContext(), "No escribiste bien tu número de Whatsapp", Toast.LENGTH_SHORT).show();
            return;
        }


        SharedPreferences pref = getSharedPreferences("Datos", Context.MODE_PRIVATE);
        HilosAndroid Hilos = new HilosAndroid();
        Hilos.execute(Codigo.getText().toString(),Numero.getText().toString(),pref.getString("dato1",""));
    }


    private class HilosAndroid extends AsyncTask<String, Void, Void> {


        @Override
        protected Void doInBackground(String... Param) {

            String SOAP_ACTION = "http://xelados.net/usuarioRecomendadoCelular";
            String NAMESPACE = "http://xelados.net/";
            String METHOD_NAME = "usuarioRecomendadoCelular";
            String URL = "http://www.xelados.net/Servicio.asmx";


            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("codigo", Param[0]);
            request.addProperty("numero", Param[1]);
            request.addProperty("id", Param[2]);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(URL);

            try {
                transporte.call(SOAP_ACTION, envelope);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();

            }

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);

            Toast.makeText(getBaseContext(),"Gracias por tu recomendación",Toast.LENGTH_LONG).show();

            AlertDialog.Builder dialogo = new AlertDialog.Builder(Actividad5.this);
            dialogo.setTitle("Mensaje");
            dialogo.setMessage("¿queres recomendar otro contacto y ganar más crédito?");
            dialogo.setCancelable(false);

            dialogo.setPositiveButton("Recomendar otro contacto", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo, int id) {
                    // finish();
                    Intent intent = new Intent(Actividad5.this,Actividad5.class);
                    startActivity(intent);
                }
            });

            dialogo.setNegativeButton("No, por el momento", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo, int id) {
                    // finish();
                    Intent intent = new Intent(Actividad5.this,Actividad4.class);
                    startActivity(intent);
                }
            });
            dialogo.show();

        }


    }










}
