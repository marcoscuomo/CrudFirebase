package br.com.mojumob.crudfirebase.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Firebase {

    private static FirebaseAuth autenticacao;
    private static DatabaseReference databaseReference;

    private static DatabaseReference getFirebaseDatabse(){

        if(databaseReference == null){
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }

    private static FirebaseAuth getFirebaseAutenticacao(){
        if(autenticacao == null){
             autenticacao = FirebaseAuth.getInstance();
        }

        return autenticacao;
    }

}
