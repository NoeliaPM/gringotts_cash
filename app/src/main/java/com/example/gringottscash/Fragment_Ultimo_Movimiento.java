package com.example.gringottscash;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.gringottscash.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Fragment_Ultimo_Movimiento extends Fragment {
    private ListView listView;
    //static Movimientos[]movimientos;
    private movimientosListener listener;
    public Fragment_Ultimo_Movimiento() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Actividad_Principal activity = (Actividad_Principal) getActivity();
        return inflater.inflate(R.layout.fragment_ultimos_movimientos, container, false);
    }

    //Asociamos el adaptador al listado
    public void onActivityCreated(Bundle state) {  /* Aquí asociamos el adaptador al listado */
        super.onActivityCreated(state);

        //Enlazamos con la parte grafica
        listView=getView().findViewById(R.id.listView_Ultimos_Movimientos);
        listView.setAdapter(new AdaptadorMovimientos(this));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null) {
                    listener.onMovimientoSeleccionado((Movimientos) listView.getAdapter().getItem(position));
                }
            }
        });
    }
    //Interfas que se llama desde el fragment y desde la activity
    public interface movimientosListener{
        void onMovimientoSeleccionado(Movimientos m);
    }
    public void setMovimientoListener(movimientosListener listener){
        this.listener=listener;
    }

    //Creamos un adaptador del listview
    class AdaptadorMovimientos extends ArrayAdapter<Movimientos>{
        Activity context;

        public AdaptadorMovimientos(Fragment context){
            super(context.getActivity(),R.layout.listitem_ultimos_movimientos,Metodos.movimientos);
            this.context=context.getActivity();
        }
        public View getView(int position,View convertView, ViewGroup parent){
            LayoutInflater inflater=context.getLayoutInflater();
            View item=inflater.inflate(R.layout.listitem_ultimos_movimientos,null);

            //Enlazamos con los elementos del listados
            TextView tv_producto=item.findViewById(R.id.TextView_Producto);
            TextView tv_tienda=item.findViewById(R.id.TextView_Tienda);
            TextView tv_precio=item.findViewById(R.id.TextView_Precio);
            TextView tv_fecha=item.findViewById(R.id.textView_Fecha);
            TextView tv_hora=item.findViewById(R.id.textView_Hora);

            tv_producto.setText(Metodos.movimientos[position].getProducto());
            tv_tienda.setText(Metodos.movimientos[position].getTienda());
            tv_precio.setText(Metodos.movimientos[position].getPrecio());
            tv_precio.setText(Metodos.movimientos[position].getPrecio());
            tv_hora.setText(Metodos.movimientos[position].getHora());
            tv_fecha.setText(Metodos.movimientos[position].getFecha());

            return item;
        }
    }

}
