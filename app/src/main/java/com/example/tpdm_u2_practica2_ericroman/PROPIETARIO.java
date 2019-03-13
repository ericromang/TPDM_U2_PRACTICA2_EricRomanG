package com.example.tpdm_u2_practica2_ericroman;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.Date;

public class PROPIETARIO {
    private BaseDatos base;
    String telefono, nombre, domicilio, fecha, error;

    public PROPIETARIO(Activity activity) {
        base = new BaseDatos(activity, "aseguradora", null, 1);
    }

    public PROPIETARIO(String telefono, String nombre, String domicilio, String fecha) {
        this.telefono = telefono;
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.fecha = fecha;
    }

    public boolean insertar(PROPIETARIO propietario) {
        try {
            SQLiteDatabase transacionInsertar = base.getWritableDatabase();
            ContentValues datos = new ContentValues();
            datos.put("TELEFONO", propietario.getTelefono());
            datos.put("NOMBRE", propietario.getNombre());
            datos.put("DOMICILIO", propietario.getDomicilio());
            datos.put("FECHA", propietario.getFecha());
            long resultado = transacionInsertar.insert("PROPIETARIO", null, datos);
            transacionInsertar.close();
            if (resultado == -1) return false;
        } catch (SQLiteException e) {
            setError(e.getMessage());
            return false;
        }
        return true;
    }
    //// todos los abogados
    public PROPIETARIO[] consultar() {
        PROPIETARIO[] resultado = null;
        try {
            SQLiteDatabase transacciónConsulta = base.getReadableDatabase();
            String SQL = "SELECT * FROM PROPIETARIO ";

            Cursor c = transacciónConsulta.rawQuery(SQL, null);
            if (c.moveToFirst()) {
                resultado = new PROPIETARIO[c.getCount()];
                int pos = 0;
                do {
                    resultado[pos] = new PROPIETARIO(c.getString(0), c.getString(1),
                            c.getString(2),c.getString(3));
                    pos++;
                } while (c.moveToNext());
            }
            transacciónConsulta.close();
        } catch (SQLiteException e) {
            return null;
        }
        return resultado;
    }

    public boolean eliminar(String p) {
        int resultado;
        try {
            SQLiteDatabase transaccionEliminar = base.getWritableDatabase();
            resultado = transaccionEliminar.delete("SEGURO","TELEFONO =? ",new String[]{p});
            resultado = transaccionEliminar.delete("PROYECTOS","TELEFONO =? ",new String[]{p});
            transaccionEliminar.close();
        } catch (SQLiteException e) {
            setError(e.getMessage());
            return false;
        }
        return resultado>0;
    }

    public boolean actualizar(PROPIETARIO p) {
        try {
            SQLiteDatabase transaccionActualizar = base.getWritableDatabase();
            ContentValues datos = new ContentValues();

            //datos.put("TELEFONO", p.getNombre());
            datos.put("NOMBRE", p.getNombre());
            datos.put("DOMICILIO", p.getFecha());
            //datos.put("FECHA",p.getFecha());

            long resultado =
                    transaccionActualizar.update("PROPIETARIO", datos, "TELEFONO=?", new String[]{"" + p.getTelefono()});
            transaccionActualizar.close();
            if (resultado == 0) return false;
        }catch(SQLiteException e){
            setError(e.getMessage());
            return false;
        }
        return true;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getError() {
        return error;
    }

    public void setError(String er) {
        error = er;
    }

}
