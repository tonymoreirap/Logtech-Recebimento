package com.cadastro.logtech.integracaocad.integracaocadastro.BDHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cadastro.logtech.integracaocad.integracaocadastro.model.Fornecedores;

import java.util.ArrayList;

public class FornecedoresBd extends SQLiteOpenHelper {

    private static final String DATABASE ="bdfornecedores";
    private  static final int VERSION = 1;

    public FornecedoresBd (Context context){
        super(context, DATABASE,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String fornecedor = "CREATE TABLE fornecedores(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nomeFornecedor TEXT NOT NULL, RazaoSocial TEXT NOT NULL, CNPJ TEXT NOT NULL);";
        db.execSQL(fornecedor);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String fornecedor = "DROP TABLE IF EXISTS fornecedores";
        db.execSQL(fornecedor);
    }

    // aqui salva
    public void salvarFornecedor(Fornecedores fornecedores){
        ContentValues values = new ContentValues();

        values.put("nomeFornecedor",fornecedores.getnomeFornecedor());
        values.put("RazaoSocial",fornecedores.getRazaoSocial());
        values.put("CNPJ",fornecedores.getCNPJ());

        getWritableDatabase().insert("fornecedores",null,values);
    }

    // metodo alterar concluído ↓ :D
    public void alterarFornecedor(Fornecedores fornecedor){
        ContentValues values = new ContentValues();

        values.put("nomeFornecedor",fornecedor.getnomeFornecedor());
        values.put("RazaoSocial",fornecedor.getRazaoSocial());
        values.put("CNPJ",fornecedor.getCNPJ());

        String[] args = {fornecedor.getId().toString()};
        getWritableDatabase().update("fornecedores",values,"id=?",args);

    }

    public void deletarFornecedor(Fornecedores fornecedor){
        String[] args = {fornecedor.getId().toString()};
        getWritableDatabase().delete("fornecedores","id=?",args);
    }

    // lista - mostrar

    public ArrayList<Fornecedores> getLista(){
        String[] columns ={"id","nomeFornecedor","RazaoSocial","CNPJ"};
        Cursor cursor = getWritableDatabase().query("fornecedores",columns,null,null,null,null,null,null);
        ArrayList<Fornecedores> fornecedores = new ArrayList<Fornecedores>();

        while (cursor.moveToNext()){
            Fornecedores fornecedor = new Fornecedores();
            fornecedor.setId(cursor.getLong(0));
            fornecedor.setnomeFornecedor(cursor.getString(1));
            fornecedor.setRazaoSocial(cursor.getString(2));
            fornecedor.setCNPJ(cursor.getString(3));

            fornecedores.add(fornecedor);
        }
        return fornecedores;
    }



}
