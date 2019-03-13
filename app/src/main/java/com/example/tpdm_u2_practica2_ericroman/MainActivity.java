package com.example.tpdm_u2_practica2_ericroman;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView listaPropietarios;
    PROPIETARIO vectorP[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaPropietarios = findViewById(R.id.ListaPropietarios);
        listaPropietarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mostrarAlertaPropietarios(position);
            }
        });
    }
    private void mostrarAlertaPropietarios(final int pos) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        if (!(vectorP==null)){
            alerta.setTitle("Attencion")
                    .setMessage("Deseas modifica/editar el proyecto "+vectorP[pos].getTelefono()+"?")
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
        Intent eliminaractualiza = new Intent(this,Main3Activity.class);

        eliminaractualiza.putExtra("TELEFONO",vectorP[pos].getTelefono());
        eliminaractualiza.putExtra("NOMBRE",vectorP[pos].getNombre());
        eliminaractualiza.putExtra("DOMICILIO",vectorP[pos].getDomicilio());
        eliminaractualiza.putExtra("FECHA",vectorP[pos].getFecha());

        startActivity(eliminaractualiza);
    }
    @Override
    protected void onStart(){
        PROPIETARIO propietario = new PROPIETARIO(this);
        vectorP = propietario.consultar();
        String[] descUbi = null;
        if (vectorP==null){
            descUbi=new String[1];
            descUbi[0]="no hay propietarios capturados";
        }else{
            descUbi = new String[vectorP.length] ;
            for (int i=0;i<vectorP.length;i++){
                PROPIETARIO temp = vectorP[i];
                descUbi[i]= temp.getNombre()+"\n"+temp.getTelefono();
            }
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,descUbi);

        listaPropietarios.setAdapter(adaptador);

        super.onStart();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.crearPropietario :
                Intent nuevoPropietario = new Intent(this,Main2Activity.class);
                startActivity(nuevoPropietario);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
