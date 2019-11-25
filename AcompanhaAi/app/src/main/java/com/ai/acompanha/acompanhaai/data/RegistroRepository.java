package com.ai.acompanha.acompanhaai.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.ai.acompanha.acompanhaai.R;
import com.ai.acompanha.acompanhaai.data.contract.RegistroContract;
import com.ai.acompanha.acompanhaai.data.dbhelper.RegistroDbHelper;
import com.ai.acompanha.acompanhaai.data.model.Registro;

import java.util.ArrayList;
import java.util.Date;

import static java.security.AccessController.getContext;

public class RegistroRepository {

    private Context context;
    RegistroDbHelper dbHelper;

    public RegistroRepository(Context ctx) {
        this.context = ctx;
        this.dbHelper = new RegistroDbHelper(this.context);
    }


    public long insert(Registro registro) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RegistroContract.RegistroEntry.COLUMN_CONSUMO, registro.getConsumo());
        values.put(RegistroContract.RegistroEntry.COLUMN_VALOR_ESTIMADO, registro.getValorPrevisto());
        values.put(RegistroContract.RegistroEntry.COLUMN_VALOR_REAL, registro.getValorReal());
        values.put(RegistroContract.RegistroEntry.COLUMN_PERIODO, registro.getPeriodo().toString());

        long newRowId = db.insert(RegistroContract.RegistroEntry.TABLE_NAME, null, values);

        return newRowId;
    }

    public ArrayList<Registro> buscarRegistros() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                RegistroContract.RegistroEntry.COLUMN_CONSUMO,
                RegistroContract.RegistroEntry.COLUMN_VALOR_ESTIMADO,
                RegistroContract.RegistroEntry.COLUMN_VALOR_REAL,
                RegistroContract.RegistroEntry.COLUMN_PERIODO
        };

        String sortOrder = RegistroContract.RegistroEntry.COLUMN_PERIODO + " DESC";

        Cursor cursor = db.query(
                RegistroContract.RegistroEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        ArrayList<Registro> registros = new ArrayList<>();

        while (cursor.moveToNext()) {
            long registroID = cursor.getLong(cursor.getColumnIndexOrThrow(RegistroContract.RegistroEntry._ID));
            int consumo = cursor.getInt(cursor.getColumnIndexOrThrow(RegistroContract.RegistroEntry.COLUMN_CONSUMO));
            double valorEstimado = cursor.getDouble(cursor.getColumnIndexOrThrow(RegistroContract.RegistroEntry.COLUMN_VALOR_ESTIMADO));
            double valorReal = cursor.getDouble(cursor.getColumnIndexOrThrow(RegistroContract.RegistroEntry.COLUMN_VALOR_REAL));
            String periodo = cursor.getString(cursor.getColumnIndexOrThrow(RegistroContract.RegistroEntry.COLUMN_PERIODO));

            registros.add(new Registro(registroID, consumo, valorEstimado, valorReal, periodo));
        }

        cursor.close();
        return registros;

    }

    public int deletaRegistro(long id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = RegistroContract.RegistroEntry._ID + " = ";
        String[] selectionArgs = {String.format("%d",id)};

        int deleteRow = db.delete(RegistroContract.RegistroEntry.TABLE_NAME, selection, selectionArgs);

        return deleteRow;
    }
}
