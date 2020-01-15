package com.cadastro.logtech.integracaocad.integracaocadastro.BDHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cadastro.logtech.integracaocad.integracaocadastro.model.Clientes;

import java.util.ArrayList;

public class ClientesBd extends SQLiteOpenHelper {

    private static final String DATABASE ="bdclientes";
    private  static final int VERSION = 1;

    public ClientesBd (Context context){
        super(context, DATABASE,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String cliente = "CREATE TABLE clientes(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nomeCliente TEXT NOT NULL, RazaoSocial TEXT NOT NULL, CNPJCPF TEXT NOT NULL);";
        db.execSQL(cliente);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String cliente = "DROP TABLE IF EXISTS clientes";
        db.execSQL(cliente);
    }

    // aqui salva
    public void salvarCliente(Clientes clientes){
        ContentValues values = new ContentValues();

        values.put("nomeCliente",clientes.getnomeCliente());
        values.put("RazaoSocial",clientes.getRazaoSocial());
        values.put("CNPJCPF",clientes.getCNPJCPF());

        getWritableDatabase().insert("clientes",null,values);
    }

    // metodo alterar concluído ↓ :D
    public void alterarCliente(Clientes clientes){
        ContentValues values = new ContentValues();

        values.put("nomeCliente",clientes.getnomeCliente());
        values.put("RazaoSocial",clientes.getRazaoSocial());
        values.put("CNPJCPF",clientes.getCNPJCPF());

        String[] args = {clientes.getId().toString()};
        getWritableDatabase().update("clientes",values,"id=?",args);

    }

    public void deletarFornecedor(Clientes clientes){
        String[] args = {clientes.getId().toString()};
        getWritableDatabase().delete("clientes","id=?",args);
    }

    // lista - mostrar

    public ArrayList<Clientes> getLista(){
        String[] columns ={"id","nomeCliente","RazaoSocial","CNPJCPF"};
        Cursor cursor = getWritableDatabase().query("clientes",columns,null,null,null,null,null,null);
        ArrayList<Clientes> clientes = new ArrayList<Clientes>();

        while (cursor.moveToNext()){
            Clientes cliente = new Clientes();
            cliente.setId(cursor.getLong(0));
            cliente.setnomeCliente(cursor.getString(1));
            cliente.setRazaoSocial(cursor.getString(2));
            cliente.setCNPJCPF(cursor.getString(3));

            clientes.add(cliente);
        }
        return clientes;
    }

}
