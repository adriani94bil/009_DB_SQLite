package com.appDB.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.appDB.model.Palabra;

public class DictionaryDataBase extends SQLiteOpenHelper {

    //atributos

    private static  final String DATABASE_NAME="diccionario.db";
    private static  final int DATABASE_VERSION=1;
    private static  final String PALABRAS_TABLE_CREATE="CREATE TABLE palabras (_id INTEGER PRIMARY KEY , palabra TEXT, definicion TEXT)";

    public DictionaryDataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PALABRAS_TABLE_CREATE);
        db.execSQL("INSERT INTO PALABRAS (palabra,definicion)"+ "VALUES ('Triángulo','Figura de tres lados')");
        db.execSQL("INSERT INTO PALABRAS (palabra,definicion)"+ "VALUES ('Quimera','Algo imposible')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS palabras");
        onCreate(db);
    }

    //lista de palabras
    public Cursor getListaPalabras(){
        SQLiteDatabase db=getReadableDatabase();
        String query="SELECT _id, palabra, definicion FROM palabras ORDER BY palabra ASC";

        return db.rawQuery(query,null);
    }
    public long crearRegistro(String palabra, String definition){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("palabra",palabra);
        values.put("definicion",definition);
        return db.insert("PALABRAS", null, values);
    }
    public int modificarRegistro(long id,String palabra, String definition){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put("_id",id);
        values.put("palabra",palabra);
        values.put("_id",definition);
        return db.update(
                "PALABRAS",
                values,
                ":id=?",
                new String[]{String.valueOf(id)});
    }public int modificarRegistro(Palabra palabra){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put("_id",palabra.getId());
        values.put("palabra",palabra.getPalabra());
        values.put("_id",palabra.getDefinicion());
        return db.update(
                "PALABRAS",
                values,
                ":id=?",
                new String[]{String.valueOf(palabra.getId())});
    }

    public Palabra buscarPorId(long id) {
        Palabra returnVal=null;
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT _id,palabra,definicion FROM PALABRAS WHERE _id = ?", new  String[]{String .valueOf(id)});
        if (cursor.getCount()==1){
            cursor.moveToFirst();
            returnVal=new Palabra(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
        }
        return returnVal;
    }
    public  int borrarRegistro(long id){
        SQLiteDatabase db=getWritableDatabase();
        return db.delete("PALABRAS","_id=?", new String[]{String.valueOf(id)});

    }
}
