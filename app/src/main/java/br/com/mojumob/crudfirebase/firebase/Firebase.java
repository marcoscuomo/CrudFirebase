package br.com.mojumob.crudfirebase.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.mojumob.crudfirebase.helper.Base64Custom;

public class Firebase {

    public static FirebaseAuth autenticacao;
    public static DatabaseReference databaseReference;

    public static DatabaseReference getFirebaseDatabse(){

        if(databaseReference == null){
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }

    public static FirebaseAuth getFirebaseAutenticacao(){
        if(autenticacao == null){
             autenticacao = FirebaseAuth.getInstance();
        }

        return autenticacao;
    }

    public static String getIdentificadorUsuario(){

        FirebaseAuth usuario = getFirebaseAutenticacao();
        String email = usuario.getCurrentUser().getEmail();
        String identificadorUsuario = Base64Custom.codificarNaBase64(email);

        return identificadorUsuario;
    }

}
