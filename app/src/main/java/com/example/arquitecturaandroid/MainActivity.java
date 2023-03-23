package com.example.arquitecturaandroid;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    /*Variables que identifican los elementos existentes*/
    EditText usuario, contrasena;
    Button btLogin;
    String datosUsuario;



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
        String id = null;

        Log.i("1", "Realizando llamada a EntrarApp");
        datosUsuario =hiloAcceso.EntrarApp(usuario.getText().toString(),
                contrasena.getText().toString());

        Log.i("6", "Llamada a gestion respuesta");

       id=  hiloAcceso.gestionRespuesta(datosUsuario, this);

       if(id != null){
           menuInicial(id);
       }

    }

    /**
     * Metodo al que se llama una vez tenemos el acceso y nos permitira cargar el menu incial.
     * @param identificador
     */
    public void menuInicial(String identificador){

        Intent i=new Intent(getApplicationContext(), MenuPrincipal.class);

        startActivity(i);

    }
}