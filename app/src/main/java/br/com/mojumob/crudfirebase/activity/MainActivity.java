package br.com.mojumob.crudfirebase.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.mojumob.crudfirebase.R;

public class MainActivity extends AppCompatActivity {

    //Atributos
    private EditText edtLogin;
    private EditText edtSenha;
    private Button btnLogin;
    private TextView txtCriarConta;
    private TextView txtEsqueceuSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializações
        edtLogin = findViewById(R.id.edtLogin);
    }
}
