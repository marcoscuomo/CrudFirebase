package br.com.mojumob.crudfirebase.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import br.com.mojumob.crudfirebase.R;
import br.com.mojumob.crudfirebase.firebase.Firebase;
import br.com.mojumob.crudfirebase.helper.Base64Custom;
import br.com.mojumob.crudfirebase.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    //Atributos
    private EditText edtNome, edtEmail, edtSenha;
    private Button btnCadastrar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //Inicializações
        edtNome      = findViewById(R.id.cadastro_edtNome);
        edtEmail     = findViewById(R.id.cadastro_edtLogin);
        edtSenha     = findViewById(R.id.senha_edtEmail);
        btnCadastrar = findViewById(R.id.senha_btnEnviar);

        //Evento click Botao cadastrar
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Iniciando as variaveis para teste
                String nome = edtNome.getText().toString();
                String email = edtEmail.getText().toString();
                String senha = edtSenha.getText().toString();

                //Verificando se ha campos em branco
                if(!nome.isEmpty()){
                    if(!email.isEmpty()){
                        if(!senha.isEmpty()){

                            usuario = new Usuario(nome, email, senha);
                            cadastrarUsuario(usuario);

                        }else{
                            exibiMensagemCampoVazio("Senha");
                        }

                    }else{
                        exibiMensagemCampoVazio("E-mail");
                    }

                }else{
                    exibiMensagemCampoVazio("Nome");
                }

            }
        });//Fim do evento click botao cadadastrar
    }

    //Metodo para cadastrar o usuario após a checagem de que não há campo vazio
    private void cadastrarUsuario(final Usuario usuario) {

        autenticacao = Firebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            String idUsario = Base64Custom.codificarNaBase64(usuario.getEmail());
                            usuario.setIdUsuario(idUsario);
                            usuario.salvar();
                            finish();

                        }else{
                            //Tratamento de excesao ao criar uma conta
                            String excesao = "";
                            try{
                                throw task.getException();
                            }catch (FirebaseAuthWeakPasswordException e){
                                excesao = "Digite uma senha mais forte";
                            }catch (FirebaseAuthInvalidCredentialsException e){
                                excesao = "Digite um E-mail válido";
                            }catch (FirebaseAuthUserCollisionException e){
                                excesao = "E-mail já cadastrado";
                            }
                            catch (Exception e) {
                                excesao = "Erro ao cadastrar o usuário " + e.getMessage();
                                e.printStackTrace();
                            }

                            Toast.makeText(CadastroActivity.this,
                                    excesao, Toast.LENGTH_SHORT).show();
                        }

                    }
                });//Fim do onCompleteListenet


    }//Fim do metodo cadastrarUsuario


    //Metodo para exibir um toast em caso de campo vazio
    private void exibiMensagemCampoVazio(String campo) {
        Toast.makeText(this, "Por favor, preencha o campo " + campo +
                " para concluir o cadastro", Toast.LENGTH_LONG).show();
    }
}
