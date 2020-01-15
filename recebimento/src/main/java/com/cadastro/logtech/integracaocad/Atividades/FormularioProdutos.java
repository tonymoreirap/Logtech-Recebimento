package com.cadastro.logtech.integracaocad.Atividades;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.cadastro.logtech.integracaocad.R;
import com.cadastro.logtech.integracaocad.integracaocadastro.BDHelper.ProdutosBd;
import com.cadastro.logtech.integracaocad.integracaocadastro.model.Produtos;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class FormularioProdutos extends AppCompatActivity{
    EditText editText_CodBarra;
    EditText editText_NomeProd;
    EditText editText_Descricao;
    EditText editText_Quantidade;
    EditText editText_Fat;
    EditText editText_Emb;
    EditText editText_QtdPed;
    Button btn_Gravaprod; //INCLUIR
    Switch btn_habdestecla; //vai habilitar e desabilitar o teclado
    Switch btn_scancodbar;
    Produtos editarProduto, produto;
    ProdutosBd bdHelper;

    ListView listaProd;
    ArrayList<Produtos> listview_Produtos;
    ArrayAdapter adapter;
    private Long CodId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("WMS Expert - [ Produtos ]");
        setContentView(R.layout.activity_formulario_produtos);

        produto = new Produtos();
        bdHelper = new ProdutosBd(FormularioProdutos.this);

        Intent intent = getIntent();
        editarProduto = (Produtos) intent.getSerializableExtra("produto-escolhido");

        editText_CodBarra = (EditText) findViewById(R.id.editText_CodBarra);
        editText_NomeProd = (EditText) findViewById(R.id.editText_NomeProd);
        editText_Descricao = (EditText) findViewById(R.id.editText_Descricao);
        editText_Quantidade =(EditText) findViewById(R.id.editText_Quantidade);
        editText_Fat  = (EditText) findViewById(R.id.editText_Fat);
        editText_Emb  = (EditText) findViewById(R.id.editText_Emb);
        editText_QtdPed  = (EditText) findViewById(R.id.editText_QtdPed);

        btn_Gravaprod = (Button) findViewById(R.id.btn_Gravaprod);;

        listaProd = (ListView) findViewById(R.id.listview_Produtos);

        btn_habdestecla = (Switch) findViewById(R.id.btn_habdestecla);

        btn_scancodbar = (Switch) findViewById(R.id.btn_scancodbar);

        registerForContextMenu(listaProd);

        if (editarProduto !=null){

            editText_CodBarra.setText(editarProduto.getCodBarra());
            editText_NomeProd.setText(editarProduto.getNomeProduto());
            editText_Descricao.setText(editarProduto.getDescricao());
            editText_Fat.setText(editarProduto.getFator());
            editText_Emb.setText(editarProduto.getEmbalagem());
            editText_QtdPed.setText(editarProduto.getQuantidade_pedida());
            editText_Quantidade.setText(editarProduto.getQuantidade()+"");

            produto.setId(editarProduto.getId());

        }

        btn_Gravaprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(produto.getId() == null) {

                    produto.setCodBarra(editText_CodBarra.getText().toString());
                    produto.setNomeProduto(editText_NomeProd.getText().toString());
                    produto.setDescricao(editText_Descricao.getText().toString());
                    produto.setEmbalagem(editText_Emb.getText().toString());
                    produto.setFator(editText_Fat.getText().toString());
                    produto.setQuantidade_pedida(editText_QtdPed.getText().toString());
                    produto.setQuantidade(Integer.parseInt(editText_Quantidade.getText().toString()));
                    bdHelper.salvarProduto(produto);
                    bdHelper.close();

                    carregarProduto(); //lista o produto na lista
                    LimpaProduto(); //limpa os campos
                }
                else
                {
                    produto.setId(CodId);
                    produto.setCodBarra(editText_CodBarra.getText().toString());
                    produto.setNomeProduto(editText_NomeProd.getText().toString());
                    produto.setDescricao(editText_Descricao.getText().toString());
                    produto.setEmbalagem(editText_Emb.getText().toString());
                    produto.setFator(editText_Fat.getText().toString());
                    produto.setQuantidade_pedida(editText_QtdPed.getText().toString());
                    produto.setQuantidade(Integer.parseInt(editText_Quantidade.getText().toString()));
                    bdHelper.alterarProduto(produto);
                    bdHelper.close();

                    carregarProduto(); //lista o produto na lista
                    LimpaProduto(); //limpa os campos
                }
            }
        });

        listaProd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

                Produtos produtoEscolhido = (Produtos) adapter.getItemAtPosition(position);

                CodId = produtoEscolhido.getId();
                editText_CodBarra.setText(produtoEscolhido.getCodBarra().toString());
                editText_NomeProd.setText(produtoEscolhido.getNomeProduto().toString());
                editText_Descricao.setText(produtoEscolhido.getDescricao().toString());
                editText_Fat.setText(produtoEscolhido.getFator());
                editText_Emb.setText(produtoEscolhido.getEmbalagem());
                editText_QtdPed.setText(produtoEscolhido.getQuantidade_pedida());
                editText_Quantidade.setText(String.valueOf(produtoEscolhido.getQuantidade()));
            }
        });

        listaProd.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                produto = (Produtos) adapter.getItemAtPosition(position);
                return false;
            }
        });

        btn_habdestecla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager HabDesabilita = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                if(btn_habdestecla.isChecked()){

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(editText_CodBarra.getWindowToken(), 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(editText_NomeProd.getWindowToken(), 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(editText_Descricao.getWindowToken(), 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(editText_Fat.getWindowToken(), 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(editText_NomeProd.getWindowToken(), 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(editText_Emb.getWindowToken(), 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(editText_QtdPed.getWindowToken(), 0);

                }
                else {

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(editText_CodBarra.getWindowToken(), 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(editText_NomeProd.getWindowToken(), 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(editText_Descricao.getWindowToken(), 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(editText_Quantidade.getWindowToken(), 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(editText_Fat.getWindowToken(), 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(editText_Emb.getWindowToken(), 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(editText_QtdPed.getWindowToken(), 0);

                }
            }
        });

        btn_scancodbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn_scancodbar.isChecked()){
                    ChamaScann();
                }
            }
        });

    }

    public void ChamaScann(){new IntentIntegrator((Activity)this).initiateScan();}

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem menuDelete = menu.add("Deletar Este Produto");
        menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                bdHelper = new ProdutosBd(FormularioProdutos.this);
                bdHelper.deletarProduto(produto);
                bdHelper.close();
                carregarProduto();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarProduto();
    }

    public void carregarProduto(){
        bdHelper = new ProdutosBd(FormularioProdutos.this);
        listview_Produtos = bdHelper.getLista();
        bdHelper.close();

        if (listview_Produtos != null){
            adapter = new ArrayAdapter<Produtos>(FormularioProdutos.this, android.R.layout.simple_list_item_1, listview_Produtos);
            listaProd.setAdapter(adapter);
        }

    }

    public void LimpaProduto(){
        editText_CodBarra.setText("");
        editText_NomeProd.setText("");
        editText_Descricao.setText("");
        editText_Fat.setText("");
        editText_Emb.setText("");
        editText_QtdPed.setText("");
        editText_Quantidade.setText("");
    }


    public void scanBarcodeCustomOptions(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Saiu do leitor de codigo de barra", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scaneando: " + result.getContents(), Toast.LENGTH_LONG).show();
                editText_CodBarra.setText(result.getContents());
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * Sample of scanning from a Fragment
     */
    public static class ScanFragment extends Fragment {
        private String toast;

        public ScanFragment() {
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            displayToast();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_scan, container, false);
            Button scan = (Button) view.findViewById(R.id.scan_from_fragment);
            scan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scanFromFragment();
                }
            });
            return view;
        }

        public void scanFromFragment() {
            IntentIntegrator.forFragment(this).initiateScan();
        }

        private void displayToast() {
            if(getActivity() != null && toast != null) {
                Toast.makeText(getActivity(), toast, Toast.LENGTH_LONG).show();
                toast = null;
            }
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if(result != null) {
                if(result.getContents() == null) {
                    toast = "Cancelled from fragment";
                } else {
                    toast = "Scanned from fragment: " + result.getContents();
                }

                // At this point we may or may not have a reference to the activity
                displayToast();
            }
        }
    }


}
