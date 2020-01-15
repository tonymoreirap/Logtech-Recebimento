package com.cadastro.logtech.integracaocad.integracaocadastro.BDHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cadastro.logtech.integracaocad.integracaocadastro.model.Entradas;

import java.util.ArrayList;

public class EntradasBd extends SQLiteOpenHelper {

    private static final String DATABASE ="bdentradas";
    private  static final int VERSION = 1;

    public EntradasBd (Context context){
        super(context, DATABASE,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String entradas = "CREATE TABLE entradas(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, codproduto TEXT NOT NULL, tipomovimento TEXT NOT NULL, qtdemov TEXT NOT NULL);";
        db.execSQL(entradas);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String entradas = "DROP TABLE IF EXISTS entradas";
        db.execSQL(entradas);
    }

    // aqui salva
    public void salvarEntradas(Entradas entradas){
        ContentValues values = new ContentValues();

        values.put("codproduto",entradas.getCodproduto());
        values.put("tipomovimento",entradas.getTipomovimento());
        values.put("qtdemov",entradas.getQtdemov());

        getWritableDatabase().insert("entradas",null,values);
    }

    // metodo alterar concluído ↓ :D
    public void alterarEntradas(Entradas entradas){
        ContentValues values = new ContentValues();

        values.put("codproduto",entradas.getCodproduto());
        values.put("tipomovimento",entradas.getTipomovimento());
        values.put("qtdemov",entradas.getQtdemov());

        String[] args = {entradas.getId().toString()};
        getWritableDatabase().update("entradas",values,"id=?",args);

    }

    public void deletarEntradas(Entradas entradas){
        String[] args = {entradas.getId().toString()};
        getWritableDatabase().delete("entradas","id=?",args);
    }

    // lista - mostrar

    public ArrayList<Entradas> getLista(){
        String[] columns ={"id","codproduto","tipomovimento","qtdemov"};
        Cursor cursor = getWritableDatabase().query("entradas",columns,null,null,null,null,null,null);
        ArrayList<Entradas> entrada = new ArrayList<Entradas>();

        while (cursor.moveToNext()){
            Entradas entradas = new Entradas();
            entradas.setId(cursor.getLong(0));
            entradas.setCodproduto(cursor.getString(1));
            entradas.setTipomovimento( String.valueOf(cursor.getLong(0)) + " - " + cursor.getString(2));
            entradas.setQtdemov(cursor.getString(3));

            entrada.add(entradas);
        }
        return entrada;
    }

}
