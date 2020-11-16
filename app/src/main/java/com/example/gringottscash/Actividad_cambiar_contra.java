package com.example.gringottscash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class Actividad_cambiar_contra extends AppCompatActivity {
    private EditText et_nueva_contra,et_confirm_contra;
    private TextView tv_titulo,tv_espere,tv_nueva_contra,tv_confirm_contra;
    private Button boton_guardar;
    private ProgressBar pb_nueva;
    private Async tarea;
    private Metodos m=new Metodos();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_cambiar_contra);

        et_nueva_contra=findViewById(R.id.editText_Nueva_contra);
        et_confirm_contra=findViewById(R.id.editText_repite_contra);
        tv_confirm_contra=findViewById(R.id.textView_Repite_contra);
        tv_espere=findViewById(R.id.textView_espera_identidad);
        tv_nueva_contra=findViewById(R.id.textView_Nueva_contra);
        tv_titulo=findViewById(R.id.textView_Identidad_confirmada);
        boton_guardar=findViewById(R.id.button_Guardar_nueva_contra);
        pb_nueva=findViewById(R.id.progressBar2);

        et_confirm_contra.setVisibility(View.INVISIBLE);
        et_nueva_contra.setVisibility(View.INVISIBLE);
        tv_nueva_contra.setVisibility(View.INVISIBLE);
        tv_confirm_contra.setVisibility(View.INVISIBLE);
        tv_titulo.setVisibility(View.INVISIBLE);
        boton_guardar.setVisibility(View.INVISIBLE);

        tarea=new Async();
        tarea.execute();

        boton_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_confirm_contra.getText().toString().equals(et_nueva_contra.getText().toString())){
                    m.cambiarContra(Actividad_cambiar_contra.this,et_confirm_contra.getText().toString());
                    Toast toast = Toast.makeText(getApplicationContext(),"Contraseña actualizada.", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent i=new Intent(v.getContext(),Actividad_contra.class);
                    startActivityForResult(i,0);
                }
                else{
                    et_confirm_contra.setText("");
                    et_nueva_contra.setText("");
                    Toast toast = Toast.makeText(getApplicationContext(),"Las contraseñas no coinciden.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
    private void tareaLarga(){
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {}
    }
    private class Async extends AsyncTask<Void, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            for (int i=0; i<=4; i++){
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
            pb_nueva.setProgress(progreso);
        }
        protected void onPreExecute(){
            //Inicializamos la barra de progreso poniendo su valor máximo y a cero para comenzar
            pb_nueva.setMax(20);
            pb_nueva.setProgress(0);
        }
        protected void onPostExecute(Boolean result){
            if (result){
                pb_nueva.setVisibility(View.INVISIBLE);
                tv_espere.setVisibility(View.INVISIBLE);
                et_confirm_contra.setVisibility(View.VISIBLE);
                et_nueva_contra.setVisibility(View.VISIBLE);
                tv_nueva_contra.setVisibility(View.VISIBLE);
                tv_confirm_contra.setVisibility(View.VISIBLE);
                boton_guardar.setVisibility(View.VISIBLE);
                tv_titulo.setVisibility(View.VISIBLE);
            }
        }
    }
}
