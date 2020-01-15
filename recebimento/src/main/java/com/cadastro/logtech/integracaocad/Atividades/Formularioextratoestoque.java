package com.cadastro.logtech.integracaocad.Atividades;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.cadastro.logtech.integracaocad.R;
import com.cadastro.logtech.integracaocad.integracaocadastro.BDHelper.EstoqueBd;
import com.cadastro.logtech.integracaocad.integracaocadastro.BDHelper.EstoqueSldBd;
import com.cadastro.logtech.integracaocad.integracaocadastro.BDHelper.ProdutosBd;
import com.cadastro.logtech.integracaocad.integracaocadastro.model.Estoque;
import com.cadastro.logtech.integracaocad.integracaocadastro.model.EstoqueSld;
import com.cadastro.logtech.integracaocad.integracaocadastro.model.Produtos;

import java.util.ArrayList;

public class Formularioextratoestoque extends AppCompatActivity {
    private EditText editText_Caption;
    private Button btn_Retorna;
    private Button btn_ListaSaldo;
    private ListView ListaSaldo;
    private ArrayList<Estoque> listview_Saldo;
    EstoqueBd bdHelper;
    Estoque estoque;
    ArrayAdapter adapter;

    private EstoqueSld estoquesld;
    private Produtos produtos;
    private ProdutosBd BuscaProd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("WMS Expert - [ Extrato de Estoque ]");
        setContentView(R.layout.activity_extratoestoque);

        bdHelper = new EstoqueBd(Formularioextratoestoque.this);;
        BuscaProd = new ProdutosBd(Formularioextratoestoque.this);
        estoque = new Estoque();
        estoquesld = new EstoqueSld();

        editText_Caption = (EditText) findViewById(R.id.editText_Caption);
        btn_Retorna = (Button) findViewById(R.id.btn_Retorna);
        ListaSaldo = (ListView) findViewById(R.id.listview_Saldo);

        btn_Retorna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(ListaSaldo.getWindowToken(), 0);

        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(editText_Caption.getWindowToken(), 0);


        carregarExtratosEstoque(); //chama a listagem

    }

    public void carregarExtratosEstoque(){
        bdHelper = new EstoqueBd(Formularioextratoestoque.this);
        listview_Saldo = getLista();
        bdHelper.close();

        if (listview_Saldo != null){
            adapter = new ArrayAdapter<Estoque>(Formularioextratoestoque.this, android.R.layout.simple_list_item_1, listview_Saldo);
            ListaSaldo.setAdapter(adapter);
        }
    }

    public ArrayList<Estoque> getLista(){
        String[] columns ={"id","codproduto","tipomovimento","qtdemov", "datamov"};
        Cursor cursor = bdHelper.getWritableDatabase().query("estoquemov",columns,null,null,null,null,null,null);
        ArrayList<Estoque> estoques = new ArrayList<Estoque>();
        produtos = new Produtos();
        BuscaProd = new ProdutosBd(Formularioextratoestoque.this);
        while (cursor.moveToNext()){
            Estoque estoque = new Estoque();
            estoque.setId(cursor.getLong(0));
            estoque.setCodproduto(cursor.getString(1));
            produtos = BuscaProd.carregaDadoByCodBarra(cursor.getString(1));
            estoque.setTipomovimento(cursor.getString(1) + "    " + produtos.getDescricao() + "   " + cursor.getString(2) + "    " + cursor.getString(3) + "    " + cursor.getString(4));
            estoque.setQtdemov(cursor.getString(3));
            estoque.setDatamov(cursor.getString(4));
            estoques.add(estoque);
        }
        return estoques;

    }

}
