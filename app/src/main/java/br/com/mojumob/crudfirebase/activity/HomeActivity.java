package br.com.mojumob.crudfirebase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import br.com.mojumob.crudfirebase.R;
import br.com.mojumob.crudfirebase.firebase.Firebase;


public class HomeActivity extends AppCompatActivity {

    //Atributos
    private FirebaseAuth autenticacao;
    private MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inicializações
        Toolbar toolbar          = findViewById(R.id.toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        //searchView               = findViewById(R.id.pesquisarPrincipal);

        setContentView(R.layout.activity_home);

        toolbar.setTitle("Lista de contatos");
        setSupportActionBar(toolbar);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
}
