package com.example.gringottscash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Actividad_Carga extends AppCompatActivity {
    private ProgressBar pb;
    private Async tarea;
    private Metodos m=new Metodos();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad__carga);
        getSupportActionBar().hide();

        //Enlazamos con la parte gráfica
        pb=findViewById(R.id.progressBar);
        tarea=new Async();
        tarea.execute();

        //Si se inicia sesión por primera vez se crean los ficheros
        m.primeraContra(Actividad_Carga.this);
        m.primerMovimiento(Actividad_Carga.this);
        m.primerSaldo(Actividad_Carga.this);
    }
    private void tareaLarga(){
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {}
    }
    private class Async extends AsyncTask<Void, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            for (int i=0; i<=2; i++){
                tareaLarga();
                publishProgress(i*10);
                if (isCancelled())
                    break;
            }
            return true;
        }
        protected void onProgressUpdate(Integer... values){
            //Aquí actualizamos el estado de la barra de progreso con el valor recibido como parámetro
            int progreso = values[0].intValue();
            pb.setProgress(progreso);
        }
        protected void onPreExecute(){
            //Inicializamos la barra de progreso poniendo su valor máximo y a cero para comenzar
            pb.setMax(20);
            pb.setProgress(0);
        }
        protected void onPostExecute(Boolean result){
            if (result){
                Intent i=new Intent(Actividad_Carga.this, Actividad_contra.class);
                startActivity(i);
                finish();
            }
        }
    }
    public boolean existe(String[]archivos, String busqueda){
        for(int i=0; i<archivos.length;i++){
            if(busqueda.equals(archivos[i])){
                return true;
            }
        }
        return false;
    }
}

