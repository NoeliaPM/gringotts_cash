package com.example.gringottscash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Actividad_Escribir_Movimiento extends AppCompatActivity {
    private Metodos m=new Metodos();
    private Spinner spinner;
    private EditText et_producto, et_precio;
    private RadioButton rb_compra, rb_venta;
    private Button boton_comprar, boton_galeon, boton_sickle,boton_knut;
    private boolean check, moneda_elegida;
    private String tienda="",saldo_antiguo, movimientos_antiguos, tipo_moneda,galeones, simbolo;
    private int nuevo_saldo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad__escribir__movimiento);
        et_producto=findViewById(R.id.editText_Producto);
        et_precio=findViewById(R.id.editText_Precio);
        spinner=findViewById(R.id.spinner);
        rb_compra=findViewById(R.id.radioButton_Compra);
        rb_venta=findViewById(R.id.radioButton_Venta);
        boton_comprar=findViewById(R.id.button_Comprar);
        boton_galeon=findViewById(R.id.button_Galleon);
        boton_sickle=findViewById(R.id.button_Sickles);
        boton_knut=findViewById(R.id.button_Knut);
        tipo_moneda="";
        moneda_elegida=false;

        //Creamos adaptador para trabajar con arrays en el spinner con el layout del spinner plegado
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.stringsarray, android.R.layout.simple_spinner_item);
        //Asociamos layout spinner desplegado
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Asignamos el adaptador al spinner
        spinner.setAdapter(adapter);
        check=false;
        //Evento de elemento seleccionado del spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                check=true;
                tienda= (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        boton_galeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moneda_elegida=true;
                tipo_moneda="G";
            }
        });
        boton_sickle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moneda_elegida=true;
                tipo_moneda="S";
            }
        });
        boton_knut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moneda_elegida=true;
                tipo_moneda="K";
            }
        });
        boton_comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Si han elegido una moneda
                if (moneda_elegida) {
                    //Si NO rellenan la casilla de precio
                    if (et_precio.getText().toString().isEmpty()) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Debes introducir un precio.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else {
                        //Si NO introduciste el nombre del producto
                        if(et_producto.getText().toString().isEmpty()){
                            Toast toast = Toast.makeText(getApplicationContext(), "Debes introducir el nombre del producto.", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else{
                            //Si  eligió compra o venta
                            if(rb_compra.isChecked()||rb_venta.isChecked()) {
                                //Hora actual:  dateFormat.format(date)
                                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                                Date date = new Date();
                                //Día actual:  (c.get(Calendar.DATE))+(c.get(Calendar.MONTH))+(c.get(Calendar.YEAR))
                                Calendar c = Calendar.getInstance();
                                //Leemos el documento de movimientos
                                movimientos_antiguos =m.leerMovimientosAntiguos(Actividad_Escribir_Movimiento.this);

                                //Leemos cuánto saldo había
                                saldo_antiguo=m.leerSaldo(Actividad_Escribir_Movimiento.this);

                                //Hacemos la conversión en función de la moneda que se vaya a usar.
                                String precio_producto=et_precio.getText().toString();
                                galeones=m.conversionMoneda(tipo_moneda,precio_producto);

                                //Restamos o sumamos el precio del producto.
                                if (rb_compra.isChecked()) {
                                    nuevo_saldo = (Integer.parseInt(saldo_antiguo)) - Integer.parseInt(galeones);
                                    simbolo="-";
                                    //Sobreescribimos los movimientos
                                   m.sobrescribirMovimientos(Actividad_Escribir_Movimiento.this,et_producto.getText().toString(),tienda,simbolo,et_precio.getText().toString(),tipo_moneda,Integer.toString(c.get(Calendar.DATE)),Integer.toString(c.get(Calendar.MONTH)),Integer.toString(c.get(Calendar.YEAR)),dateFormat.format(date) ,movimientos_antiguos);
                                }
                                //Si es una venta
                                else {
                                    nuevo_saldo = (Integer.parseInt(saldo_antiguo)) + Integer.parseInt(galeones);
                                    simbolo="+";
                                    m.sobrescribirMovimientos(Actividad_Escribir_Movimiento.this,et_producto.getText().toString(),tienda,simbolo,et_precio.getText().toString(),tipo_moneda,Integer.toString(c.get(Calendar.DATE)),Integer.toString(c.get(Calendar.MONTH)),Integer.toString(c.get(Calendar.YEAR)),dateFormat.format(date) ,movimientos_antiguos);
                                }

                                //Escribimos el nuevo saldo
                                m.sobreescribirSaldo(Actividad_Escribir_Movimiento.this,nuevo_saldo);

                                //Actualizamos
                                Intent i = new Intent(v.getContext(), Actividad_Principal.class);
                                startActivityForResult(i, 0);
                            }
                            else{
                                Toast toast = Toast.makeText(getApplicationContext(), "Debes elegir si vas a comprar o a vender.", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    }
                }
                //Si no han elegido moneda
                else {
                    Toast toast = Toast.makeText(getApplicationContext(),"Debes elegir una moneda.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}
