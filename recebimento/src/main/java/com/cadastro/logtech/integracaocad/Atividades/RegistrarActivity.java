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
import com.cadastro.logtech.integracaocad.integracaocadastro.BDHelper.UsuariosBd;
import com.cadastro.logtech.integracaocad.integracaocadastro.model.Usuarios;

import java.util.ArrayList;

public class RegistrarActivity extends AppCompatActivity {

    /* Declara as variaveis de referencias dos componentes */
    private EditText edtNome;
    private EditText edtCelular;
    private EditText edtSenha;
    Button btn_GravaUsuario; //INCLUIR
    Button btn_AlterarUsuario; //ALTERAR
    Button btn_ExcluirUsuario; //EXCLUIR
    Switch btn_habdestecla; //vai habilitar e desabilitar o teclado
    Usuarios editarUsuario, usuario;
    UsuariosBd bdHelper;

    ListView listaUsuar;
    ArrayList<Usuarios> listview_Usuarios;
    ArrayAdapter adapter;
    private Long CodId;
    private Long id;
    private String nome;
    private String senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("WMS Expert - [ Usuarios ]");
        setContentView(R.layout.activity_registrar);

        usuario = new Usuarios(id, nome, senha);
        bdHelper = new UsuariosBd(RegistrarActivity.this);

        Intent intent = getIntent();
        editarUsuario = (Usuarios) intent.getSerializableExtra("usuario-escolhido");

        /* Busca as referencias dos componentes que estão na view e as atribui nas variaveis declaradas */
        edtCelular = (EditText) findViewById(R.id.edtCelular);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        edtNome = (EditText) findViewById(R.id.edtNome);

        btn_GravaUsuario = (Button) findViewById(R.id.btn_GravaUsuario);
        btn_AlterarUsuario = (Button) findViewById(R.id.btn_AlterarUsuario);
        btn_ExcluirUsuario = (Button) findViewById(R.id.btn_ExcluirUsuario);

        listaUsuar = (ListView) findViewById(R.id.listview_Usuarios);

        btn_habdestecla = (Switch) findViewById(R.id.btn_habdestecla);

        registerForContextMenu(listaUsuar);

        if (editarUsuario !=null){

            edtNome.setText(editarUsuario.getNome());
            edtCelular.setText(editarUsuario.getCelular());
            edtSenha.setText(editarUsuario.getSenha()+"");

            usuario.setId(editarUsuario.getId());

        }

        btn_GravaUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usuario.setNome(edtNome.getText().toString());
                usuario.setCelular(edtCelular.getText().toString());
                usuario.setSenha(edtSenha.getText().toString());

                bdHelper.salvarUsuario(usuario);
                bdHelper.close();

                carregarUsuario(); //lista o produto na lista
            }
        });

        btn_AlterarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usuario.setId(CodId);
                usuario.setNome(edtNome.getText().toString());
                usuario.setCelular(edtCelular.getText().toString());
                usuario.setSenha(edtSenha.getText().toString());

                bdHelper.alterarUsuario(usuario);
                bdHelper.close();

                carregarUsuario(); //lista o produto na lista

            }

        });

        btn_ExcluirUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usuario.setId(CodId);
                usuario.setNome(edtNome.getText().toString());
                usuario.setCelular(edtCelular.getText().toString());
                usuario.setSenha(edtSenha.getText().toString());

                bdHelper = new UsuariosBd(RegistrarActivity.this);
                bdHelper.deletarUsuario(usuario);
                bdHelper.close();

                carregarUsuario(); //lista o produto na lista

            }

        });

        listaUsuar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

                Usuarios usuarioEscolhido = (Usuarios) adapter.getItemAtPosition(position);

                CodId = usuarioEscolhido.getId();
                edtNome.setText(usuarioEscolhido.getNome().toString());
                edtCelular.setText(usuarioEscolhido.getCelular().toString());
                edtSenha.setText(usuarioEscolhido.getSenha());
            }
        });

        listaUsuar.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                usuario = (Usuarios) adapter.getItemAtPosition(position);
                return false;
            }
        });

        btn_habdestecla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn_habdestecla.isChecked()){
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(edtNome.getWindowToken(), 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(edtCelular.getWindowToken(), 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(edtSenha.getWindowToken(), 0);

                }
                else {

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .showSoftInput(edtNome, 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .showSoftInput(edtCelular, 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .showSoftInput(edtSenha, 0);
                }
            }
        });

        edtNome.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                btn_habdestecla.setChecked(false);
            }
        });

        edtCelular.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                btn_habdestecla.setChecked(false);
            }
        });

        edtSenha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                btn_habdestecla.setChecked(false);
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem menuDelete = menu.add("Deletar Este Usuario");
        menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                bdHelper = new UsuariosBd(RegistrarActivity.this);
                bdHelper.deletarUsuario(usuario);
                bdHelper.close();
                carregarUsuario();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarUsuario();
    }

    public void carregarUsuario(){
        bdHelper = new UsuariosBd(RegistrarActivity.this);
        listview_Usuarios = bdHelper.getLista();
        bdHelper.close();

        if (listview_Usuarios != null){
            adapter = new ArrayAdapter<Usuarios>(RegistrarActivity.this, android.R.layout.simple_list_item_1, listview_Usuarios);
            listaUsuar.setAdapter(adapter);
        }

    }

    /* Método limpa os campos dos componentes edittext */
    public void limparCampos(){
        edtNome.setText("");
        edtCelular.setText("");
        edtSenha.setText("");
    }
}
