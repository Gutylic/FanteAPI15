package com.unprofesorya.fante;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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


public class Actividad4 extends AppCompatActivity implements View.OnClickListener {

    TextView Usuario;
    TextView Plata;
    Button boton1;
    Button boton2;
    String resultado;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad4);

        Usuario = (TextView) findViewById(R.id.celular);
        Plata = (TextView) findViewById(R.id.credito);

        boton1 = (Button) findViewById(R.id.button2);
        boton1.setOnClickListener(this);

        boton2 = (Button) findViewById(R.id.button4);
        boton2.setOnClickListener(this);

        pref = getSharedPreferences("Datos", Context.MODE_PRIVATE);

        Usuario.setText(pref.getString("dato4",""));
        Plata.setText("$: " + pref.getString("dato2",""));

        Log.e("datos4===>",pref.getString("dato4",pref.getString("dato4","")));
        Log.e("datos2===>",pref.getString("dato2",pref.getString("dato2","")));
        Log.e("datos1===>",pref.getString("dato1",pref.getString("dato1","")));

        Log.e("datos===>",pref.getString("dato","0"));


        HilosAndroid Hilos = new HilosAndroid();
        Hilos.execute(pref.getString("dato1","0"));



    }

    public void onClick(View v){


        if(v == boton1 ){

            Intent intent = new Intent(Actividad4.this,Actividad5.class);
            startActivity(intent);
        }

        if(v == boton2 ){

            Intent intent = new Intent(Actividad4.this,Actividad6.class);
            startActivity(intent);

        }

    }

    private class HilosAndroid extends AsyncTask<String,Integer,String> {


        @Override
        protected String doInBackground(String... Param) {

            String SOAP_ACTION = "http://xelados.net/usuarioCelularId";
            String NAMESPACE = "http://xelados.net/";
            String METHOD_NAME = "usuarioCelularId";
            String URL = "http://www.xelados.net/Servicio.asmx";

            Log.e("parametro===>",Param[0].toString());

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("id", Param[0].toString());

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
        protected void onPostExecute(String resultado){

            Log.e("resultadoout===>",resultado);
            if (resultado.compareTo("0") == 0 )
            {
                Log.e("resultadoin===>",resultado);

                pref = getSharedPreferences("Datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();

                editor.putBoolean("dato3",false);
                editor.clear();
                editor.commit();

                Intent intent = new Intent(Actividad4.this,Actividad0.class);
                startActivity(intent);
                return;
            }

            String[] temp;
            temp = resultado.split("x");
            Integer Variable = temp.length;
            Log.e("resultadofuera==>",pref.getString("dato4",""));
            if (pref.getString("dato4","").compareTo(temp[0]) != 0)
            {
                Log.e("resultadoadentro==>",temp[0]);

                pref = getSharedPreferences("Datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();

                editor.putBoolean("dato3",false);
                editor.clear();
                editor.commit();

                Intent intent = new Intent(Actividad4.this,Actividad0.class);
                startActivity(intent);
                return;
            }


            pref = getSharedPreferences("Datos", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();

            Plata.setText("$: " + temp[1]);

            editor.putString("dato2",temp[1]);

            editor.commit();

        }

        @Override
        protected void onCancelled(){
            super.onCancelled();
            Toast.makeText(getBaseContext(), "Ups... cancelaste la aplicación", Toast.LENGTH_SHORT).show();
        }

    }

}