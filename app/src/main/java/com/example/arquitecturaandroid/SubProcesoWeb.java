package com.example.arquitecturaandroid;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Clase para obtener datos de web service en formato Json usando arquitectura REST.
 * Android no permite ejecutar operaciones de red en el hilo principal de la aplicación. En esta
 * clase usaremos ExecutorService para crear hilos asíncronos. También detendremos la ejecución del
 * hilo principal hasta obtener los datos necesarios.
 */
public class SubProcesoWeb extends AppCompatActivity {

    /*Atributos de la clase*/
    String txtJson;

    /*Constructor*/
    public SubProcesoWeb(){}

    /**
     * Método que crea el hilo asíncrono para hacer la llamada al resto de métodos. Este método
     * paraliza el hilo principal hasta obtener los datos necesarios.
     * @param usuario
     * @param contrasena
     * @return resultado de la consulta realizada en el webservice.
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String EntrarApp(String usuario, String contrasena) throws ExecutionException, InterruptedException {


        /*Se necesita un hilo asíncrono*/
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Log.i("2", "Definicion tarea");
        /*Defininos tarea. Necesitamos recuperar datos, usamos Callable*/
        Callable<String> tarea= ()->{
            HttpURLConnection conexion=null;
            conexion = conectar(usuario, contrasena);
            txtJson = logIn(conexion);
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
     *
     * @param usuario
     * @param pass
     * @return Objeto conexión de HttpURLConnection.
     * @throws IOException
     * Clase que nos permite establecer conexión con una URL donde podemos enviar parámetros para
     * realizar la consulta en la BD. En este caso se verifica un usuario y una contraseña.
     */
    public HttpURLConnection conectar(String usuario, String pass) throws IOException, InterruptedException {

        String parametros;

        /*creamos la sentencia con los parámetros para montar el URI*/
        parametros = "name=" + usuario + "&pass=" + pass;

        Log.i("3", "Establecindo conexion con url");
        /*Establecemos conexión con la URL*/
        URL url = new URL("http://192.168.1.116:8080/acces/acces");
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

    /**
     * Método que recoge el Json que nos entrega un webservice.
     * @param conect
     * @return respuesta con los datos del webservice.
     * @throws IOException
     */
    public String logIn(@NonNull HttpURLConnection conect) throws IOException{

        String respuesta = "";

        Log.i("5", "Revisando respuesta");
        /*Revisamos correcta conexión con webservice*/
        if(conect.getResponseCode()==200){

            /*Leemos datos y guardamos en variable para devolver*/
            Scanner lectura = new Scanner(conect.getInputStream());
            while(lectura.hasNext()){
                respuesta += lectura.next();
            }
        }

        return respuesta;
    }

    /**
     * Método que analiza la respuesta del webservice para verificar un usuario. Este método se debe
     * llamar fuera del hilo asíncrono puesto que es muy problemático mostrar mensajes toast en el
     * contexto principal mediante un hilo.
     * @param resp
     * @param context
     * @throws JSONException
     */
    public String gestionRespuesta(String resp, Context context) throws JSONException {

        String texto;
        String id =null;

        /*Comprobamos la respuesta del webservice, podrá ser false si no existe coincidencia y
         * en caso de ser distinto de false habrá coincidencia*/

        Log.i("Contenido de la respuesta:", resp);

        if(resp.contains("Concedido")){

            texto = context.getString(R.string.Permitido);
            Toast toast = Toast.makeText(context, texto, Toast.LENGTH_LONG);
            toast.show();
            id= resp.substring(8);


        }else{

            texto = context.getString(R.string.Denegado);
            Toast toast = Toast.makeText(context, texto, Toast.LENGTH_LONG);
            toast.show();

        }

       return id;
    }



}