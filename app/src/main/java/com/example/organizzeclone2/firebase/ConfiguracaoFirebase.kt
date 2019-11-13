package com.example.organizzeclone2.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ConfiguracaoFirebase {



companion object{
    lateinit var autenticacao : FirebaseAuth
    lateinit var mRefenciaFirebase : DatabaseReference


}

    fun  getFirebaseAutenticacao() {
        if (autenticacao == null){
            autenticacao =   FirebaseAuth.getInstance()
        }
      //  return autenticacao
    }

    fun  getFirebaseDatabaseReference() : DatabaseReference {
        if (autenticacao == null){
            mRefenciaFirebase =  FirebaseDatabase.getInstance().getReference()
        }
        return mRefenciaFirebase
    }




}