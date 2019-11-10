package com.ai.acompanha.acompanhaai.data.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ai.acompanha.acompanhaai.data.contract.RegistroContract;

import static com.ai.acompanha.acompanhaai.data.contract.RegistroContract.RegistroEntry.COLUMN_CONSUMO;
import static com.ai.acompanha.acompanhaai.data.contract.RegistroContract.RegistroEntry.COLUMN_PERIODO;
import static com.ai.acompanha.acompanhaai.data.contract.RegistroContract.RegistroEntry.COLUMN_VALOR_ESTIMADO;
import static com.ai.acompanha.acompanhaai.data.contract.RegistroContract.RegistroEntry.COLUMN_VALOR_REAL;

public class RegistroDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AcompanhaAi.db";


    public RegistroDbHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + RegistroContract.RegistroEntry.TABLE_NAME + " (" +
                    RegistroContract.RegistroEntry._ID + " INTEGER PRIMARY KEY," +
                    COLUMN_CONSUMO + " INTEGER," +
                    COLUMN_VALOR_ESTIMADO + " FLOAT," +
                    COLUMN_VALOR_REAL + " FLOAT," +
                    COLUMN_PERIODO + " DATE)"

            ;

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + RegistroContract.RegistroEntry.TABLE_NAME;
}
