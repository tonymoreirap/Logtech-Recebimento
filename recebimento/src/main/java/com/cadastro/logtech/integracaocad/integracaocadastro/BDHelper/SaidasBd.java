package com.cadastro.logtech.integracaocad.integracaocadastro.BDHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cadastro.logtech.integracaocad.integracaocadastro.model.Saidas;

import java.util.ArrayList;

public class SaidasBd extends SQLiteOpenHelper {

    private static final String DATABASE ="bdsaidas";
    private  static final int VERSION = 1;

    public SaidasBd (Context context){
        super(context, DATABASE,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String saidas = "CREATE TABLE saidas(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, codproduto TEXT NOT NULL, tipomovimento TEXT NOT NULL, qtdemov TEXT NOT NULL);";
        db.execSQL(saidas);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String entradas = "DROP TABLE IF EXISTS saidas";
        db.execSQL(entradas);
    }

    // aqui salva
    public void salvarSaidas(Saidas saidas){
        ContentValues values = new ContentValues();

        values.put("codproduto",saidas.getCodproduto());
        values.put("tipomovimento",saidas.getTipomovimento());
        values.put("qtdemov",saidas.getQtdemov());

        getWritableDatabase().insert("saidas",null,values);
    }

    // metodo alterar concluído ↓ :D
    public void alterarSaidas(Saidas saidas){
        ContentValues values = new ContentValues();

        values.put("codproduto",saidas.getCodproduto());
        values.put("tipomovimento",saidas.getTipomovimento());
        values.put("qtdemov",saidas.getQtdemov());

        String[] args = {saidas.getId().toString()};
        getWritableDatabase().update("saidas",values,"id=?",args);

    }

    public void deletarSaidas(Saidas saidas){
        String[] args = {saidas.getId().toString()};
        getWritableDatabase().delete("saidas","id=?",args);
    }

    // lista - mostrar

    public ArrayList<Saidas> getLista(){
        String[] columns ={"id","codproduto","tipomovimento","qtdemov"};
        Cursor cursor = getWritableDatabase().query("saidas",columns,null,null,null,null,null,null);
        ArrayList<Saidas> saida = new ArrayList<Saidas>();

        while (cursor.moveToNext()){
            Saidas saidas = new Saidas();
            saidas.setId(cursor.getLong(0));
            saidas.setCodproduto(cursor.getString(1));
            saidas.setTipomovimento( String.valueOf(cursor.getLong(0)) + " - " + cursor.getString(2));
            saidas.setQtdemov(cursor.getString(3));

            saida.add(saidas);
        }
        return saida;
    }

}
