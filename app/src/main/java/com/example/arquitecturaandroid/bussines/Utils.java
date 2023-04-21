package com.example.arquitecturaandroid.bussines;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.arquitecturaandroid.R;

import java.util.List;

public class Utils {
    /**
     * Metodo que diferencia valores nulos, de problemas en formatdo numérico, retornando un valor
     * determinado para identificar la situación.
     * @param intString
     * @param context
     * @return
     */
    public static Double getFloat(String intString, Context context) {

        Log.i("Valor editText", intString);
        /*Este caso se da cuando existan editText sin valor introducido*/
        if(intString.isEmpty()){
            intString="-100";
        }
        Log.i("Valor tras if", intString);

        try {
            return Double.parseDouble(intString);
        } catch (NumberFormatException e) {
            Log.i("Parseador", e.toString());
            Toast toast = Toast.makeText(context, "Debe introducir valores numericos", Toast.LENGTH_LONG);
            toast.show();
            return 0.00;
        }
    }

    public static Double calculoMedia(List<Double> listaNotas ){

        Double media;
        int contador;
        int i;


        contador = listaNotas.size();
        media = 0.0;
        for(i=0; i<contador; i++){
            media += listaNotas.get(i);
        }
        media = media / contador;


        return media;
    }


}
