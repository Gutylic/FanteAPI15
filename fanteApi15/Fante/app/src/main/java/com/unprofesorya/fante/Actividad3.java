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

public class Actividad3 extends AppCompatActivity implements View.OnClickListener {

    TextView f;
    TextView guion;
    EditText primeralinea;
    EditText segundalinea;
    Button Activar;
    TextView noRecibioAlta;
    String resultado1;
    Spinner pais;
    String resultado;
    TextView codigoPais;
    EditText numeroCelular;
    EditText contrasenaUsuario;
    EditText RecontrasenaUsuario;
    Button RegistroUsuario;
    Integer codigo;
    String datos[] = {"Argentina","Mexico","Peru","Colombia","Uruguay","Brasil","Bolivia","España",
            "Chile","Cuba","Costa Rica","Ecuador","EEUU","Belice","El Salvador",
            "Guatemala","Honduras","Panama","Paraguay","Puerto Rico","Nicaragua",
            "Republica Dominicana","Venezuela"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad3);

        f = (TextView) findViewById(R.id.textView9);
        guion = (TextView) findViewById(R.id.textView10);
        primeralinea = (EditText) findViewById(R.id.editText5);
        segundalinea = (EditText) findViewById(R.id.editText6);

        Activar = (Button) findViewById(R.id.Activar);
        Activar.setOnClickListener(Actividad3.this);

        numeroCelular = (EditText) findViewById(R.id.editText);
        codigoPais = (TextView) findViewById(R.id.codigoPais);

        pais = (Spinner) findViewById(R.id.paisSpinner);

        contrasenaUsuario = (EditText) findViewById(R.id.contarasena);
        RecontrasenaUsuario = (EditText) findViewById(R.id.repContrasena);

        RegistroUsuario = (Button) findViewById(R.id.button);
        RegistroUsuario.setOnClickListener(this);

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

        if (v == RegistroUsuario ) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(RecontrasenaUsuario.getWindowToken(), 0);

            String numero = numeroCelular.getText().toString().substring(0,1);

            if (numero.compareTo("0") == 0){
                Toast.makeText(getApplicationContext(), "Debes quitar el cero inicial", Toast.LENGTH_SHORT).show();
                return;
            }


            if (numeroCelular.length() <= 9){
                Toast.makeText(getApplicationContext(), "No escribiste bien tu número de Whatsapp", Toast.LENGTH_SHORT).show();
                return;
            }


            if (contrasenaUsuario.getText().toString().matches("")) {
                Toast.makeText(getApplicationContext(), "No escribiste contraseña", Toast.LENGTH_SHORT).show();
                return;
            }

            if (contrasenaUsuario.length() < 5) {
                Toast.makeText(getApplicationContext(), "Tu contraseña debe tener más de 6 caracteres", Toast.LENGTH_LONG).show();
                return;
            }

            if (numeroCelular.getText().toString().matches("")) {
                Toast.makeText(getApplicationContext(), "No escribiste tu número de celular", Toast.LENGTH_LONG).show();
                return;
            }

            if (RecontrasenaUsuario.getText().toString().matches("")) {
                Toast.makeText(getApplicationContext(), "No repetiste tu contraseña", Toast.LENGTH_LONG).show();
                return;
            }

            if ((contrasenaUsuario.getText().toString()).compareTo(RecontrasenaUsuario.getText().toString()) != 0) {
                Toast.makeText(getApplicationContext(), "Tus contraseñas deben ser iguales", Toast.LENGTH_LONG).show();
                return;
            }

            if (contrasenaUsuario.length() > 5 && ((contrasenaUsuario.getText().toString()).compareTo(RecontrasenaUsuario.getText().toString()) == 0)) {
                RegistroUsuario.setEnabled(false);
                HilosAndroid Hilos = new HilosAndroid();
                Hilos.execute(codigoPais.getText().toString(), numeroCelular.getText().toString(), contrasenaUsuario.getText().toString());
            }
        }

        if (v == Activar){

            String Unido = primeralinea.getText().toString() + segundalinea.getText().toString();
            Log.e("unido",Unido);
            HilosAndroid1 Hilos1 = new HilosAndroid1();
            Hilos1.execute(codigoPais.getText().toString(),numeroCelular.getText().toString(),Unido);
        }


    }

    private class HilosAndroid extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... Param) {

            String SOAP_ACTION = "http://xelados.net/registrarUsuarioCelular";
            String NAMESPACE = "http://xelados.net/";
            String METHOD_NAME = "registrarUsuarioCelular";
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


            if(resultado.compareTo("-1") == 0){
                Toast.makeText(getApplicationContext(), "El usuario ya esta registrado", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Actividad3.this,Actividad1.class);
                startActivity(intent);
                return;
            }


            numeroCelular.setEnabled(false);
            pais.setEnabled(false);
            Activar.setEnabled(true);
            contrasenaUsuario.setEnabled(false);
            RecontrasenaUsuario.setEnabled(false);
            String resultado1 = resultado.substring(0,3);
            String resultado2 = resultado.substring(3,6);
            RegistroUsuario.setEnabled(false);
            RegistroUsuario.setTextColor(Color.RED);
            RegistroUsuario.setText("Código F:" + resultado1 + " - " + resultado2);
            f.setVisibility(View.VISIBLE);
            guion.setVisibility(View.VISIBLE);
            primeralinea.setVisibility(View.VISIBLE);
            segundalinea.setVisibility(View.VISIBLE);
            Activar.setVisibility(View.VISIBLE);



        }

        @Override
        protected void onCancelled(){
            super.onCancelled();
            Toast.makeText(getApplicationContext(), "Ups... cancelaste la aplicación", Toast.LENGTH_SHORT).show();
        }

    }

    private class HilosAndroid1 extends AsyncTask<String,Integer,String> {


        @Override
        protected String doInBackground(String... Param) {

            String SOAP_ACTION = "http://xelados.net/activarUsuarioCelular";
            String NAMESPACE = "http://xelados.net/";
            String METHOD_NAME = "activarUsuarioCelular";
            String URL = "http://www.xelados.net/Servicio.asmx";


            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("codigo", Param[0].toString());
            request.addProperty("numero", Param[1].toString());
            request.addProperty("codigoSMS", Param[2].toString());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(URL);

            try {

                transporte.call(SOAP_ACTION, envelope);
                SoapPrimitive resultado_xml1 = (SoapPrimitive) envelope.getResponse();
                resultado1 = resultado_xml1.toString();

                Log.e("resultado",resultado1);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }

            return resultado1;
        }


        @Override
        protected void onPostExecute(String resultado1){

            SharedPreferences pref = getSharedPreferences("Datos", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();

            editor.putString("dato1",resultado1);
            editor.putString("dato4",codigoPais.getText().toString()+numeroCelular.getText().toString());
            editor.putBoolean("dato3",true);
            Log.e("datos11111111===>",pref.getString("dato1",resultado1));
            editor.commit();

            Intent intent = new Intent(Actividad3.this,Actividad4.class);
            startActivity(intent);
        }

        @Override
        protected void onCancelled(){
            super.onCancelled();
            Toast.makeText(getApplicationContext(), "Ups... cancelaste la aplicación", Toast.LENGTH_SHORT).show();
        }

    }




}


