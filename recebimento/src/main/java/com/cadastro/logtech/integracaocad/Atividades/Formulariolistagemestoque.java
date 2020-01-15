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
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cadastro.logtech.integracaocad.R;
import com.cadastro.logtech.integracaocad.integracaocadastro.BDHelper.EstoqueBd;
import com.cadastro.logtech.integracaocad.integracaocadastro.BDHelper.EstoqueSldBd;
import com.cadastro.logtech.integracaocad.integracaocadastro.BDHelper.ProdutosBd;
import com.cadastro.logtech.integracaocad.integracaocadastro.model.EstoqueSld;
import com.cadastro.logtech.integracaocad.integracaocadastro.model.Produtos;

import java.util.ArrayList;

public class Formulariolistagemestoque extends AppCompatActivity {
    private EditText editText_Caption;
    private Button btn_Retorna;
    private Button btn_ListaSaldo;
    private ListView ListaSaldo;
    private ArrayList<EstoqueSld> listview_Saldo;
    EstoqueBd bdHelper;
    ArrayAdapter adapter;

    private EstoqueSldBd estoquesldbd;
    private EstoqueSld estoquesld;
    private Produtos produtos;
    private ProdutosBd BuscaProd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("WMS Expert - [ Saldo de Estoque ]");
        setContentView(R.layout.activity_listagemestoque);

        bdHelper = new EstoqueBd(Formulariolistagemestoque.this);
        estoquesldbd = new EstoqueSldBd(Formulariolistagemestoque.this);
        BuscaProd = new ProdutosBd(Formulariolistagemestoque.this);

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


        carregarSaldos(); //chama a listagem

    }

    public void carregarSaldos(){
        //LIMPA A TABELA DE SALDOS
        estoquesldbd.deletaEtoquesldAll();
        GravaMovEstoque();
    }


    //GRAVA DADOS NA TABELA DE MOVIMENTAÇÃO
    public void GravaMovEstoque(){
        try {
            String sql = "select tbe.codproduto, (tbe.qdsaldoe - tbs.qdsaldos) as saldoest "+
                         "from(select cast(x.codproduto as varchar(10)) as codproduto,     "+
                         "       cast(x.tipomovimento as varchar(100)) as tipomovimento,   "+
                         "       sum(cast(x.qtdemov as numeric)) as qdsaldoe "+
                         "     from estoquemov x "+
                         "        where cast(x.tipomovimento as varchar(100)) = 'ENTRADAS' "+
                         "        group by cast(x.codproduto as varchar(10)), cast(x.tipomovimento as varchar(100))) as tbe, "+
                         "(select cast(x.codproduto as varchar(10)) as codproduto, "+
                         "        cast(x.tipomovimento as varchar(100)) as tipomovimento,  "+
                         "        sum(cast(x.qtdemov as numeric)) as qdsaldos "+
                         "from estoquemov x "+
                         "  where cast(x.tipomovimento as varchar(100)) = 'SAIDAS' "+
                         "group by cast(x.codproduto as varchar(10)), cast(x.tipomovimento as varchar(100))) as tbs "+
                         "where tbe.codproduto = tbs.codproduto ";

            SQLiteDatabase db = bdHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                estoquesld.setCodproduto(cursor.getString(0));
                produtos = BuscaProd.carregaDadoByCodBarra(cursor.getString(0));
                estoquesld.setNomeproduto(produtos.getDescricao());
                estoquesld.setSaldoEst(cursor.getString(1));
                estoquesldbd.salvarEstoquesld(estoquesld);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        estoquesldbd = new EstoqueSldBd(Formulariolistagemestoque.this);
        listview_Saldo = estoquesldbd.getListaSaldo();
        estoquesldbd.close();

        if (listview_Saldo != null){
            adapter = new ArrayAdapter<EstoqueSld>(Formulariolistagemestoque.this, android.R.layout.simple_list_item_1, listview_Saldo);
            ListaSaldo.setAdapter(adapter);
        }


    }


}
