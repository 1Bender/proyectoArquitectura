package com.example.arquitecturaandroid.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

public class Calificaciones extends AppCompatActivity {

    List<Estudiante> responseList;
    List<Notas> responseNotes;

    String alumnoSeleccionado;
    String idBoletinNotas;

    TextView medMates;
    TextView medLengua;
    TextView medDibujo;
    TextView medSocial;

    Double media;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificaciones);

        medMates = findViewById(R.id.medMat);
        medLengua = findViewById(R.id.medLengua);
        medDibujo = findViewById(R.id.medDib);
        medSocial = findViewById(R.id.medSoci);

        alumnoSeleccionado = this.getIntent().getExtras().getString("alumno seleccionado");
        onGetIdBoletin();


    }


    /**
     * Metodo asincrono que nos permite obetner el identificador para las consultas en la
     * tabla de notas
     */
    private void onGetIdBoletin(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.116:8080/profile/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.i("nombre alumno seleccionado Notas", alumnoSeleccionado);

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

                Log.i("Id obtenido Notas", responseList.get(0).getId());
                idBoletinNotas = responseList.get(0).getId();

                /*Como las llamadas son asincronas, para evitar realizar la llamada al API
                * sin el id de boletin, se ha incluido dentro del hilo secundario centralizandose las
                * acciones dependientes dentro de este*/
                onGetNotas();

            }

            @Override
            public void onFailure(Call<List<Estudiante>> call, Throwable t) {
                Log.i("Error al traer los datos", t.getMessage());

            }
        });

    }

    /**
     * Metodo asincorno que recupera las listas de notas del API
     */
    private void onGetNotas(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.116:8080/notas/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiController apiController = retrofit.create(ApiController.class);

        Call<List<Notas>> call= apiController.getnotes(idBoletinNotas);

        call.enqueue(new Callback<List<Notas>>() {
            @Override
            public void onResponse(Call<List<Notas>> call, Response<List<Notas>> response) {

                if(!response.isSuccessful()){
                    Log.e("Respuesta fallida", "El codigo HTTP no es el esperado");
                    return;
                }

                responseNotes = response.body();

                media = Utils.calculoMedia(responseNotes.get(0).getMatematicas());
                introducirMedias(media, 1);

                media = Utils.calculoMedia(responseNotes.get(0).getLengua());
                introducirMedias(media, 2);

                media = Utils.calculoMedia(responseNotes.get(0).getDibujo());
                introducirMedias(media, 3);

                media = Utils.calculoMedia(responseNotes.get(0).getSocial());
                introducirMedias(media, 4);


            }

            @Override
            public void onFailure(Call<List<Notas>> call, Throwable t) {
                Log.i("Sin respuesta", "No esperada respuesta en Json al guardar, correcto");

            }
        });


    }

    /**
     * Metodo que nos permite introducir las medias en los textView distinguiendo segun el numero de
     * control, a que materia pertenece.
     * @param media
     * @param control
     */
    public void introducirMedias(Double media, int control){
        String valor;

        switch (control){
            case 1:
                valor = String.valueOf(media);
                medMates.setText(valor.substring(0,3));
                break;
            case 2:
                valor = String.valueOf(media);
                medLengua.setText(valor.substring(0,3));
                break;
            case 3:
                valor = String.valueOf(media);
                medDibujo.setText(valor.substring(0,3));
                break;
            case 4:
                valor = String.valueOf(media);
                medSocial.setText(valor.substring(0,3));
                break;
        }


    }



}