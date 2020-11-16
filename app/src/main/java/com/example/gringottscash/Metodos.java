package com.example.gringottscash;

import android.app.Activity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class Metodos {
    private String movimientos_antiguos="",saldo="",galeones="",todo="";
    private int contador;
    static Movimientos[]movimientos;

    public Metodos(){

    }
    //******************************************************
    //                         *
    //                    PRIMER INCIO
    //                         *
    //******************************************************
    public void primeraContra(Activity actividad){
        String[] archivos = actividad.fileList();
        if(existe(archivos,"contra.txt")){
        }
        else{
            OutputStreamWriter osw;
            try {
                osw = new OutputStreamWriter(actividad.openFileOutput("contra.txt", Actividad_Carga.MODE_APPEND));
                osw.write("1712");
                osw.flush();
                osw.close();
            } catch (IOException io) {
            }
        }
    }
    public void primerMovimiento(Activity actividad){
        String[] archivos = actividad.fileList();
        if(existe(archivos,"movimientos.txt")){
        }
        else{
            OutputStreamWriter osw;
            try {
                //Hora actual:  dateFormat.format(date)
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Date date = new Date();
                //Día actual:  (c.get(Calendar.DATE))+(c.get(Calendar.MONTH))+(c.get(Calendar.YEAR))
                Calendar c = Calendar.getInstance();
                osw = new OutputStreamWriter(actividad.openFileOutput("movimientos.txt", Actividad_Carga.MODE_APPEND));
                osw.write("Apertura de cuenta;Gringotts Bank;+50000G;"+(c.get(Calendar.DATE))+"-"+(c.get(Calendar.MONTH))+"-"+(c.get(Calendar.YEAR))+";"+dateFormat.format(date));
                osw.flush();
                osw.close();
            } catch (IOException io) {
            }
        }
    }
    public void primerSaldo(Activity actividad){
        String[] archivos = actividad.fileList();
        if(existe(archivos,"saldo.txt")){
        }
        else{
            OutputStreamWriter osw;
            try {
                osw = new OutputStreamWriter(actividad.openFileOutput("saldo.txt", Actividad_Carga.MODE_APPEND));
                osw.write("50000");
                osw.flush();
                osw.close();
            } catch (IOException io) {
            }
        }
    }
    //******************************************************
    //                         *
    //                     CONTRASEÑA
    //                         *
    //******************************************************
    public String leerContra(Activity actividad){
        try {
            InputStreamReader isr = new InputStreamReader(actividad.openFileInput("contra.txt"));
            BufferedReader br = new BufferedReader(isr);
            String linea = br.readLine();
             todo = "";
            while (linea != null) {
                todo = todo + linea + "\n";
                linea = br.readLine();
            }
            br.close();
            isr.close();
        } catch (IOException io) {
        }
        return todo;
    }

    public void cambiarContra(Activity actividad, String nueva_contra){
        OutputStreamWriter osw;
        try {
            osw = new OutputStreamWriter(actividad.openFileOutput("contra.txt", MODE_PRIVATE));
            osw.write(nueva_contra);
            osw.flush();
            osw.close();
        } catch (IOException io) {
        }
    }
    //******************************************************
    //                         *
    //                    LEER MOVIMIENTOS
    //                         *
    //******************************************************
    public String leerMovimientosAntiguos(Activity actividad){
        try {
            InputStreamReader isr = new InputStreamReader(actividad.openFileInput("movimientos.txt"));
            BufferedReader br = new BufferedReader(isr);
            String linea = br.readLine();
            while (linea != null) {
                movimientos_antiguos = movimientos_antiguos + linea + "\n";
                linea = br.readLine();
            }
            br.close();
            isr.close();
        } catch (IOException io) {
        }
        return movimientos_antiguos;
    }
    public void leerMovimientosSplit(Activity actividad){
        movimientos=new Movimientos[contador];
        String[] archivos = actividad.fileList();
        int i=0;
        if(existe(archivos, "movimientos.txt")) {
            try {
                InputStreamReader isr = new InputStreamReader(actividad.openFileInput("movimientos.txt"));
                BufferedReader br = new BufferedReader(isr);
                String linea = br.readLine();
                while (linea != null) {
                    String[] parts = linea.split(";");
                    movimientos[i]=new Movimientos(parts[0], parts[1], parts[2],parts[3],parts[4]);
                    i++;
                    linea = br.readLine();
                }
                br.close();
                isr.close();
            } catch (IOException io) {
            }
        }
    }
    public String leerSaldo(Activity actividad){
        try {
            InputStreamReader isr = new InputStreamReader(actividad.openFileInput("saldo.txt"));
            BufferedReader br = new BufferedReader(isr);
            saldo = br.readLine();
            br.close();
            isr.close();
        } catch (IOException io) {
        }
        return saldo;
    }

    public int contarLineasMovimientos(Activity actividad){
        String[] archivos = actividad.fileList();
        contador=0;
        if(existe(archivos, "movimientos.txt")) {
            try {
                InputStreamReader isr = new InputStreamReader(actividad.openFileInput("movimientos.txt"));
                BufferedReader br = new BufferedReader(isr);
                String linea = br.readLine();
                while (linea != null) {
                    contador++;
                    linea = br.readLine();
                }
                br.close();
                isr.close();
            } catch (IOException io) {
            }
        }
        return contador;
    }

    //******************************************************
    //                         *
    //                  NUEVOS MOVIMIENTOS
    //                         *
    //******************************************************
    public void sobrescribirMovimientos(Activity actividad,String producto, String tienda, String simbolo, String precio, String tipo_moneda,String dia, String mes, String año, String hora, String movimientos_antiguos_guardados){
        OutputStreamWriter osw;
        try {
            osw = new OutputStreamWriter(actividad.openFileOutput("movimientos.txt", MODE_PRIVATE));
            osw.write(producto + ";" + tienda + ";" + simbolo + precio + tipo_moneda + ";" + dia+"-"+ mes +"-"+ año + ";" + hora+ "\n" + movimientos_antiguos_guardados);
            osw.flush();
            osw.close();
        } catch (FileNotFoundException io) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sobreescribirSaldo(Activity actividad, int nuevo_saldo){
        OutputStreamWriter osw2;
        try {
            osw2 = new OutputStreamWriter(actividad.openFileOutput("saldo.txt", MODE_PRIVATE));
            osw2.write(Integer.toString(nuevo_saldo));
            osw2.flush();
            osw2.close();
        } catch (IOException io) {
        }
    }

    //******************************************************
    //                         *
    //                        OTROS
    //                         *
    //******************************************************
    public boolean existe(String[]archivos, String busqueda){
        for(int i=0; i<archivos.length;i++){
            if(busqueda.equals(archivos[i])){
                return true;
            }
        }
        return false;
    }

    public String conversionMoneda(String tipo_moneda, String precio){
        if (tipo_moneda.equals("G")) {
            galeones = precio;
        }
        else if (tipo_moneda.equals("S")) {
            double euros = (Integer.parseInt(precio) * 0.34) / 1;
            double gal = euros / 6;
            galeones = String.valueOf(Math.round(gal));
        }
        else if (tipo_moneda.equals("K")) {
            double euro = (Integer.parseInt(precio) * 0.01) / 1;
            double gal = euro / 6;
            galeones = String.valueOf(Math.round(gal));
        }
        return galeones;
    }
}
