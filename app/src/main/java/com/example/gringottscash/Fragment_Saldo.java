package com.example.gringottscash;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class Fragment_Saldo extends Fragment {
    private Metodos m=new Metodos();
    private Button boton_cambio_moneda, boton_hacer_donacion, boton_enviar_dinero;
    private EditText et_cantidad, et_nombre_envio, et_cantidad_envio;
    private RadioButton rb_peddo,rb_ed,rb_of;
    private TextView tv_Saldo;
    private String linea, cantidad_donar, asociacion,cantidad_envio;
    public Fragment_Saldo() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Actividad_Principal activity = (Actividad_Principal) getActivity();
        return inflater.inflate(R.layout.fragment_saldo, container, false);
    }

    //Asociamos el adaptador al listado
    public void onActivityCreated(Bundle state) {  /* Aquí asociamos el adaptador al listado */
        super.onActivityCreated(state);

        tv_Saldo=getActivity().findViewById(R.id.textView_Saldo);
        boton_cambio_moneda=getActivity().findViewById(R.id.button_cambio_moneda);
        boton_hacer_donacion=getActivity().findViewById(R.id.button_Hacer_donacion);
        boton_enviar_dinero=getActivity().findViewById(R.id.button_Enviar_dinero);
        asociacion="";
        boton_cambio_moneda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater li = getLayoutInflater();
                View view = li.inflate(R.layout.alertdialog_conversion, null);
                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                builder.setNegativeButton("Cerrar", null);
                builder.setCancelable(false);
                final AlertDialog dialog = builder.create();
                dialog.setOnShowListener( new DialogInterface.OnShowListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onShow(DialogInterface arg0) {
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.black);
                    }
                });
                dialog.show();
            }
        });
        boton_hacer_donacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                LayoutInflater li = getLayoutInflater();
                View vi = li.inflate(R.layout.alertdialog_donacion, null);
                builder1.setView(vi);
                AlertDialog alertDialog = builder1.create();
                //EDITTEXT ALERT DIALOG
                et_cantidad=vi.findViewById(R.id.editText_cantidad_donacion);
                rb_peddo=vi.findViewById(R.id.radioButton_Peddo);
                rb_ed=vi.findViewById(R.id.radioButton_ED);
                rb_of=vi.findViewById(R.id.radioButton_OF);
                //BOTÓN DONAR
                builder1.setPositiveButton("Donar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        cantidad_donar=(et_cantidad.getText().toString()).substring(0,et_cantidad.getText().toString().length()-1);//Cogemos valor del editText

                        if(cantidad_donar.isEmpty()){
                            Toast toast1 =Toast.makeText(getContext(), "Tienes que introducir la cantidad de la donación", Toast.LENGTH_SHORT);
                            toast1.show();
                        }
                        else{
                            if (rb_peddo.isChecked()||rb_ed.isChecked()||rb_of.isChecked()){
                                if(rb_peddo.isChecked()){
                                    asociacion="P.E.D.D.O.";
                                }
                                else if(rb_of.isChecked()){
                                    asociacion="Orden del Fénix";
                                }
                                else if(rb_ed.isChecked()){
                                    asociacion="Ejercito de Dumbledore";
                                }
                                //Hora actual:  dateFormat.format(date)
                                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                                Date date = new Date();
                                //Día actual:  (c.get(Calendar.DATE))+(c.get(Calendar.MONTH))+(c.get(Calendar.YEAR))
                                Calendar c = Calendar.getInstance();
                                //Leemos el documento de movimientos
                                String movimientos_antiguos = m.leerMovimientosAntiguos(getActivity());
                                //Leemos cuánto saldo había
                                linea=m.leerSaldo(getActivity());

                                Integer nuevo_saldo = (Integer.parseInt(linea)) - Integer.parseInt((et_cantidad.getText().toString()).substring(0,et_cantidad.getText().toString().length()-1));
                                //Sobreescribimos los movimientos
                                m.sobrescribirMovimientos(getActivity(),"Donación", asociacion, "-", et_cantidad.getText().toString(), "",Integer.toString(c.get(Calendar.DATE)), Integer.toString(c.get(Calendar.MONTH)), Integer.toString(c.get(Calendar.YEAR)), dateFormat.format(date) ,movimientos_antiguos);

                                //Escribimos el nuevo saldo
                                m.sobreescribirSaldo(getActivity(),nuevo_saldo);

                                //Recargamos la página
                                getActivity().finish();
                                Intent i=new Intent(getActivity(),Actividad_Principal.class);
                                startActivityForResult(i,0);
                            }
                            else{
                                Toast toast1 =Toast.makeText(getContext(), "Tienes que seleccionar una asociación", Toast.LENGTH_SHORT);
                                toast1.show();
                            }
                        }
                    }
                });
                builder1.setNegativeButton("Cancelar", null);
                builder1.setCancelable(false);
                final AlertDialog dialog=builder1.create();
                dialog.setOnShowListener( new DialogInterface.OnShowListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onShow(DialogInterface arg0) {
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.black);
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.doradoMedio);
                    }
                });
                dialog.show();
            }
        });
        boton_enviar_dinero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                LayoutInflater li = getLayoutInflater();
                View vi = li.inflate(R.layout.alertdialog_enviar_dinero, null);
                builder1.setView(vi);
                AlertDialog alertDialog = builder1.create();
                //EDITTEXT ALERT DIALOG
                et_cantidad_envio=vi.findViewById(R.id.editText_Cantidad_envio);
                et_nombre_envio=vi.findViewById(R.id.editText_nombre_envio);

                builder1.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        cantidad_envio=(et_cantidad_envio.getText().toString()).substring(0,et_cantidad_envio.getText().toString().length()-1);//Cogemos valor del editText

                        if(cantidad_envio.isEmpty()){
                            Toast toast1 =Toast.makeText(getContext(), "Tienes que introducir la cantidad del envio.", Toast.LENGTH_SHORT);
                            toast1.show();
                        }
                        else{
                            if(et_nombre_envio.getText().toString().isEmpty()){
                                Toast toast1 =Toast.makeText(getContext(), "Tienes que introducir el nombre del beneficiario.", Toast.LENGTH_SHORT);
                                toast1.show();
                            }
                            else {
                                    //Hora actual:  dateFormat.format(date)
                                    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                                    Date date = new Date();
                                    //Día actual:  (c.get(Calendar.DATE))+(c.get(Calendar.MONTH))+(c.get(Calendar.YEAR))
                                    Calendar c = Calendar.getInstance();
                                    //Leemos el documento de movimientos
                                    String movimientos_antiguos = m.leerMovimientosAntiguos(getActivity());

                                    //Leemos cuánto saldo había
                                    linea=m.leerSaldo(getActivity());

                                    Integer nuevo_saldo = (Integer.parseInt(linea)) - Integer.parseInt((et_cantidad_envio.getText().toString()).substring(0,et_cantidad_envio.getText().toString().length()-1));
                                    //Sobreescribimos los movimientos
                                    m.sobrescribirMovimientos(getActivity(),"Envío", et_nombre_envio.getText().toString(), "-", et_cantidad_envio.getText().toString(), "",Integer.toString(c.get(Calendar.DATE)), Integer.toString(c.get(Calendar.MONTH)), Integer.toString(c.get(Calendar.YEAR)), dateFormat.format(date) ,movimientos_antiguos);

                                    //Escribimos el nuevo saldo
                                    m.sobreescribirSaldo(getActivity(),nuevo_saldo);

                                //Recargamos la página
                                getActivity().finish();
                                Intent i=new Intent(getActivity(),Actividad_Principal.class);
                                startActivityForResult(i,0);
                            }
                        }
                    }
                });
                builder1.setNegativeButton("Cancelar", null);
                builder1.setCancelable(false);
                final AlertDialog dialog=builder1.create();
                dialog.setOnShowListener( new DialogInterface.OnShowListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onShow(DialogInterface arg0) {
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.black);
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.doradoMedio);
                    }
                });
                dialog.show();
            }
        });

        //Mostrar saldo actual
        linea=m.leerSaldo(getActivity());
        tv_Saldo.setText(linea+" Galeones");

    }

}
