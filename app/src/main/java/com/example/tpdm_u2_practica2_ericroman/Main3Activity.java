package com.example.tpdm_u2_practica2_ericroman;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class Main3Activity extends AppCompatActivity {
    EditText telefono,nombre,domicilio,fecha;
    Button regresar,eliminar,actualizar,nuevoseguro;
    ListView ListaSeguros;
    SEGURO vectorS[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        telefono = findViewById(R.id.telefonoPropietario2);
        nombre = findViewById(R.id.nombrePropietario2);
        domicilio =findViewById(R.id.domicilioPropietario2);
        fecha = findViewById(R.id.fechaPropietario2);
        regresar = findViewById(R.id.regresarPropietario2);
        eliminar = findViewById(R.id.eliminarPropietario2);
        actualizar = findViewById(R.id.actualizarPropietario2);
        nuevoseguro = findViewById(R.id.nuevoSeguro);
        ListaSeguros = findViewById(R.id.ListaSeguros);

        Bundle parametros = getIntent().getExtras();

        telefono.setText(parametros.getString("TELEFONO"));
        nombre.setText(parametros.getString("NOMBRE"));
        domicilio.setText(parametros.getString("DOMICILIO"));
        fecha.setText(parametros.getString("FECHA"));
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarPropietario();
            }
        });
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarPropietario();
            }
        });
        nuevoseguro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nuevoseguro();
            }
        });
        ListaSeguros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mostrarAlertaSeguros(position);
            }
        });
    }
    private void mostrarAlertaSeguros(final int pos) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        if (!(vectorS==null)){
            alerta.setTitle("Attencion")
                    .setMessage("Deseas modifica/editar el proyecto "+vectorS[pos].getTelefono()+"?")
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            invocarEliminarActualizar(pos);
                        }
                    }).setNegativeButton("NO",null)
                    .show();
        }
    }
    private void invocarEliminarActualizar(int pos) {
        Intent eliminaractualizaSeguro = new Intent(this,Main5Activity.class);

        eliminaractualizaSeguro.putExtra("IDSEGURO",vectorS[pos].getIdseguro());
        eliminaractualizaSeguro.putExtra("DESCRIPCION",vectorS[pos].getDescripcion());
        eliminaractualizaSeguro.putExtra("FECHA",vectorS[pos].getFecha());
        eliminaractualizaSeguro.putExtra("TIPO",vectorS[pos].getTipo());
        eliminaractualizaSeguro.putExtra("TELEFONO",vectorS[pos].getTelefono());

        startActivity(eliminaractualizaSeguro);
    }
    @Override
    protected void onStart(){
        SEGURO propietario = new SEGURO(this);
        vectorS = propietario.consultar();
        String[] descUbi = null;
        if (vectorS==null){
            descUbi=new String[1];
            descUbi[0]="no hay propietarios capturados";
        }else{
            descUbi = new String[vectorS.length] ;
            for (int i=0;i<vectorS.length;i++){
                SEGURO temp = vectorS[i];
                descUbi[i]= temp.getDescripcion()+"\n"+temp.getTelefono();
            }
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,descUbi);

        ListaSeguros.setAdapter(adaptador);

        super.onStart();
    }
    private  void nuevoseguro (){
        Intent crearseguro = new Intent(this,Main4Activity.class);
        crearseguro.putExtra("TELEFONO",telefono.getText().toString());
        startActivity(crearseguro);
    }
    public void  actualizarPropietario(){
        PROPIETARIO propietario = new PROPIETARIO(this);
        String mensaje;
        boolean respuesta = propietario.actualizar(new PROPIETARIO(telefono.getText().toString(),nombre.getText().toString(),
                domicilio.getText().toString(),fecha.getText().toString()));
        if(respuesta){
            mensaje = "se pudo actualizar el proyecto "+nombre.getText().toString();
        }else{
            mensaje = "Error! no se pudo actualizar el proyecto, respuesta de SQLITE: "+propietario.error;
        }
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("ATENCION").setMessage(mensaje)
                .setPositiveButton("ok",null).show();
    }
    private void eliminarPropietario() {
        PROPIETARIO propietario = new PROPIETARIO(this);
        String mensaje;
        boolean respuesta = propietario.eliminar(telefono.getText().toString()+"");
        if(respuesta){
            mensaje = "se pudo eliminar el propietario ";

        }else{
            mensaje = "Error! no se pudo actualizar el propietario, respuesta de SQLITE: "+propietario.error;
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
