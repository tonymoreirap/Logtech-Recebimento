package com.cadastro.logtech.integracaocad.Atividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cadastro.logtech.integracaocad.R;
import com.cadastro.logtech.integracaocad.integracaocadastro.BDHelper.UsuariosBd;

;

public class LoginActivity extends AppCompatActivity {

    /* Declara as variaveis de referencias dos componentes */
    private EditText edtUsuario;
    private EditText edtSenha;
    private Button btnRegistrar;
    private Button btnLogin;
    UsuariosBd ValidUsu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_login);

        /* Busca as referencias dos componentes que estão na view e as atribui nas variaveis declaradas */
        edtUsuario = (EditText) findViewById(R.id.edtUsuario);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);

        super.getSupportActionBar().setTitle("WMS Expert - [ Login ]");

        /* Atrela um método no evento de click do botão, implementando a interface OnClickListener e a passando a como parametro */
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidUsu = new UsuariosBd(LoginActivity.this);
                String usuario = edtUsuario.getText().toString();
                String senha = edtSenha.getText().toString();
                if(!usuario.equals("") && !senha.equals("")) {
                    if(!ValidUsu.validaLogin(edtUsuario.getText().toString(), edtSenha.getText().toString()))
                    {
                        Toast.makeText(getApplicationContext(), "Usuario ou senha incorretos!", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Não deixe nenhum campo em branco!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegistrarActivity.class));
            }
        });

    }

    @Override
    protected void onResume() {
        if(getIntent().getBooleanExtra("SAIR", false)){
            finish();
        }
        super.onResume();
    }

}
