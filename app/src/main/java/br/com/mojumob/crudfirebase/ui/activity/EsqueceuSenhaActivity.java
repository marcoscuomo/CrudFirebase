package br.com.mojumob.crudfirebase.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import br.com.mojumob.crudfirebase.R;
import br.com.mojumob.crudfirebase.firebase.Firebase;

public class EsqueceuSenhaActivity extends AppCompatActivity {

    //Atributos
    private EditText edtEmail;
    private Button btnEnviar;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueceu_senha);

        //Inicializações
        edtEmail  = findViewById(R.id.inicial_edtEmail);
        btnEnviar = findViewById(R.id.inicial_btnEnviar);

        //Firebase
        autenticacao = Firebase.getFirebaseAutenticacao();

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString();
                autenticacao.sendPasswordResetEmail(email);
                Toast.makeText(EsqueceuSenhaActivity.this, "Enviamos instruções para o seu email.", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }
}
