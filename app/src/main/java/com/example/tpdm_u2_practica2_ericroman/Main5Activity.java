package com.example.tpdm_u2_practica2_ericroman;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Main5Activity extends AppCompatActivity {
    EditText idseguro,descripcion,fecha,telefono;
    Spinner tipo;
    Button regresar,actualizar,eliminar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        idseguro = findViewById(R.id.idseguroSeguro3);
        descripcion = findViewById(R.id.descripcionSeguro3);
        fecha =findViewById(R.id.fechaSeguro3);
        telefono = findViewById(R.id.telefonoSeguro3);
        regresar = findViewById(R.id.regresarSeguro3);
        actualizar = findViewById(R.id.actualizarSeguro3);
        eliminar = findViewById(R.id.eliminarSeguro3);
        tipo = findViewById(R.id.tipoSeguro4);

        Bundle parametros = getIntent().getExtras();

        telefono.setText(parametros.getString("TELEFONO"));
        idseguro.setText(parametros.getString("IDSEGURO"));
        descripcion.setText(parametros.getString("DESCRIPCION"));
        fecha.setText(parametros.getString("FECHA"));
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarSeguro();
            }
        });
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarSeguro();
            }
        });
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void  actualizarSeguro(){
        SEGURO seguro = new SEGURO(this);
        String mensaje;
        boolean respuesta = seguro.actualizar(new SEGURO(idseguro.getText().toString(),descripcion.getText().toString(),
                fecha.getText().toString(),tipo.getSelectedItemPosition(),telefono.getText().toString()));
        if(respuesta){
            mensaje = "se pudo actualizar el proyecto "+descripcion.getText().toString();
        }else{
            mensaje = "Error! no se pudo actualizar el proyecto, respuesta de SQLITE: "+seguro.error;
        }
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("ATENCION").setMessage(mensaje)
                .setPositiveButton("ok",null).show();
    }
    private void eliminarSeguro() {
        SEGURO seguro = new SEGURO(this);
        String mensaje;
        boolean respuesta = seguro.eliminar(idseguro.getText().toString()+"");
        if(respuesta){
            mensaje = "se pudo eliminar el seguro ";

        }else{
            mensaje = "Error! no se pudo actualizar el seguro, respuesta de SQLITE: "+seguro.error;
        }
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("ATENCION").setMessage(mensaje)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();

    }

}
