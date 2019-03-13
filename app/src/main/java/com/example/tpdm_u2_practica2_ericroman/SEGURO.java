package com.example.tpdm_u2_practica2_ericroman;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class SEGURO {
    private BaseDatos base;
    String idseguro, descripcion, fecha, telefono, error;
    int tipo;


    public SEGURO(Activity activity) {
        base = new BaseDatos(activity, "aseguradora", null, 1);
    }

    public SEGURO(String idseguro, String descripcion, String fecha, int tipo, String telefono) {
        this.idseguro = idseguro;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.tipo = tipo;
        this.telefono = telefono;
    }

    public boolean insertar(SEGURO propietario) {
        try {
            SQLiteDatabase transacionInsertar = base.getWritableDatabase();
            ContentValues datos = new ContentValues();
            datos.put("IDSEGURO", propietario.getIdseguro());
            datos.put("DESCRIPCION", propietario.getDescripcion());
            datos.put("FECHA", propietario.getFecha());
            datos.put("TIPO", propietario.getTipo());
            datos.put("TELEFONO", propietario.getTelefono());
            long resultado = transacionInsertar.insert("SEGURO", null, datos);
            transacionInsertar.close();
            if (resultado == -1) return false;
        } catch (SQLiteException e) {
            setError(e.getMessage());
            return false;
        }
        return true;
    }
    //// todos los abogados
    public SEGURO[] consultar() {
        SEGURO[] resultado = null;
        try {
            SQLiteDatabase transacciónConsulta = base.getReadableDatabase();
            String SQL = "SELECT * FROM SEGURO ";

            Cursor c = transacciónConsulta.rawQuery(SQL, null);
            if (c.moveToFirst()) {
                resultado = new SEGURO[c.getCount()];
                int pos = 0;
                do {
                    resultado[pos] = new SEGURO(c.getString(0), c.getString(1),
                            c.getString(2),c.getInt(3),c.getString(4));
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
            resultado = transaccionEliminar.delete("SEGURO","IDSEGURO =? ",new String[]{p});
            transaccionEliminar.close();
        } catch (SQLiteException e) {
            setError(e.getMessage());
            return false;
        }
        return resultado>0;
    }

    public boolean actualizar(SEGURO p) {
        try {
            SQLiteDatabase transaccionActualizar = base.getWritableDatabase();
            ContentValues datos = new ContentValues();

            datos.put("DESCRIPCION", p.getDescripcion());
            datos.put("TIPO", p.getTipo());

            long resultado =
                    transaccionActualizar.update("SEGURO", datos, "IDSEGURO=?", new String[]{"" + p.getIdseguro()});
            transaccionActualizar.close();
            if (resultado == 0) return false;
        }catch(SQLiteException e){
            setError(e.getMessage());
            return false;
        }
        return true;
    }

    public String getIdseguro() {
        return idseguro;
    }

    public void setIdseguro(String idseguro) {
        this.idseguro = idseguro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
