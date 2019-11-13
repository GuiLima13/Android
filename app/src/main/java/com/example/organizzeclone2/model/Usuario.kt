package com.example.organizzeclone2.model

import android.util.Log
import android.widget.Toast
import com.example.organizzeclone2.firebase.ConfiguracaoFirebase
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.Exclude
import com.google.firebase.database.FirebaseDatabase
import kotlin.math.log

class Usuario {



    lateinit var idUsuario: String
    lateinit var nome: String
    lateinit var email: String
    lateinit var senha: String
    var despesaTotal = 0.00
    var receitaTotal = 0.00

    fun salvar() {


        var mReferenciaFirebase = FirebaseDatabase.getInstance().getReference()

        mReferenciaFirebase.child("usuarios").child(idUsuario).child("email").setValue(email)
        mReferenciaFirebase.child("usuarios").child(idUsuario).child("nome").setValue(nome)
        mReferenciaFirebase.child("usuarios").child(idUsuario).child("despesaTotal").setValue(despesaTotal)
        mReferenciaFirebase.child("usuarios").child(idUsuario).child("receitaTotal").setValue(receitaTotal)

    }


}