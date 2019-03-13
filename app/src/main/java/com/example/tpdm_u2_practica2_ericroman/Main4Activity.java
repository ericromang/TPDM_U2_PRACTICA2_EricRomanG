package com.example.tpdm_u2_practica2_ericroman;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Main4Activity extends AppCompatActivity {
    EditText idseguro2,descripcion2,fecha2,telefono2;
    Spinner tipo2;
    Button regresar2,insertar2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        idseguro2 = findViewById(R.id.idseguroSeguro);
        descripcion2 = findViewById(R.id.descripcionSeguro);
        fecha2 =findViewById(R.id.fechaSeguro);
        tipo2 = findViewById(R.id.tipoSeguro);
        telefono2 = findViewById(R.id.telefonoSeguro);
        regresar2 = findViewById(R.id.regresarSeguro);
        insertar2 = findViewById(R.id.insertarSeguro);
        Bundle parametrosS = getIntent().getExtras();
        telefono2.setText(parametrosS.getString("TELEFONO"));

        insertar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarSeguro();
            }
        });
        regresar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void insertarSeguro(){
        SEGURO seguro = new SEGURO(this);
        String mensaje;
        boolean respuesta = seguro.insertar(new SEGURO(idseguro2.getText().toString(),descripcion2.getText().toString(),
                fecha2.getText().toString(),tipo2.getSelectedItemPosition(),telefono2.getText().toString()));
        if(respuesta){
            mensaje = "se pudo insertar el seguro "+descripcion2.getText().toString();
        }else{
            mensaje = "Error! no se pudo crear seguro, respuesta de SQLITE: "+seguro.error;
        }
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("ATENCION").setMessage(mensaje)
                .setPositiveButton("ok",null).show();
    }
}
