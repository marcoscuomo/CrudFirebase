package br.com.mojumob.crudfirebase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.mojumob.crudfirebase.R;
import br.com.mojumob.crudfirebase.adapter.AdapterContato;
import br.com.mojumob.crudfirebase.firebase.Firebase;
import br.com.mojumob.crudfirebase.model.Contato;


public class HomeActivity extends AppCompatActivity {

    //Atributos
    private FirebaseAuth autenticacao;
    private MaterialSearchView searchView;
    private RecyclerView recyclerContatos;
    private List<Contato>listaContatos = new ArrayList<>();
    private AdapterContato adapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Inicializações
        Toolbar toolbar          = findViewById(R.id.toolbarMain);
        FloatingActionButton fab = findViewById(R.id.fab);
        recyclerContatos         = findViewById(R.id.recyclerContatos);
        searchView               = findViewById(R.id.persquisarPrincipal);

        toolbar.setTitle("Lista de contatos");
        setSupportActionBar(toolbar);

        /*/Criando conteudo teste
        Contato contatoTeste = new Contato("Marcos", "bytecore@uol.com", "11988766676");
        listaContatos.add(contatoTeste);

        Contato contatoTeste2 = new Contato();
        contatoTeste2.setNome("Jose");
        contatoTeste2.setEmail("ze@ig.com");
        listaContatos.add(contatoTeste2);

        Contato contatoTeste3 = new Contato();
        contatoTeste3.setNome("Maria");
        contatoTeste3.setTelfone("11987889987");
        listaContatos.add(contatoTeste3);*/



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Iniciando o Alterdialog
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(HomeActivity.this);
                final View mView = getLayoutInflater().inflate(R.layout.dialog_cadastrar, null);

                //Iniciando os elementos do xml
                final EditText edtNome           = mView.findViewById(R.id.dialog_edtNome);
                final EditText edtEmail          = mView.findViewById(R.id.dialog_edtEmail);
                final EditText edtTelefone       = mView.findViewById(R.id.dialog_edtTelefone);
                Button btnCadastrarContato = mView.findViewById(R.id.dialog_btnCadastrar);

                //Tratando a açao de clique
                btnCadastrarContato.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(!edtNome.getText().toString().isEmpty()){
                            if((!edtEmail.getText().toString().isEmpty()) ||
                                    (!edtTelefone.getText().toString().isEmpty())){

                                Contato contato = new Contato();
                                contato.setNome(edtNome.getText().toString());
                                if(!edtEmail.getText().toString().isEmpty()){
                                    contato.setEmail(edtEmail.getText().toString());
                                }

                                if(!edtTelefone.getText().toString().isEmpty()){
                                    contato.setTelfone(edtTelefone.getText().toString());
                                }

                                String idContato = String.valueOf(UUID.randomUUID());
                                contato.setIdContato(idContato);
                                contato.salvar();

                                //Limpando as caixas de texto para um novo cadastro
                                edtNome.setText("");
                                edtEmail.setText("");
                                edtTelefone.setText("");

                            }else{
                                Toast.makeText(HomeActivity.this, R.string.pelo_menos_um_dado_cadastral,
                                        Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(HomeActivity.this, R.string.preencha_o_campo_nome
                                    , Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                mBuilder.setPositiveButton("Sair", null);
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

            }
        });

        /*Configurando RecyclerView*/
        //Adapter
        adapter = new AdapterContato(HomeActivity.this, listaContatos);

        //Configurações gerais
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
        recyclerContatos.setLayoutManager(layoutManager);
        recyclerContatos.setHasFixedSize(true);
        recyclerContatos.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        //Configurando o botaoo de pesquisa
        MenuItem item = menu.findItem(R.id.menu_pesquisar);
        searchView.setMenuItem(item);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_sair:
                autenticacao = Firebase.getFirebaseAutenticacao();
                autenticacao.signOut();
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                finish();
                break;
        }


        return super.onOptionsItemSelected(item);
    }


    private void recuperaContatos(){

        String idUsuario = Firebase.getIdentificadorUsuario();
        databaseReference = Firebase.getFirebaseDatabse().child("contatos").child(idUsuario);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dados : dataSnapshot.getChildren()){
                    Contato contato = dados.getValue(Contato.class);
                    listaContatos.add(contato);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperaContatos();
    }
}
