package com.unprofesorya.fante;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Actividad2 extends AppCompatActivity implements View.OnClickListener {

    Spinner pais;
    TextView codigoPais;
    EditText numeroCelular;
    EditText contrasenaUsuario;
    Button logueoUsuario;
    Button olvidarContrasena;

    String datos[] = {"Argentina","Mexico","Peru","Colombia","Uruguay","Brasil","Bolivia","España",
            "Chile","Cuba","Costa Rica","Ecuador","EEUU","Belice","El Salvador",
            "Guatemala","Honduras","Panama","Paraguay","Puerto Rico","Nicaragua",
            "Republica Dominicana","Venezuela"};
    String resultado = "";
    Integer codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad2);


        numeroCelular = (EditText) findViewById(R.id.numeroCelular);
        codigoPais = (TextView) findViewById(R.id.codigoPais);

        pais = (Spinner) findViewById(R.id.paisSpinner);

        contrasenaUsuario = (EditText) findViewById(R.id.contrasenaUsuario);

        logueoUsuario = (Button) findViewById(R.id.button3);
        logueoUsuario.setOnClickListener(this);

        olvidarContrasena = (Button) findViewById(R.id.button13);
        olvidarContrasena.setOnClickListener(this);



        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.activity_spinner_item, datos);
        arrayAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        pais.setAdapter(arrayAdapter);

        pais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                switch (i) {

                    case 0:
                        codigoPais.setText("+54");
                        break;
                    case 1:
                        codigoPais.setText("+52");
                        break;
                    case 2:
                        codigoPais.setText("+51");
                        break;
                    case 3:
                        codigoPais.setText("+57");
                        break;
                    case 4:
                        codigoPais.setText("+598");
                        break;
                    case 5:
                        codigoPais.setText("+55");
                        break;
                    case 6:
                        codigoPais.setText("+591");
                        break;
                    case 7:
                        codigoPais.setText("+34");
                        break;
                    case 8:
                        codigoPais.setText("+56");
                        break;
                    case 9:
                        codigoPais.setText("+53");
                        break;
                    case 10:
                        codigoPais.setText("+506");
                        break;
                    case 11:
                        codigoPais.setText("+593");
                        break;
                    case 12:
                        codigoPais.setText("+1");
                        break;
                    case 13:
                        codigoPais.setText("+501");
                        break;
                    case 14:
                        codigoPais.setText("+503");
                        break;
                    case 15:
                        codigoPais.setText("+502");
                        break;
                    case 16:
                        codigoPais.setText("+504");
                        break;
                    case 17:
                        codigoPais.setText("+507");
                        break;
                    case 18:
                        codigoPais.setText("+595");
                        break;
                    case 19:
                        codigoPais.setText("+1");
                        break;
                    case 20:
                        codigoPais.setText("+505");
                        break;
                    case 21:
                        codigoPais.setText("+1");
                        break;
                    case 22:
                        codigoPais.setText("+58");
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

    }


    public void onClick (View v){

        if (v == logueoUsuario){

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(contrasenaUsuario.getWindowToken(),0);

            String numero = numeroCelular.getText().toString().substring(0,1);

            if (numero.compareTo("0") == 0){
                Toast.makeText(getApplicationContext(), "Debes quitar el cero inicial", Toast.LENGTH_SHORT).show();
                return;
            }


            if (numeroCelular.length() <= 9){
                Toast.makeText(getApplicationContext(), "No escribiste bien tu número de Whatsapp", Toast.LENGTH_SHORT).show();
                return;
            }

            if (contrasenaUsuario.getText().toString().matches(""))
            {
                Toast.makeText(getApplicationContext(),"Contraseña vacia",Toast.LENGTH_SHORT).show();
                return;
            }

            if (numeroCelular.getText().toString().matches(""))
            {
                Toast.makeText(getApplicationContext(),"Numero vacio",Toast.LENGTH_SHORT).show();
                return;
            }

            HilosAndroid Hilos = new HilosAndroid();
            Hilos.execute(codigoPais.getText().toString(),numeroCelular.getText().toString(),contrasenaUsuario.getText().toString());

        }

        if (v == olvidarContrasena){

            olvidarContrasena.setEnabled(false);
            olvidarContrasena.setText("espere unos minutos");
            logueoUsuario.setEnabled(false);
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(contrasenaUsuario.getWindowToken(),0);

            HilosAndroid1 Hilos1 = new HilosAndroid1();
            Hilos1.execute(codigoPais.getText().toString(),numeroCelular.getText().toString());


        }


    }

    private class HilosAndroid extends AsyncTask<String,Integer,String> {


        @Override
        protected String doInBackground(String... Param) {

            String SOAP_ACTION = "http://xelados.net/usuarioCelular";
            String NAMESPACE = "http://xelados.net/";
            String METHOD_NAME = "usuarioCelular";
            String URL = "http://www.xelados.net/Servicio.asmx";


            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("codigo", Param[0].toString());
            request.addProperty("numero", Param[1].toString());
            request.addProperty("contrasena", Param[2].toString());

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

            String[] temp;
            temp = resultado.split("x");
            Integer Variable = temp.length;

            SharedPreferences pref = getSharedPreferences("Datos", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();

            editor.putString("dato1",temp[0]);
            editor.putString("dato2",temp[1]);
            editor.putBoolean("dato3",true);
            editor.putString("dato4",codigoPais.getText().toString() + numeroCelular.getText().toString());

            editor.commit();

            if (Variable == 1 ) {
                Toast.makeText(getBaseContext(), "Usuario o contraseña erronea", Toast.LENGTH_SHORT).show();

                pref = getSharedPreferences("Datos", Context.MODE_PRIVATE);
                editor = pref.edit();

                editor.putBoolean("dato3", false);

                editor.commit();
            }

            if (Variable == 2 ){
                Toast.makeText(getBaseContext(), "Bien, ya ingresaste ", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Actividad2.this,Actividad4.class);
                startActivity(intent);

            }
        }

        @Override
        protected void onCancelled(){
            super.onCancelled();
            Toast.makeText(getBaseContext(), "Ups... cancelaste la aplicación", Toast.LENGTH_SHORT).show();
        }

    }


    private class HilosAndroid1 extends AsyncTask<String,Integer,Void> {


        @Override
        protected Void doInBackground(String... Param) {

            String SOAP_ACTION = "http://xelados.net/recuperarContraenaCelular";
            String NAMESPACE = "http://xelados.net/";
            String METHOD_NAME = "recuperarContraenaCelular";
            String URL = "http://www.xelados.net/Servicio.asmx";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("codigo", Param[0]);
            request.addProperty("numero", Param[1]);

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

            return null;
        }


        @Override
        protected void onPostExecute(Void avoid) {

            Toast.makeText(getBaseContext(), "En minutos recibiras la contraseña", Toast.LENGTH_LONG).show();

            SystemClock.sleep(120000);
            olvidarContrasena.setTextColor(Color.RED);
            olvidarContrasena.setText("la contraseña es: " + resultado);
            logueoUsuario.setEnabled(true);
        }

        @Override
        protected void onCancelled(){
            super.onCancelled();
            Toast.makeText(getBaseContext(), "Ups... cancelaste la aplicación", Toast.LENGTH_SHORT).show();
        }

    }
}
