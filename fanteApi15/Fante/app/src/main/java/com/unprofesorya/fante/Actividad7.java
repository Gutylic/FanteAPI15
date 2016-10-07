package com.unprofesorya.fante;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.IdRes;
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

public class Actividad7 extends AppCompatActivity implements View.OnClickListener {

    TextView Usuario;
    TextView Plata;
    Button tarjetaPrepaga;
    EditText codigoTarjeta;
    String resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad7);

        SharedPreferences pref = getSharedPreferences("Datos", Context.MODE_PRIVATE);

        Usuario = (TextView) findViewById(R.id.celular);
        Plata = (TextView) findViewById(R.id.credito);


        Usuario.setText(pref.getString("dato4","0"));
        Plata.setText("$: " + pref.getString("dato2","0"));

        codigoTarjeta = (EditText) findViewById(R.id.editText7);
        tarjetaPrepaga = (Button) findViewById(R.id.button12);

        tarjetaPrepaga.setOnClickListener(this);

    }

    public void onClick (View v){

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(codigoTarjeta.getWindowToken(), 0);

        SharedPreferences pref = getSharedPreferences("Datos", Context.MODE_PRIVATE);
        HilosAndroid Hilos = new HilosAndroid();
        Hilos.execute(pref.getString("dato1",""),codigoTarjeta.getText().toString());

    }

    private class HilosAndroid extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... Param) {

            String SOAP_ACTION = "http://xelados.net/tarjetaPrepagaCelular";
            String NAMESPACE = "http://xelados.net/";
            String METHOD_NAME = "tarjetaPrepagaCelular";
            String URL = "http://www.xelados.net/Servicio.asmx";


            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("id", Param[0]);
            request.addProperty("codigoTarjeta", Param[1]);



            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(URL);

            try {
                transporte.call(SOAP_ACTION, envelope);
                SoapPrimitive resultado_xml = (SoapPrimitive) envelope.getResponse();
                resultado = resultado_xml.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();

            }

            return resultado;
        }


        @Override
        protected void onPostExecute(String resultado) {

            super.onPostExecute(resultado);

            if (resultado.compareTo("-1") != 0){

                AlertDialog.Builder dialogo = new AlertDialog.Builder(Actividad7.this);
                dialogo.setTitle("tu crédtio se cargo exitosamente");
                dialogo.setCancelable(false);
                dialogo.setMessage("¿Queres enviarnos tus ejercicios ahora?");
                dialogo.setPositiveButton("No, En otro momento", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo, int id) {
                        finish();

                    }
                });

                dialogo.setNegativeButton("Si, quiero unas respuestas", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo, int id) {
                        Intent intent = new Intent(Actividad7.this,Actividad4.class);
                        startActivity(intent);
                    }
                });
                dialogo.show();
            }

            if (resultado.compareTo("-1") == 0){

                AlertDialog.Builder dialogo = new AlertDialog.Builder(Actividad7.this);
                dialogo.setTitle("UPS... la tarjeta parece no ser válida");
                dialogo.setCancelable(false);
                dialogo.setMessage("¿Quieres interlo nuevamente?");
                dialogo.setPositiveButton("No, En otro momento", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo, int id) {
                        Intent intent = new Intent(Actividad7.this,Actividad4.class);
                        startActivity(intent);
                    }
                });

                dialogo.setNegativeButton("Si, ahora", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo, int id) {
                        finish();
                    }
                });
                dialogo.show();
            }

        }


    }




}

