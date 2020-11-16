package com.example.gringottscash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Actividad_Scan_QR extends AppCompatActivity {
    private CameraSource cameraSource;
    private SurfaceView cameraView;
    private final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    private String token = "",tokenanterior = "",movimientos_antiguos,saldo_antiguo,nombre_producto, nombre_tienda,galeones, precio_producto,tipo_moneda;
    private int nuevo_saldo=0;
    private Button button_scan;
    private Metodos m=new Metodos();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad__scan__q_r);
        cameraView =  findViewById(R.id.surfaceView);
        button_scan=findViewById(R.id.button_scan_qrcode);
        initQR();
    }

    public void initQR() {

        // creo el detector qr
        BarcodeDetector barcodeDetector =
                new BarcodeDetector.Builder(this)
                        .setBarcodeFormats(Barcode.ALL_FORMATS)
                        .build();

        // creo la camara
        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1600, 1024)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        // listener de ciclo de vida de la camara
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                // verifico si el usuario dio los permisos para la camara
                if (ActivityCompat.checkSelfPermission(Actividad_Scan_QR.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        // verificamos la version de ANdroid que sea al menos la M para mostrar
                        // el dialog de la solicitud de la camara
                        if (shouldShowRequestPermissionRationale(
                                Manifest.permission.CAMERA)) ;
                        requestPermissions(new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);

                    }
                    return;
                } else {
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch ( IOException ie) {
                    }
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        // preparo el detector de QR
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }


            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() > 0) {
                    //*******EL VALOR DEL QR HECHO STRING************//
                    button_scan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Hora actual:  dateFormat.format(date)
                            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                            Date date = new Date();
                            //Día actual:  (c.get(Calendar.DATE))+(c.get(Calendar.MONTH))+(c.get(Calendar.YEAR))
                            Calendar c = Calendar.getInstance();

                            //Separamos el string recogido del qr
                            String[] parts = token.split(";");
                            nombre_producto=parts[0];
                            nombre_tienda=parts[1];
                            precio_producto=parts[2];
                            tipo_moneda=parts[3];

                            //Leemos el documento de movimientos
                            movimientos_antiguos =m.leerMovimientosAntiguos(Actividad_Scan_QR.this);

                            //Leemos cuánto saldo había
                            saldo_antiguo=m.leerSaldo(Actividad_Scan_QR.this);

                            //Hacemos la conversión en función de la moneda que se vaya a usar.
                            galeones=m.conversionMoneda(tipo_moneda.trim(),precio_producto.trim());

                            //Obtenemos el nuevo saldo
                            nuevo_saldo = (Integer.parseInt(saldo_antiguo)) - Integer.parseInt(galeones);

                            //Sobreescribimos los movimientos
                            m.sobrescribirMovimientos(Actividad_Scan_QR.this,nombre_producto,nombre_tienda,"-",precio_producto,tipo_moneda,Integer.toString(c.get(Calendar.DATE)),Integer.toString(c.get(Calendar.MONTH)),Integer.toString(c.get(Calendar.YEAR)),dateFormat.format(date) ,movimientos_antiguos);

                            //Escribimos el nuevo saldo
                            m.sobreescribirSaldo(Actividad_Scan_QR.this,nuevo_saldo);
                            Toast toast = Toast.makeText(getApplicationContext(),nombre_producto+" "+precio_producto+tipo_moneda+" pagado.", Toast.LENGTH_SHORT);
                            toast.show();
                            //Actualizamos
                            Intent i = new Intent(v.getContext(), Actividad_Principal.class);
                            startActivityForResult(i, 0);

                        }
                    });
                    // obtenemos el token
                    token = barcodes.valueAt(0).displayValue.toString();

                    // verificamos que el token anterior no se igual al actual
                    // esto es util para evitar multiples llamadas empleando el mismo token
                    if (!token.equals(tokenanterior)) {

                        // guardamos el ultimo token proceado
                        tokenanterior = token;

                        if (URLUtil.isValidUrl(token)) {
                            // si es una URL valida abre el navegador

                        } else {

                        }

                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    synchronized (this) {
                                        wait(5000);
                                        // limpiamos el token
                                        tokenanterior = "";
                                    }
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                    }
                }
            }
        });

    }

}



