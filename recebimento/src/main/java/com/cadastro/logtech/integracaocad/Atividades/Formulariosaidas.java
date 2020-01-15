package com.cadastro.logtech.integracaocad.Atividades;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.cadastro.logtech.integracaocad.R;
import com.cadastro.logtech.integracaocad.integracaocadastro.BDHelper.EstoqueBd;
import com.cadastro.logtech.integracaocad.integracaocadastro.BDHelper.ProdutosBd;
import com.cadastro.logtech.integracaocad.integracaocadastro.BDHelper.SaidasBd;
import com.cadastro.logtech.integracaocad.integracaocadastro.model.Estoque;
import com.cadastro.logtech.integracaocad.integracaocadastro.model.Produtos;
import com.cadastro.logtech.integracaocad.integracaocadastro.model.Saidas;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Formulariosaidas extends AppCompatActivity {
    EditText editText_CodBarra;
    EditText editText_NomeProduto;
    EditText editText_Descricao;
    EditText editText_Quantidade;
    EditText editText_Emb;
    EditText editText_Fat;
    EditText editText_QtdPed;

    Button btn_GravaSaidas; //Grava Saidas
    Switch btn_habdestecla;
    Switch btn_scancodbar; //vai habilitar e desabilitar o teclado
    Saidas editarSaidas, saidas;
    SaidasBd bdHelper;
    Estoque estoque;
    EstoqueBd bdHelperEstMov;
    private Produtos produtos;

    ListView listaSaidas;
    ArrayList<Saidas> listview_Saidas;
    ArrayAdapter adapter;
    private Object Date;
    private Long CodId;
    private ProdutosBd BuscaProd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("WMS Expert - [ Saidas ]");
        setContentView(R.layout.activity_formulario_saidas);

        saidas = new Saidas();
        estoque = new Estoque();
        bdHelper = new SaidasBd(Formulariosaidas.this);
        bdHelperEstMov = new EstoqueBd(Formulariosaidas.this);

        Intent intent = getIntent();
        editarSaidas = (Saidas) intent.getSerializableExtra("saida-escolhida");

        editText_CodBarra = (EditText) findViewById(R.id.editText_CodBarra);
        editText_NomeProduto = (EditText) findViewById(R.id.editText_NomeProduto);
        editText_Descricao = (EditText) findViewById(R.id.editText_Descricao);
        editText_Quantidade =(EditText) findViewById(R.id.editText_Quantidade);

        editText_Emb = (EditText) findViewById(R.id.editText_Emb);
        editText_Fat = (EditText) findViewById(R.id.editText_Fat);
        editText_QtdPed = (EditText) findViewById(R.id.editText_QtdPed);

        btn_GravaSaidas = (Button) findViewById(R.id.btn_GravaSaida);
        btn_habdestecla = (Switch) findViewById(R.id.btn_habdestecla);
        btn_scancodbar = (Switch) findViewById(R.id.btn_scancodbar);

        BuscaProd = new ProdutosBd(getBaseContext());

        if (editarSaidas !=null){

            editText_CodBarra.setText(editarSaidas.getCodproduto());
            editText_Quantidade.setText(editarSaidas.getQtdemov());
            saidas.setId(editarSaidas.getId());

        }

        btn_GravaSaidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(saidas.getId() == null) {

                    saidas.setCodproduto(editText_CodBarra.getText().toString());
                    saidas.setTipomovimento("SAIDAS");
                    saidas.setQtdemov(editText_Quantidade.getText().toString());
                    bdHelper.salvarSaidas(saidas);
                    bdHelper.close(); //grava as saidas na movimentação do estoque
                    //****************************************************************************************
                    //grava movimentação estoque EDNARDO HONORATO*********************************************
                    //****************************************************************************************
                    estoque.setCodproduto(editText_CodBarra.getText().toString());
                    estoque.setTipomovimento("SAIDAS");
                    estoque.setQtdemov(editText_Quantidade.getText().toString());

                    bdHelperEstMov.salvarEstoques(estoque);
                    bdHelperEstMov.close();
                    LimpaCampos();

                }
                else
                {
                    saidas.setId(CodId);
                    saidas.setCodproduto(editText_CodBarra.getText().toString());
                    saidas.setTipomovimento("SAIDAS");
                    saidas.setQtdemov(editText_Quantidade.getText().toString());

                    bdHelper.alterarSaidas(saidas);
                    bdHelper.close();
                    LimpaCampos();

                }

            }
        });

        editText_CodBarra.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                ChamaScann();
                editText_CodBarra.clearFocus();
            }
        });

        editText_CodBarra.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    produtos = BuscaProd.carregaDadoByCodBarra(editText_CodBarra.getText().toString());
                    editText_NomeProduto.setText(produtos.getDescricao());
                    editText_Emb.setText(produtos.getEmbalagem());
                    editText_Fat.setText(produtos.getFator());
                    editText_QtdPed.setText(produtos.getQuantidade_pedida());
                    editText_Quantidade.requestFocus();
                    return true;
                }
                return false;
            }
        });

        btn_scancodbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn_scancodbar.isChecked()){
                    ChamaScann();
                }
                else
                {
                    ChamaScann();
                }
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
                            .hideSoftInputFromWindow(editText_NomeProduto.getWindowToken(), 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(editText_Quantidade.getWindowToken(), 0);

                }
                else {

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(editText_CodBarra.getWindowToken(), 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(editText_NomeProduto.getWindowToken(), 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(editText_Quantidade.getWindowToken(), 0);

                }
            }
        });


    }

    public void ChamaScann(){new IntentIntegrator((Activity)this).initiateScan();}

    public void ConsultaCodBarra(){
        produtos = BuscaProd.carregaDadoByCodBarra(editText_CodBarra.getText().toString());
        editText_NomeProduto.setText(produtos.getDescricao());
        editText_Emb.setText(produtos.getEmbalagem());
        editText_Fat.setText(produtos.getFator());
        editText_QtdPed.setText(produtos.getQuantidade_pedida());
        editText_Quantidade.requestFocus();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem menuDelete = menu.add("Deletar Esta Saida");
        menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                bdHelper = new SaidasBd(Formulariosaidas.this);
                bdHelper.deletarSaidas(saidas);
                bdHelper.close();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void LimpaCampos(){
        editText_CodBarra.setText("");
        editText_NomeProduto.setText("");
        editText_Quantidade.setText("");
        editText_Emb.setText("");
        editText_Fat.setText("");
        editText_QtdPed.setText("");
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
                if(editText_CodBarra.getText() != null){
                    ConsultaCodBarra();
                }
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
