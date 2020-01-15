package com.cadastro.logtech.integracaocad.integracaocadastro.BDHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cadastro.logtech.integracaocad.integracaocadastro.model.Estoque;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EstoqueBd extends SQLiteOpenHelper {

    private static final String DATABASE ="bdestoque";
    private  static final int VERSION = 1;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");
    Date date = new Date();

    public EstoqueBd (Context context){
        super(context, DATABASE,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String estoque = "CREATE TABLE estoquemov(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, codproduto TEXT NOT NULL, tipomovimento TEXT NOT NULL, qtdemov TEXT NOT NULL, datamov TEXT);";
        db.execSQL(estoque);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String estoque = "DROP TABLE IF EXISTS estoquemov";
        db.execSQL(estoque);
    }

    // aqui salva
    public void salvarEstoques(Estoque estoque){
        ContentValues values = new ContentValues();
        try {
            values.put("codproduto", estoque.getCodproduto());
            values.put("tipomovimento", estoque.getTipomovimento());
            values.put("qtdemov", estoque.getQtdemov());
            values.put("datamov", dateFormat.format(date).toString());

            getWritableDatabase().insert("estoquemov", null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // metodo alterar concluído ↓ :D
    public void alterarEstoques(Estoque estoque){
        ContentValues values = new ContentValues();

        values.put("codproduto",estoque.getCodproduto());
        values.put("tipomovimento",estoque.getTipomovimento());
        values.put("qtdemov",estoque.getQtdemov());
        values.put("datamov",estoque.getDatamov());

        String[] args = {estoque.getId().toString()};
        getWritableDatabase().update("estoquemov",values,"id=?",args);

    }

    public void deletarEstoques(Estoque estoque){
        String[] args = {estoque.getId().toString()};
        getWritableDatabase().delete("estoquemov","id=?",args);
    }

    // lista - mostrar

}
