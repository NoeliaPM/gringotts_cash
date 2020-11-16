package com.example.gringottscash;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Fragment_Confi extends Fragment {
    private Button boton_sonido, boton_cambio_contra;
    private boolean sonido=false;
    public Fragment_Confi(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Actividad_Principal activity = (Actividad_Principal) getActivity();
        return inflater.inflate(R.layout.fragment__confi, container, false);
    }
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        boton_sonido=getView().findViewById(R.id.button_sonido);
        boton_cambio_contra=getView().findViewById(R.id.button_confi_cambio_contra);
        //OnClick botón sonido
        boton_sonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sonido){
                    apagaMusica(v);
                    sonido=false;
                    boton_sonido.setBackgroundResource(R.drawable.voooooooooooooz);
                }
                else{
                    enciendeMusica(v);
                    sonido=true;
                    boton_sonido.setBackgroundResource(R.drawable.altaaaa);
                }
            }
        });
        boton_cambio_contra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                Intent i=new Intent(v.getContext(),Actividad_cambiar_contra.class);
                startActivityForResult(i,0);
            }
        });
}
    public void enciendeMusica(View v){
        Intent miMusica=new Intent(v.getContext(),Servicio_musica.class);
        getActivity().startService(miMusica);
    }

    public void apagaMusica(View v){
        Intent miMusica=new Intent(v.getContext(),Servicio_musica.class);
        getActivity().stopService(miMusica);
    }
}
