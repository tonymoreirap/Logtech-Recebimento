package com.cadastro.logtech.integracaocad.Atividades;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.cadastro.logtech.integracaocad.R;

public class FormularioDigitaCodbarra extends AppCompatActivity {
    Button zxing_button;
    Button BTN_Continuar;
    Button BTN_habteclado;
    EditText TXT_CodBarr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("LogTech - [ Digitar Cod.Barra ]");
        setContentView(R.layout.activity_codbarrdigita);

        zxing_button = (Button) findViewById(R.id.zxing_button);
        BTN_Continuar = (Button) findViewById(R.id.BTN_Continuar);
        BTN_habteclado = (Button) findViewById(R.id.BTN_habteclado);
        TXT_CodBarr = (EditText) findViewById(R.id.TXT_CodBarr);

    }

}
