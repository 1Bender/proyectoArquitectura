package com.example.arquitecturaandroid.bussines;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.arquitecturaandroid.R;

public class Utils {

    public static Double getFloat(String intString, Context context) {
        try {
            return Double.parseDouble(intString);
        } catch (NumberFormatException e) {
            Log.i("Parseador", e.toString());
            Toast toast = Toast.makeText(context, "Debe introducir valores numericos", Toast.LENGTH_LONG);
            toast.show();
            return 0.0;
        }
    }
}
