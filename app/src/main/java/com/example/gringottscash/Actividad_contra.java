package com.example.gringottscash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Actividad_contra extends AppCompatActivity {
    private Metodos m=new Metodos();
    private Button uno,dos,tres,cuatro,cinco,seis,siete,ocho,nueve,cero,borrar,ast,entrar, olvido, almohadilla;
    private TextView contras;
    private String contra, todo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        uno=findViewById(R.id.button_1);
        dos=findViewById(R.id.button2);
        tres=findViewById(R.id.button3);
        cuatro=findViewById(R.id.button4);
        cinco=findViewById(R.id.button5);
        seis=findViewById(R.id.button6);
        siete=findViewById(R.id.button7);
        ocho=findViewById(R.id.button8);
        nueve=findViewById(R.id.button9);
        cero=findViewById(R.id.button0);
        ast=findViewById(R.id.button_asterisco);
        almohadilla=findViewById(R.id.button_borrar);
        borrar=findViewById(R.id.button_borrar_nuevo);
        entrar=findViewById(R.id.button_contra_entrar);
        contras=findViewById(R.id.textView_Contra);
        olvido=findViewById(R.id.button_olvido_contra);
        contra="";
        uno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contra=contra+"1";
                contras.setText(contras.getText().toString()+"*");
            }
        });
        dos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contra=contra+"2";
                contras.setText(contras.getText().toString()+"*");
            }
        });
        tres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contra=contra+"3";
                contras.setText(contras.getText().toString()+"*");
            }
        });
        cuatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contra=contra+"4";
                contras.setText(contras.getText().toString()+"*");
            }
        });
        cinco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contra=contra+"5";
                contras.setText(contras.getText().toString()+"*");
            }
        });
        seis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contra=contra+"6";
                contras.setText(contras.getText().toString()+"*");
            }
        });
        siete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contra=contra+"7";
                contras.setText(contras.getText().toString()+"*");
            }
        });
        ocho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contra=contra+"8";
                contras.setText(contras.getText().toString()+"*");
            }
        });
        nueve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contra=contra+"9";
                contras.setText(contras.getText().toString()+"*");
            }
        });
        cero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contra=contra+"0";
                contras.setText(contras.getText().toString()+"*");
            }
        });
        ast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contra=contra+"*";
                contras.setText(contras.getText().toString()+"*");
            }
        });
        almohadilla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contra=contra+"#";
                contras.setText(contras.getText().toString()+"*");
            }
        });
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contra=contra.substring(0, contra.length() - 1);
                contras.setText((contras.getText().toString()).substring(0, contras.length() - 1));
            }
        });

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Leer archivo
                todo=m.leerContra(Actividad_contra.this);

                if(todo.trim().equals(contra)){
                    Intent i=new Intent(v.getContext(),Actividad_Principal.class);
                    startActivityForResult(i,0);
                    finish();
                }
                else{
                    contra="";
                    contras.setText("");
                    Toast toast = Toast.makeText(getApplicationContext(),"Contraseña incorrecta.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        olvido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i=new Intent(v.getContext(),Actividad_cambiar_contra.class);
                startActivityForResult(i,0);
            }
        });

    }

}
