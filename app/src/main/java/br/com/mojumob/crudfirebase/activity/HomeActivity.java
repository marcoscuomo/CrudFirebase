package br.com.mojumob.crudfirebase.activity;

import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
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
import br.com.mojumob.crudfirebase.helper.RecyclerItemClickListener;
import br.com.mojumob.crudfirebase.model.Contato;


public class HomeActivity extends AppCompatActivity {

    //Atributos
    private FirebaseAuth autenticacao;
    private MaterialSearchView searchView;
    private RecyclerView recyclerContatos;
    private List<Contato>listaContatos = new ArrayList<>();
    private AdapterContato adapter;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;

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

        //Listner do searchView

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Iniciando o Alterdialog
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(HomeActivity.this);
                final View mView = getLayoutInflater().inflate(R.layout.dialog_cadastrar, null);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();

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
                                contato.setEmail(edtEmail.getText().toString());
                                contato.setTelfone(edtTelefone.getText().toString());

                                String idContato = String.valueOf(UUID.randomUUID());
                                contato.setIdContato(idContato);
                                contato.salvar();
                                adapter.notifyDataSetChanged();
                                dialog.hide();


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

        //Clique no recyclerView
        recyclerContatos.addOnItemTouchListener(new RecyclerItemClickListener(
                HomeActivity.this, recyclerContatos,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        final Contato contatoSelecionado = listaContatos.get(position);

                        //Iniciando o Alterdialog
                        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(HomeActivity.this);
                        final View mView = getLayoutInflater().inflate(R.layout.dialog_cadastrar, null);

                        mBuilder.setView(mView);
                        final AlertDialog dialog = mBuilder.create();


                        //Iniciando os elementos do xml
                        final EditText edtNome           = mView.findViewById(R.id.dialog_edtNome);
                        final EditText edtEmail          = mView.findViewById(R.id.dialog_edtEmail);
                        final EditText edtTelefone       = mView.findViewById(R.id.dialog_edtTelefone);
                        Button btnCadastrarContato = mView.findViewById(R.id.dialog_btnCadastrar);
                        btnCadastrarContato.setText("Atualizar");

                        //Carregando os valores ja cadastrados
                        edtNome.setText(contatoSelecionado.getNome());
                        edtEmail.setText(contatoSelecionado.getEmail());
                        edtTelefone.setText(contatoSelecionado.getTelfone());


                        //Tratando a açao de clique
                        btnCadastrarContato.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if(!edtNome.getText().toString().isEmpty()){
                                    if((!edtEmail.getText().toString().isEmpty()) ||
                                            (!edtTelefone.getText().toString().isEmpty())){

                                        Contato contato = new Contato();
                                        contato.setNome(edtNome.getText().toString());
                                        contato.setEmail(edtEmail.getText().toString());
                                        contato.setTelfone(edtTelefone.getText().toString());
                                        contato.setIdContato(contatoSelecionado.getIdContato());
                                        contato.atualizar(contatoSelecionado.getIdContato());
                                        adapter.notifyDataSetChanged();
                                        dialog.hide();

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
                        dialog.show();


                    }

                    @Override
                    public void onLongItemClick(View view, final int position) {

                        final Contato contatoSelecionado = listaContatos.get(position);

                        AlertDialog.Builder dialog = new AlertDialog.Builder(HomeActivity.this);

                        dialog.setTitle("Exclusão");
                        dialog.setMessage("Confirma a exclusão do contato? ");
                        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                contatoSelecionado.deletar(contatoSelecionado.getIdContato());
                            }
                        });
                        dialog.setNegativeButton("Não", null);

                        dialog.create();
                        dialog.show();
                        adapter.notifyItemRemoved(position);

                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }
        ));

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


        valueEventListener = databaseReference.orderByChild("nome").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaContatos.clear();
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

    @Override
    protected void onStop() {
        super.onStop();
        databaseReference.removeEventListener(valueEventListener);
    }
}
