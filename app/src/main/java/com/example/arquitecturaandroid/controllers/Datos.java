package com.example.arquitecturaandroid.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.arquitecturaandroid.DTO.Estudiante;
import com.example.arquitecturaandroid.R;
import com.example.arquitecturaandroid.bussines.ApiController;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Datos extends AppCompatActivity {

    TextView localidad;
    TextView padres;
    TextView contacto;
    TextView situacionPersonal;
    TextView nombreAlumno;

    List<Estudiante> responseList;

    String alumnoSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);

        alumnoSeleccionado = this.getIntent().getExtras().getString("alumno seleccionado");
        localidad = findViewById(R.id.localidad);
        padres = findViewById(R.id.padres);
        contacto = findViewById(R.id.contacto);
        situacionPersonal = findViewById(R.id.personal);
        nombreAlumno = findViewById(R.id.nombreAlumno);
        onGetData();



    }

    private void onGetData(){

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

                nombreAlumno.setText(responseList.get(0).getName());
                localidad.setText(responseList.get(0).getCity());
                padres.setText(responseList.get(0).getPadres());
                contacto.setText(responseList.get(0).getPhone().toString());
                situacionPersonal.setText(responseList.get(0).getSpecial());


            }

            @Override
            public void onFailure(Call<List<Estudiante>> call, Throwable t) {
                Log.i("Error al traer los datos", t.getMessage());

            }
        });

    }
}