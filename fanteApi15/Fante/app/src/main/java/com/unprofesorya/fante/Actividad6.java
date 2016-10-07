package com.unprofesorya.fante;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class Actividad6 extends AppCompatActivity implements View.OnClickListener {

    String resultado="";
    TextView Usuario;
    TextView Plata;
    Button SOS;
    Button tarjetaPrepaga;
    Button Bancaria;
    SharedPreferences pref;
    Button Rapipago;
    Button MercaPago;
    Button PayPal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad6);

        pref = getSharedPreferences("Datos", Context.MODE_PRIVATE);
        Usuario = (TextView) findViewById(R.id.celular);
        Plata = (TextView) findViewById(R.id.credito);

        Plata.setText("$: " + pref.getString("dato2",""));
        Usuario.setText(pref.getString("dato4",""));

        SOS = (Button) findViewById(R.id.button7);
        tarjetaPrepaga = (Button) findViewById(R.id.button6);
        Bancaria = (Button) findViewById(R.id.button8);
        Rapipago = (Button) findViewById(R.id.button9);
        MercaPago = (Button) findViewById(R.id.button10);
        PayPal = (Button) findViewById(R.id.button11);

        SOS.setOnClickListener(this);
        tarjetaPrepaga.setOnClickListener(this);
        Bancaria.setOnClickListener(this);
        Rapipago.setOnClickListener(this);
        MercaPago.setOnClickListener(this);
        PayPal.setOnClickListener(this);

    }

    public void onClick(View v){
        if (v == SOS ){

            HilosAndroid Hilos = new HilosAndroid();
            Hilos.execute(pref.getString("dato1",""));

        }
        if (v == tarjetaPrepaga ){

            Intent intent = new Intent(Actividad6.this,Actividad7.class);
            startActivity(intent);
        }
        if (v == Bancaria ){

            Intent intent = new Intent(Actividad6.this,Actividad8.class);
            startActivity(intent);
        }

        if (v == Rapipago ){

            Toast.makeText(getApplicationContext(),"Espere unos segundos",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Actividad6.this,Actividad9.class);
            startActivity(intent);
        }

        if (v == MercaPago ){

            Toast.makeText(getApplicationContext(),"Espere unos segundos",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Actividad6.this,Actividad10.class);
            startActivity(intent);
        }

        if (v == PayPal ){

            Toast.makeText(getApplicationContext(),"Espere unos segundos",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Actividad6.this,Actividad11.class);
            startActivity(intent);
        }
    }

    private class HilosAndroid extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... Param) {

            String SOAP_ACTION = "http://xelados.net/pedidoSOSCelular";
            String NAMESPACE = "http://xelados.net/";
            String METHOD_NAME = "pedidoSOSCelular";
            String URL = "http://www.xelados.net/Servicio.asmx";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("id", Param[0]);

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

            if (Double.parseDouble(resultado) != -1.00){

                Double Dinero = Double.parseDouble(pref.getString("dato2","0"));
                SharedPreferences pref = getSharedPreferences("Datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();

                editor.putString("dato2",String.valueOf(Dinero + Double.parseDouble(resultado)));
                editor.commit();

                AlertDialog.Builder dialogo = new AlertDialog.Builder(Actividad6.this);
                dialogo.setTitle("Recibiste tu prestamo");
                dialogo.setMessage("¿Queres enviarnos tus ejercicios ahora?");
                dialogo.setCancelable(false);

                dialogo.setNegativeButton("No, En otro momento", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo, int id) {
                        finish();

                    }
                });
                dialogo.setPositiveButton("Si, quiero unas respuestas", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo, int id) {
                        // finish();
                        Intent intent = new Intent(Actividad6.this,Actividad4.class);
                        startActivity(intent);
                    }
                });
                dialogo.show();

            }
            if (Double.parseDouble(resultado) == -1.00){

                AlertDialog.Builder dialogo = new AlertDialog.Builder(Actividad6.this);
                dialogo.setTitle("Ya recibiste tu prestamo");
                dialogo.setMessage("¿Queres enviarnos tus ejercicios ahora?");
                dialogo.setCancelable(false);

                dialogo.setNegativeButton("No, En otro momento", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo, int id) {
                        finish();

                    }
                });
                dialogo.setPositiveButton("Si, quiero unas respuestas", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo, int id) {
                        // finish();
                        Intent intent = new Intent(Actividad6.this,Actividad4.class);
                        startActivity(intent);
                    }
                });
                dialogo.show();
            }

            Plata.setText("$: " + pref.getString("dato2",""));


        }

    }

}
