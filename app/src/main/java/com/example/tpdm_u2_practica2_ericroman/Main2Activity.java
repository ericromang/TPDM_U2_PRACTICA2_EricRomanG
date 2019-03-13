package com.example.tpdm_u2_practica2_ericroman;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {
    EditText telefono,nombre,domicilio,fecha;
    Button regresar,insertar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        telefono = findViewById(R.id.telefonoPropietario);
        nombre = findViewById(R.id.nombrePropietario);
        domicilio =findViewById(R.id.domicilioPropietario);
        fecha = findViewById(R.id.fechaPropietario);
        regresar = findViewById(R.id.regresarPropietario);
        insertar = findViewById(R.id.insertarPropietario);
        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarPropietario();
            }
        });
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void  insertarPropietario(){
        PROPIETARIO propietario = new PROPIETARIO(this);
        String mensaje;
        boolean respuesta = propietario.insertar(new PROPIETARIO(telefono.getText().toString(),nombre.getText().toString(),
                domicilio.getText().toString(),fecha.getText().toString()));
        if(respuesta){
            mensaje = "se pudo insertar el proyecto "+nombre.getText().toString();
        }else{
            mensaje = "Error! no se pudo crear proyecto, respuesta de SQLITE: "+propietario.error;
        }
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("ATENCION").setMessage(mensaje)
                .setPositiveButton("ok",null).show();
    }
}
