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
import com.cadastro.logtech.integracaocad.integracaocadastro.BDHelper.ClientesBd;
import com.cadastro.logtech.integracaocad.integracaocadastro.model.Clientes;

import java.util.ArrayList;

public class FormularioClientes extends AppCompatActivity {
    EditText editText_NomeCliente, editText_RazaoSocial, editText_CNPJCPF;
    Button btn_GravaCliente; //INCLUIR
    Button btn_AlteraCliente; //ALTERAR
    Button btn_ExcluirCliente; //EXCLUIR
    Switch btn_habdestecla; //vai habilitar e desabilitar o teclado
    Clientes editarCliente, cliente;
    ClientesBd bdHelper;

    ListView listaCliente;
    ArrayList<Clientes> listview_Clientes;
    ArrayAdapter adapter;
    private Long CodId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("WMS Expert - [ Clientes ]");
        setContentView(R.layout.activity_formulario_clientes);

        cliente = new Clientes();
        bdHelper = new ClientesBd(FormularioClientes.this);

        Intent intent = getIntent();
        editarCliente = (Clientes) intent.getSerializableExtra("cliente-escolhido");

        editText_NomeCliente = (EditText) findViewById(R.id.editText_NomeCliente);
        editText_RazaoSocial = (EditText) findViewById(R.id.editText_RazaoSoc);
        editText_CNPJCPF =(EditText) findViewById(R.id.editText_Cnpjcpf);

        btn_GravaCliente = (Button) findViewById(R.id.btn_GravaCliente);
        btn_AlteraCliente = (Button) findViewById(R.id.btn_AlteraCliente);
        btn_ExcluirCliente = (Button) findViewById(R.id.btn_ExcluirCliente);

        btn_habdestecla = (Switch) findViewById(R.id.btn_habdestecla);

        listaCliente = (ListView) findViewById(R.id.listview_Clientes);
        registerForContextMenu(listaCliente);

        if (editarCliente !=null){

            editText_NomeCliente.setText(editarCliente.getnomeCliente());
            editText_RazaoSocial.setText(editarCliente.getRazaoSocial());
            editText_CNPJCPF.setText(editarCliente.getCNPJCPF()+"");

            cliente.setId(editarCliente.getId());

        }


        btn_GravaCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cliente.setnomeCliente(editText_NomeCliente.getText().toString());
                cliente.setRazaoSocial(editText_RazaoSocial.getText().toString());
                cliente.setCNPJCPF(editText_CNPJCPF.getText().toString());

                bdHelper.salvarCliente(cliente);
                bdHelper.close();

                carregarCliente(); //lista o fornecedor na lista
            }
        });

        btn_AlteraCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cliente.setId(CodId);
                cliente.setnomeCliente(editText_NomeCliente.getText().toString());
                cliente.setRazaoSocial(editText_RazaoSocial.getText().toString());
                cliente.setCNPJCPF(editText_CNPJCPF.getText().toString());

                bdHelper.alterarCliente(cliente);
                bdHelper.close();

                carregarCliente(); //lista o fornecedor na lista

            }

        });

        btn_ExcluirCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cliente.setId(CodId);
                cliente.setnomeCliente(editText_RazaoSocial.getText().toString());
                cliente.setRazaoSocial(editText_RazaoSocial.getText().toString());
                cliente.setCNPJCPF(editText_CNPJCPF.getText().toString());

                bdHelper = new ClientesBd(FormularioClientes.this);
                bdHelper.deletarFornecedor(cliente);
                bdHelper.close();

                carregarCliente(); //lista o fornecedor na lista

            }

        });

        listaCliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

                Clientes fornecedorEscolhido = (Clientes) adapter.getItemAtPosition(position);

                CodId = fornecedorEscolhido.getId();
                editText_NomeCliente.setText(fornecedorEscolhido.getnomeCliente().toString());
                editText_RazaoSocial.setText(fornecedorEscolhido.getRazaoSocial().toString());
                editText_CNPJCPF.setText(fornecedorEscolhido.getCNPJCPF());
            }
        });

        listaCliente.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                cliente = (Clientes) adapter.getItemAtPosition(position);
                return false;
            }
        });

        btn_habdestecla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn_habdestecla.isChecked()){
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(editText_NomeCliente.getWindowToken(), 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(editText_RazaoSocial.getWindowToken(), 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(editText_CNPJCPF.getWindowToken(), 0);

                }
                else {

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .showSoftInput(editText_NomeCliente, 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .showSoftInput(editText_RazaoSocial, 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .showSoftInput(editText_CNPJCPF, 0);
                }
            }
        });

        editText_NomeCliente.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

        editText_CNPJCPF.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                btn_habdestecla.setChecked(false);
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem menuDelete = menu.add("Deletar Este Cliente");
        menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                bdHelper = new ClientesBd(FormularioClientes.this);
                bdHelper.deletarFornecedor(cliente);
                bdHelper.close();
                carregarCliente();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarCliente();
    }

    public void carregarCliente(){
        bdHelper = new ClientesBd(FormularioClientes.this);
        listview_Clientes = bdHelper.getLista();
        bdHelper.close();

        if (listview_Clientes != null){
            adapter = new ArrayAdapter<Clientes>(FormularioClientes.this, android.R.layout.simple_list_item_1, listview_Clientes);
            listaCliente.setAdapter(adapter);
        }

    }

}
