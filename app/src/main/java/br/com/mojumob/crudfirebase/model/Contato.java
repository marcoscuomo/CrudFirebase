package br.com.mojumob.crudfirebase.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import br.com.mojumob.crudfirebase.firebase.Firebase;

public class Contato {

    String nome, email, telfone, idContato;

    public Contato(String nome, String email, String telfone) {
        this.nome = nome;
        this.email = email;
        this.telfone = telfone;
    }

    public Contato() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelfone() {
        return telfone;
    }

    public void setTelfone(String telfone) {
        this.telfone = telfone;
    }


    public String getIdContato() {
        return idContato;
    }

    public void setIdContato(String idContato) {
        this.idContato = idContato;
    }

    public void salvar() {

        DatabaseReference database = Firebase.getFirebaseDatabse();
        database.child("contatos")
                .child(Firebase.getIdentificadorUsuario())
                .child(this.idContato)
                .setValue(this);

    }

    public void atualizar(String idContatoSelecionado) {

        DatabaseReference database = Firebase.getFirebaseDatabse();
        database.child("contatos")
                .child(Firebase.getIdentificadorUsuario())
                .child(idContatoSelecionado)
                .setValue(this);

    }

    public void deletar(String idContatoSelecionado) {

        DatabaseReference database = Firebase.getFirebaseDatabse();
        database.child("contatos")
                .child(Firebase.getIdentificadorUsuario())
                .child(idContatoSelecionado)
                .removeValue();

    }
}
