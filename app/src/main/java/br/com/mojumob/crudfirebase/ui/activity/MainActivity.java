package br.com.mojumob.crudfirebase.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import br.com.mojumob.crudfirebase.R;
import br.com.mojumob.crudfirebase.firebase.Firebase;
import br.com.mojumob.crudfirebase.helper.Common;
import br.com.mojumob.crudfirebase.model.Usuario;

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
        inicializaComponentes();


        //Ação no clique para criar nova senha
        txtEsqueceuSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EsqueceuSenhaActivity.class));
            }
        });

        //Ação de clique no botao login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Guardando login e senha para verificacao
                String email = edtLogin.getText().toString();
                String senha = edtSenha.getText().toString();

                if(!email.isEmpty()){
                    if(!senha.isEmpty()){

                        Usuario usuario = new Usuario();
                        usuario.setEmail(email);
                        usuario.setSenha(senha);

                        validarSenha(usuario);

                    }else{
                        Toast.makeText(MainActivity.this, Common.SENHA_VAZIO,
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, Common.EMAIL_VAZIO,
                            Toast.LENGTH_SHORT).show();
                }

            }
        });//Fim do botao Logar

        //Criar conta
        txtCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CadastroActivity.class));
            }
        });

    }

    private void inicializaComponentes() {
        //Inicializações
        edtLogin         = findViewById(R.id.inicial_edtLogin);
        edtSenha         = findViewById(R.id.inicial_edtEmail);
        btnLogin         = findViewById(R.id.inicial_btnEnviar);
        txtCriarConta    = findViewById(R.id.inicial_txtCriarConta);
        txtEsqueceuSenha = findViewById(R.id.inicial_txtEsqueceuSenha);
    }

    private void validarSenha(Usuario usuario) {

        autenticacao = Firebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            abrirHome();
                        }else{
                            //Tratamento de excessoes ao se logar
                            String excessao = "";
                            try{
                                throw task.getException();
                            }catch(FirebaseAuthInvalidUserException e){
                                excessao = "Usuario não cadastrado";
                            }catch (FirebaseAuthInvalidCredentialsException e){
                                excessao = "E-mail e Senha não correspondem";
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(MainActivity.this, excessao, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void abrirHome(){
        startActivity(new Intent(MainActivity.this, HomeActivity.class));
        finish();
    }

    private void verificaUsuarioLogado(){
        autenticacao = Firebase.getFirebaseAutenticacao();
        if(autenticacao.getCurrentUser() != null){
            abrirHome();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        verificaUsuarioLogado();
    }
}
