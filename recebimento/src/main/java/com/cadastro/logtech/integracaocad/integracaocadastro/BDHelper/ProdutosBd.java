package com.cadastro.logtech.integracaocad.integracaocadastro.BDHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cadastro.logtech.integracaocad.integracaocadastro.model.Produtos;

import java.util.ArrayList;

public class ProdutosBd extends SQLiteOpenHelper {

    private static final String DATABASE ="bdprodutos";
    private  static final int VERSION = 1;

    private SQLiteDatabase db;

    public ProdutosBd (Context context){
        super(context, DATABASE,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String produto = "CREATE TABLE produtos(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, codbarra TEXT NOT NULL, nomeproduto TEXT NOT NULL, descricao TEXT NOT NULL, quantidade_pedida TEXT, fator TXT, embalagem TXT, quantidade INTEGER);";
        db.execSQL(produto);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String produto = "DROP TABLE IF EXISTS produtos";
        db.execSQL(produto);
    }
    // aqui salva
    public void salvarProduto(Produtos produto){
        ContentValues values = new ContentValues();

        values.put("codbarra",produto.getCodBarra());
        values.put("nomeproduto",produto.getNomeProduto());
        values.put("descricao",produto.getDescricao());
        values.put("quantidade_pedida",produto.getQuantidade_pedida());
        values.put("fator",produto.getFator());
        values.put("embalagem",produto.getEmbalagem());
        values.put("quantidade",produto.getQuantidade());

        getWritableDatabase().insert("produtos",null,values);
    }
    // metodo alterar concluído ↓ :D
     public void alterarProduto(Produtos produto){
        ContentValues values = new ContentValues();

        values.put("codbarra",produto.getCodBarra());
        values.put("nomeproduto",produto.getNomeProduto());
        values.put("descricao",produto.getDescricao());
        values.put("quantidade_pedida",produto.getQuantidade_pedida());
        values.put("fator",produto.getFator());
        values.put("embalagem",produto.getEmbalagem());
        values.put("quantidade",produto.getQuantidade());

        String[] args = {produto.getId().toString()};
        getWritableDatabase().update("produtos",values,"id=?",args);

    }

    public void deletarProduto(Produtos produto){
        String[] args = {produto.getId().toString()};
        getWritableDatabase().delete("produtos","id=?",args);
    }

    // lista - mostrar

    public ArrayList<Produtos> getLista(){
        String[] columns ={"id","codbarra","nomeproduto","descricao","quantidade", "fator", "embalagem", "quantidade_pedida"};
        Cursor cursor = getWritableDatabase().query("produtos",columns,null,null,null,null,null,null);
        ArrayList<Produtos> produtos = new ArrayList<Produtos>();

        while (cursor.moveToNext()){
            Produtos produto = new Produtos();
            produto.setId(cursor.getLong(0));
            produto.setCodBarra(cursor.getString(1));
            produto.setNomeProduto(cursor.getString(2));
            produto.setDescricao(cursor.getString(3));
            produto.setQuantidade(cursor.getInt(4));
            produto.setFator(cursor.getString(5));
            produto.setEmbalagem(cursor.getString(6));
            produto.setQuantidade_pedida(cursor.getString(7));
            produtos.add(produto);
        }
        return produtos;
    }
    public Produtos carregaDadoByCodBarra(String CodBarr){
        String[] columns ={"id","codbarra","nomeproduto","descricao", "quantidade_pedida", "fator","embalagem" ,"quantidade"};
        String where = "produtos.codbarra" + "=" + CodBarr;
        Cursor cursor = getWritableDatabase().query("produtos",columns, where, null, null, null, null, null);
        Produtos produto = new Produtos();

        if (cursor.moveToNext()){
            produto.setId(cursor.getLong(0));
            produto.setCodBarra(cursor.getString(1));
            produto.setNomeProduto(cursor.getString(2));
            produto.setDescricao(cursor.getString(3));
            produto.setQuantidade_pedida(cursor.getString(4));
            produto.setFator(cursor.getString(5));
            produto.setEmbalagem(cursor.getString(6));
            produto.setQuantidade(cursor.getInt(7));
        }
        return produto;
    }





}
