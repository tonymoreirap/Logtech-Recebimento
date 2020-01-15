package com.cadastro.logtech.integracaocad.integracaocadastro.BDHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cadastro.logtech.integracaocad.integracaocadastro.model.EstoqueSld;

import java.util.ArrayList;

public class EstoqueSldBd extends SQLiteOpenHelper {
    private static final String DATABASE ="bdestoquesld";
    private static final int VERSION = 1;
    private static EstoqueBd estoquebd;

    public EstoqueSldBd (Context context){
        super(context, DATABASE,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String estoquesld = "CREATE TABLE estoquesld(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, codproduto TEXT NOT NULL, tipomovimento TEXT, nomeproduto TEXT, SaldoEst TEXT, datamov TEXT);";
        db.execSQL(estoquesld);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String estoquesld = "DROP TABLE IF EXISTS estoquesld";
        db.execSQL(estoquesld);
    }

    // aqui salva
    public void salvarEstoquesld(EstoqueSld estoquesld){
        ContentValues values = new ContentValues();

        values.put("codproduto",estoquesld.getCodproduto());
        values.put("tipomovimento",estoquesld.getTipomovimento());
        values.put("nomeproduto",estoquesld.getNomeproduto());
        values.put("saldoest",estoquesld.getSaldoEst());
        values.put("datamov",estoquesld.getDatamov());

        getWritableDatabase().insert("estoquesld",null,values);
    }

    // metodo alterar concluído ↓ :D
    public void alterarEstoquesld(EstoqueSld estoquesld){
        ContentValues values = new ContentValues();

        values.put("codproduto",estoquesld.getCodproduto());
        values.put("tipomovimento",estoquesld.getTipomovimento());
        values.put("nomeproduto",estoquesld.getNomeproduto());
        values.put("saldoest",estoquesld.getSaldoEst());
        values.put("datamov",estoquesld.getDatamov());

        String[] args = {estoquesld.getId().toString()};
        getWritableDatabase().update("estoquesld",values,"id=?",args);

    }

    public void deletarEstoquesld(EstoqueSld estoquesld){
        String[] args = {estoquesld.getId().toString()};
        getWritableDatabase().delete("estoquesld","id=?",args);
    }

    public void deletaEtoquesldAll(){
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL("DELETE FROM estoquesld");
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    // lista - mostrar

    public ArrayList<EstoqueSld> getLista(){
        String[] columns ={"id","codproduto","tipomovimento","nomeproduto","saldoest", "datamov"};
        Cursor cursor = getWritableDatabase().query("estoquesld",columns,null,null,null,null,null,null);
        ArrayList<EstoqueSld> estoquesld = new ArrayList<EstoqueSld>();

        while (cursor.moveToNext()){
            EstoqueSld estoque = new EstoqueSld();
            estoque.setId(cursor.getLong(0));
            estoque.setCodproduto(cursor.getString(1));
            estoque.setTipomovimento(cursor.getString(2));
            estoque.setNomeproduto(cursor.getString(3));
            estoque.setSaldoEst(cursor.getString(4));
            estoque.setDatamov(cursor.getString(5));
            estoquesld.add(estoque);
        }
        return estoquesld;

    }

    public ArrayList<EstoqueSld> getListaSaldo(){
        String[] columns ={"codproduto","nomeproduto","saldoest"};
        Cursor cursor = getWritableDatabase().query("estoquesld",columns,null,null,null,null,null,null);
        ArrayList<EstoqueSld> estoquesld = new ArrayList<EstoqueSld>();

        while (cursor.moveToNext()){
            EstoqueSld estoque = new EstoqueSld();
            estoque.setCodproduto(cursor.getString(0));
            estoque.setNomeproduto(cursor.getString(0) + "   " + cursor.getString(1) + "   " + cursor.getString(2));
            estoque.setSaldoEst(cursor.getString(2));
            estoquesld.add(estoque);
        }
        return estoquesld;

    }


}
