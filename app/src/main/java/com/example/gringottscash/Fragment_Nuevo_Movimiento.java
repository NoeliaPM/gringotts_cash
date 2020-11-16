package com.example.gringottscash;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
public class Fragment_Nuevo_Movimiento extends Fragment {
    private Button boton_scan, boton_write;
    public Fragment_Nuevo_Movimiento() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nuevo_movimiento, container, false);
    }
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        boton_scan=getView().findViewById(R.id.button_scan_movimiento);
        boton_write=getView().findViewById(R.id.button_escribir_movimiento);

        boton_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        boton_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent (getView().getContext(),Actividad_Scan_QR.class);
                startActivityForResult(i,0);
            }
        });
        boton_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent (getView().getContext(),Actividad_Escribir_Movimiento.class);
                startActivityForResult(i,0);
            }
        });
    }
}
