package com.cadastro.logtech.integracaocad.Atividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cadastro.logtech.integracaocad.R;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    /* Declara as variaveis de referencias dos componentes */
    private ListView listOpc;
    List<String> opcoes;
    ArrayAdapter<String> adaptador;
    TextView txtHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("WMS Expert - Recebimentos");
        setContentView(R.layout.activity_home);
        listOpc = (ListView) findViewById(R.id.listOpc);
        txtHome = (TextView) findViewById(R.id.txtHome);

        opcoes = new ArrayList<String>();

        opcoes.add("    Produto                ");
        opcoes.add("    Fornecedor             ");
        opcoes.add("    Clientes               ");
        opcoes.add("    Entradas               ");
        opcoes.add("    Saidas                 ");
        opcoes.add("    Saldo de Estoque       ");
        opcoes.add("    Extrato do Produto     ");
        opcoes.add("    Sair                   ");

        adaptador = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_list_item_1, opcoes);
        listOpc.setAdapter(adaptador);
        listOpc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long l) {
                switch (posicao){
                    case 0: onChamaProduto();
                        break;
                    case 1: onChamaFornecedor();
                        break;
                    case 2: onChamaClientes();
                        break;
                    case 3: onChamaEntradas();
                        break;
                    case 4: onChamaSaidas();
                        break;
                    case 5: onChamaListagemEstoque();
                        break;
                    case 6: onChamaListaDeExtrato();
                        break;
                    case 7: Sair();
                        break;
                }
            }
        });
    }

    private void onChamaProduto(){
        Intent intent = new Intent(HomeActivity.this, FormularioProdutos.class);
        startActivity(intent);
    }

    private void onChamaFornecedor(){
        Intent intent = new Intent( HomeActivity.this, FormularioFornecedores.class);
        startActivity(intent);
    }

    private void onChamaClientes(){
        Intent intent = new Intent( HomeActivity.this, FormularioClientes.class);
        startActivity(intent);
    }

    private void onChamaEntradas() {
        Intent intent = new Intent( HomeActivity.this, Formularioentradas.class);
        startActivity(intent);
    }

    private void onChamaSaidas() {
        Intent intent = new Intent( HomeActivity.this, Formulariosaidas.class);
        startActivity(intent);
    }

    private void onChamaListagemEstoque() {
        //listagem de estoque
        Intent intent = new Intent( HomeActivity.this, Formulariolistagemestoque.class);
        startActivity(intent);
    }

    private void onChamaListaDeExtrato() {
        Intent intent = new Intent( HomeActivity.this, Formularioextratoestoque.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    public void Sair(){
        Intent it = new Intent(getApplicationContext(), LoginActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        it.putExtra("SAIR", true);
        startActivity(it);
    }

}
