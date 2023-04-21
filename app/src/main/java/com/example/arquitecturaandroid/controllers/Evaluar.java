package com.example.arquitecturaandroid.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arquitecturaandroid.DTO.Estudiante;
import com.example.arquitecturaandroid.DTO.Notas;
import com.example.arquitecturaandroid.R;
import com.example.arquitecturaandroid.bussines.ApiController;
import com.example.arquitecturaandroid.bussines.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Evaluar extends AppCompatActivity {


    EditText notaMat;
    EditText notaLeng;
    EditText notaSoci;
    EditText notaDib;
    String alumnoSeleccionado;

    String idBoletinNotas;

    List<Estudiante> responseList;

    List<Notas> responseListNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluar);

        /*Definimos variables con los id de los elementos del layout*/
        notaDib = findViewById(R.id.notaDib);
        notaLeng = findViewById(R.id.notLengua);
        notaMat = findViewById(R.id.notaMat);
        notaSoci = findViewById(R.id.notaSoci);
        alumnoSeleccionado = this.getIntent().getExtras().getString("alumno seleccionado");
        onGetIdBoletin();
    }


    /**
     * Metodo que nos permite obtener el id que identifica el boletin de notas del alumno.
     */
    private void onGetIdBoletin(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.116:8080/profile/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.i("nombre alumno seleccionado", alumnoSeleccionado);

        ApiController apiController = retrofit.create(ApiController.class);
        Call<List<Estudiante>> call= apiController.getIdBoletin(alumnoSeleccionado);

        call.enqueue(new Callback<List<Estudiante>>() {
            @Override
            public void onResponse(Call<List<Estudiante>> call, Response<List<Estudiante>> response) {

                if(!response.isSuccessful()){
                    Log.i("Respuesta fallida", "Sin respuesta");
                    return;
                }
                /*Obtenemos la respuesta*/
                responseList = response.body();

                Log.i("Id obtenido", responseList.get(0).getId());
                idBoletinNotas = responseList.get(0).getId();

            }

            @Override
            public void onFailure(Call<List<Estudiante>> call, Throwable t) {
                Log.i("Error al traer los datos", t.getMessage());

            }
        });

    }

    public void almacenarNotas(View view){

        String respuesta = onPostNotas();

        Toast toast = Toast.makeText(this, respuesta, Toast.LENGTH_LONG);
        toast.show();

    }

    /**
     * Metodo que nos permite obtener el id que identifica el boletin de notas del alumno.
     */
    private String onPostNotas(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.116:8080/notas/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        /*Controlamos el formato de los valores introducidos*/

        Double mates = Utils.getFloat(notaMat.getText().toString(), this);
        Double lengua = Utils.getFloat(notaLeng.getText().toString(), this);
        Double dibujo = Utils.getFloat(notaDib.getText().toString(), this);
        Double social = Utils.getFloat(notaSoci.getText().toString(), this);

        if(mates==0.001 || lengua==0.001 || dibujo==0.001 || social==0.001){


            return "Error al guardar, algún valor introducido no es numérico";

        }

        ApiController apiController = retrofit.create(ApiController.class);

        Call<List<Notas>> call= apiController.setNotes(idBoletinNotas, mates, lengua, dibujo, social);

        call.enqueue(new Callback<List<Notas>>() {
            @Override
            public void onResponse(Call<List<Notas>> call, Response<List<Notas>> response) {

                if(!response.isSuccessful()){
                    Log.e("Respuesta fallida", "El codigo HTTP no es el esperado");
                    return;
                }


            }

            @Override
            public void onFailure(Call<List<Notas>> call, Throwable t) {
                Log.i("Sin respuesta", "No esperada respuesta en Json al guardar, correcto");

            }
        });

        return "Valores guardados correctamente";
    }
}