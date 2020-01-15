package com.cadastro.logtech.integracaocad.Atividades;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

import com.cadastro.logtech.integracaocad.R;
import com.cadastro.logtech.integracaocad.integracaocadastro.BDHelper.FornecedoresBd;
import com.cadastro.logtech.integracaocad.integracaocadastro.model.Fornecedores;

import java.util.ArrayList;

public class FormularioFornecedores extends AppCompatActivity {
    EditText editText_NomeFornec, editText_RazaoSocial, editText_CNPJ;
    Button btn_Gravafornec; //INCLUIR
    Button btn_Alterafornec; //ALTERAR
    Button btn_Excluirfornec; //EXCLUIR
    Switch btn_habdestecla; //vai habilitar e desabilitar o teclado
    Fornecedores editarFornecedor, fornecedor;
    FornecedoresBd bdHelper;

    ListView listaFornec;
    ArrayList<Fornecedores> listview_Fornecedores;
    ArrayAdapter adapter;
    private Long CodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("WMS Expert - [ Fornecedores ]");
        setContentView(R.layout.activity_formulario_fornecedor);

        fornecedor = new Fornecedores();
        bdHelper = new FornecedoresBd(FormularioFornecedores.this);

        Intent intent = getIntent();
        editarFornecedor = (Fornecedores) intent.getSerializableExtra("fornecedor-escolhido");

        editText_NomeFornec = (EditText) findViewById(R.id.editText_NomeFornec);
        editText_RazaoSocial = (EditText) findViewById(R.id.editText_RazaoSoc);
        editText_CNPJ =(EditText) findViewById(R.id.editText_Cnpj);

        btn_Gravafornec = (Button) findViewById(R.id.btn_Gravafornec);
        btn_Alterafornec = (Button) findViewById(R.id.btn_Alterafornec);
        btn_Excluirfornec = (Button) findViewById(R.id.btn_Excluirfornec);

        btn_habdestecla = (Switch) findViewById(R.id.btn_habdestecla);

        listaFornec = (ListView) findViewById(R.id.listview_Fornecedores);
        registerForContextMenu(listaFornec);

        if (editarFornecedor !=null){

            editText_NomeFornec.setText(editarFornecedor.getnomeFornecedor());
            editText_RazaoSocial.setText(editarFornecedor.getRazaoSocial());
            editText_CNPJ.setText(editarFornecedor.getCNPJ()+"");

            fornecedor.setId(editarFornecedor.getId());

        }


        btn_Gravafornec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fornecedor.setnomeFornecedor(editText_NomeFornec.getText().toString());
                fornecedor.setRazaoSocial(editText_RazaoSocial.getText().toString());
                fornecedor.setCNPJ(editText_CNPJ.getText().toString());

                bdHelper.salvarFornecedor(fornecedor);
                bdHelper.close();

                carregarFornecedor(); //lista o fornecedor na lista
            }
        });

        btn_Alterafornec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fornecedor.setId(CodId);
                fornecedor.setnomeFornecedor(editText_NomeFornec.getText().toString());
                fornecedor.setRazaoSocial(editText_RazaoSocial.getText().toString());
                fornecedor.setCNPJ(editText_CNPJ.getText().toString());

                bdHelper.alterarFornecedor(fornecedor);
                bdHelper.close();

                carregarFornecedor(); //lista o fornecedor na lista

            }

        });

        btn_Excluirfornec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fornecedor.setId(CodId);
                fornecedor.setnomeFornecedor(editText_RazaoSocial.getText().toString());
                fornecedor.setRazaoSocial(editText_RazaoSocial.getText().toString());
                fornecedor.setCNPJ(editText_CNPJ.getText().toString());

                bdHelper = new FornecedoresBd(FormularioFornecedores.this);
                bdHelper.deletarFornecedor(fornecedor);
                bdHelper.close();

                carregarFornecedor(); //lista o fornecedor na lista

            }

        });

        listaFornec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

                Fornecedores fornecedorEscolhido = (Fornecedores) adapter.getItemAtPosition(position);

                CodId = fornecedorEscolhido.getId();
                editText_NomeFornec.setText(fornecedorEscolhido.getnomeFornecedor().toString());
                editText_RazaoSocial.setText(fornecedorEscolhido.getRazaoSocial().toString());
                editText_CNPJ.setText(fornecedorEscolhido.getCNPJ());
            }
        });

        listaFornec.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                fornecedor = (Fornecedores) adapter.getItemAtPosition(position);
                return false;
            }
        });

        btn_habdestecla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn_habdestecla.isChecked()){
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(editText_NomeFornec.getWindowToken(), 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(editText_RazaoSocial.getWindowToken(), 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(editText_CNPJ.getWindowToken(), 0);

                }
                else {

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .showSoftInput(editText_NomeFornec, 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .showSoftInput(editText_RazaoSocial, 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .showSoftInput(editText_CNPJ, 0);
                }
            }
        });

        editText_NomeFornec.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                btn_habdestecla.setChecked(false);
            }
        });

        editText_RazaoSocial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                btn_habdestecla.setChecked(false);
            }
        });

        editText_CNPJ.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                btn_habdestecla.setChecked(false);
            }
        });


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem menuDelete = menu.add("Deletar Este Fornecedor");
        menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                bdHelper = new FornecedoresBd(FormularioFornecedores.this);
                bdHelper.deletarFornecedor(fornecedor);
                bdHelper.close();
                carregarFornecedor();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarFornecedor();
    }

    public void carregarFornecedor(){
        bdHelper = new FornecedoresBd(FormularioFornecedores.this);
        listview_Fornecedores = bdHelper.getLista();
        bdHelper.close();

        if (listview_Fornecedores != null){
            adapter = new ArrayAdapter<Fornecedores>(FormularioFornecedores.this, android.R.layout.simple_list_item_1, listview_Fornecedores);
            listaFornec.setAdapter(adapter);
        }

    }

}
