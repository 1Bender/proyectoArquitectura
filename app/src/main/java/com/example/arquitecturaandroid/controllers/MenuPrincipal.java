package com.example.arquitecturaandroid.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.arquitecturaandroid.DTO.Usuario;
import com.example.arquitecturaandroid.R;
import com.example.arquitecturaandroid.bussines.ApiController;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuPrincipal extends AppCompatActivity {


    /*Traemos el id recuperado en la bd*/
    String id;

    String idAlumno;

    Spinner alumnosTutorizados;
    TextView nombreProfesor;

    List<Usuario> userList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        /*Traemos el valor guardado antes de iniciar el intent de esta pantalla*/
        id = this.getIntent().getExtras().getString("identificador");

        Log.i("id obtenido en menu", id);

        /*Definimos las variables con los ids de los elementos*/
        alumnosTutorizados = findViewById(R.id.alumnos);
        nombreProfesor = findViewById(R.id.nombreProfe);
        nombreProfesor.setText(this.getIntent().getExtras().getString("nombre"));

        onPost();

    }

    /**
     * Metodo que nos permite conectar con el API, usando el id traido, para poblar el spinner con
     * los alumnos.
     */
    private void onPost(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.116:8080/acces/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiController apiController = retrofit.create(ApiController.class);
        Call <List<Usuario>> call= apiController.getAnswers(id);

        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {

                if(!response.isSuccessful()){
                    Log.i("Respuesta fallida", "Sin respuesta");
                    return;
                }

                userList = response.body();
                Log.i("Respuesta obtenida", userList.get(0).getStudents().toString());

                alumnosTutorizados.setAdapter(new ArrayAdapter<String>(MenuPrincipal.this,
                        android.R.layout.simple_spinner_item, userList.get(0).getStudents()));




            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.i("Error al traer los datos", t.getMessage());

            }
        });

    }

    public void activityEvaluar(){

        Intent i=new Intent(MenuPrincipal.this, Evaluar.class);
        i.putExtra("identificador", id);

        Log.i("Cambiando intent", "Evaluar");

        startActivity(i);

    }

    public void activityDatos(){

        Intent i=new Intent(MenuPrincipal.this, Datos.class);
        i.putExtra("identificador", id);


        Log.i("Cambiando intent", "Datos");

        startActivity(i);

    }

    public void activityCalificaciones(){

        Intent i=new Intent(MenuPrincipal.this, Calificaciones.class);
        i.putExtra("identificador", id);


        Log.i("Cambiando intent", "Calificaciones");

        startActivity(i);

    }



}