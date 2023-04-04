package com.example.arquitecturaandroid.controllers;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arquitecturaandroid.R;
import com.example.arquitecturaandroid.bussines.SubProcesoWeb;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    /*Variables que identifican los elementos existentes*/
    EditText usuario, contrasena;
    Button btLogin;
    String datosUsuario;

    String id = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Definimos las variables con los ids de los elementos*/
        usuario = findViewById(R.id.edUsuario);
        contrasena = findViewById(R.id.edContra);
        btLogin = findViewById(R.id.btnentrar);
    }

    /**
     * Método que se ejecuta al pulsar el botón login. Este método obtiene los datos necesarios
     * del webservice usando los métodos de la clase SubProcesoWeb.
     * @param view
     * @throws JSONException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void acceso(View view) throws JSONException, ExecutionException, InterruptedException {

        SubProcesoWeb hiloAcceso = new SubProcesoWeb();


        Log.i("1", "Realizando llamada a EntrarApp");

        datosUsuario =hiloAcceso.EntrarApp(usuario.getText().toString(),
                contrasena.getText().toString());

        Log.i("6", "Llamada a gestion respuesta");

       id=  hiloAcceso.logIn(datosUsuario, this);

       if(id != null){
           menuInicial();
       }

    }

    /**
     * Metodo al que se llama una vez tenemos el acceso y nos permitira cargar el menu incial.
     * Tambien nos sirve para insertar en el intent el id y llevarnoslo a la siguinte ventana.
     */
    public void menuInicial(){

        Intent i=new Intent(MainActivity.this, MenuPrincipal.class);
        i.putExtra("identificador", id);

        Log.i("valor antes de cambio pantalla:", id);

        startActivity(i);

    }
}