package com.example.arquitecturaandroid.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.arquitecturaandroid.R;
import com.example.arquitecturaandroid.bussines.SubProcesoWeb;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MenuPrincipal extends AppCompatActivity {

    String alumnos;

    /*Traemos el id recuperado en la bd*/
    String id;

    Spinner alumnosTutorizados;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        /*Traemos el valor guardado antes de iniciar el intent de esta pantalla*/
        id = this.getIntent().getExtras().getString("identificador");

        Log.i("id obtenido en menu", id);

        /*Definimos las variables con los ids de los elementos*/
        alumnosTutorizados = findViewById(R.id.alumnos);

        try {
            poblarAlumnos();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * Método que se debe ejecutar al cargar el intent, el cual poblara el spinner con los alumnos.
     * @throws JSONException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void poblarAlumnos() throws JSONException, ExecutionException, InterruptedException {

        SubProcesoWeb hiloAlumnos = new SubProcesoWeb();
        ArrayList<String> listaAlumnos;

        Log.i("Recuperando alumnos", "Realizando llamada a conectarAlumnos");
        listaAlumnos = hiloAlumnos.misAlumnos(id);




        /*Creamos un adapter para pasarle los datos al spinner*/
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, listaAlumnos);
        alumnosTutorizados.setAdapter(adapter);



    }


}