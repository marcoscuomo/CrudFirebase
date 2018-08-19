package br.com.mojumob.crudfirebase.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.mojumob.crudfirebase.R;

public class CadastroActivity extends AppCompatActivity {

    //Atributos
    private EditText edtNome, edtEmail, edtSenha;
    private Button btnCadastrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //Inicializações
        edtNome      = findViewById(R.id.cadastro_edtNome);
        edtEmail     = findViewById(R.id.cadastro_edtLogin);
        edtSenha     = findViewById(R.id.cadastro_edtSenha);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        //Evento click Botao cadastrar
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Iniciando as variaveis para teste


            }
        });//Fim do evento click botao cadadastrar
    }
}
