package br.com.mojumob.crudfirebase.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import br.com.mojumob.crudfirebase.firebase.Firebase;

public class Usuario {

    private String nome;
    private String email;
    private String senha;
    private String idUsuario;

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Usuario() {
    }

    @Exclude
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
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

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void salvar() {

        DatabaseReference firebase = Firebase.getFirebaseDatabse();
        firebase.child("usuarios")
                .child(this.idUsuario)
                .setValue(this);

    }
}
