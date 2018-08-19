package br.com.mojumob.crudfirebase.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

}
