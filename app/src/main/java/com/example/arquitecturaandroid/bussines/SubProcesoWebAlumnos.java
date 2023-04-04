package com.example.arquitecturaandroid.bussines;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Clase que nos permitira detener la ejecución del hilo principal para obtener datos relacionados
 * con los alumnos que tutoriza el profesor.
 */
public class SubProcesoWebAlumnos extends AppCompatActivity {

    String txtJson;

    public SubProcesoWebAlumnos() {

    }

    public String traerAlumnos(String id) throws ExecutionException, InterruptedException {


        /*Se necesita un hilo asíncrono*/
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Log.i("Proceso Alumnos", "Definicion tarea");

        /*Defininos tarea. Necesitamos recuperar datos, usamos Callable*/
        Callable<String> tarea= ()->{
            HttpURLConnection conexion=null;
            conexion = conectarAlumnos(id);
            txtJson = SubProcesoWeb.estadoHttp(conexion);
            return txtJson;
        };

        Log.i("2", "Inicio del hilo asincrono");
        /*Iniciamos la tarea*/
        Future<String> future = executor.submit(tarea);

        /*El hilo principal esperara a obtener el resultado*/
        String resultado = future.get();

        /*Cerramos executor cuando finalice la tarea*/
        executor.shutdown();


        return resultado;
    }

    /**
     *Clase que realiza la conexión con el endpoint correspondiente del API
     * @param id
     * @return Objeto conexión de HttpURLConnection.
     * @throws IOException
     * Clase que nos permite establecer conexión con una URL donde podemos enviar parámetros para
     * realizar la consulta en la BD. En este caso se verifica un usuario y una contraseña.
     */
    public HttpURLConnection conectarAlumnos(String id) throws IOException, InterruptedException {

        String parametros;

        /*creamos la sentencia con los parámetros para montar el URI*/
        parametros = "id=" + id;

        Log.i("Proceso alumnos", "Establecindo conexion con url");

        /*Establecemos conexión con la URL*/
        URL url = new URL("http://192.168.1.116:8080/profile/allStudent");
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();


        /*Cambiamos el método para enviar los parámetros, y los enviamos*/
        Log.i("4", "Enviando parametros");
        try {
            conexion.setRequestMethod("POST");
            conexion.setDoOutput(true);
            conexion.getOutputStream().write(parametros.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("5", "Retornando conexion");


        /*Devolvemos el objeto conexión, con la conexión*/
        return conexion;
    }

}
