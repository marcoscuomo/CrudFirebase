package br.com.mojumob.crudfirebase.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import br.com.mojumob.crudfirebase.R;

public class MainActivity extends AppCompatActivity {

    //Atributos
    private EditText edtLogin;
    private EditText edtSenha;
    private Button btnLogin;
    private TextView txtCriarConta;
    private TextView txtEsqueceuSenha;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializações
        edtLogin         = findViewById(R.id.edtLogin);
        edtSenha         = findViewById(R.id.edtSenha);
        btnLogin         = findViewById(R.id.btnLogin);
        txtCriarConta    = findViewById(R.id.txtCriarConta);
        txtEsqueceuSenha = findViewById(R.id.txtEsqueceuSenha);

        //Ação de clique no botao login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Guardando login e senha para verificacao
                String email = edtLogin.getText().toString();
                String senha = edtSenha.getText().toString();

                if(!email.isEmpty()){

                }else{
                    Toast.makeText(MainActivity.this, "Digite seu login", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
