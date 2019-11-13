package com.example.organizzeclone2.model

import android.content.Context
import android.util.Log
import com.example.organizzeclone2.firebase.ConfiguracaoFirebase
import com.example.organizzeclone2.helper.DataUtil
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception

class Movimentacao {
    lateinit var data : String
    lateinit var categoria : String
    lateinit var descricao : String
     var valor  = 0.00
    lateinit var tipo :String
    lateinit var idUsuario :String


    fun salvar(dataEscolhida : String)  {

        var mReferenciaFirebase = FirebaseDatabase.getInstance().getReference()
        var autenticacao = FirebaseAuth.getInstance()
        this.idUsuario = autenticacao.uid.toString()
        var mesAno = DataUtil.retornoData(dataEscolhida)
        mReferenciaFirebase.child("movimentacao")
            .child(this.idUsuario)
            .child(mesAno)
            .push()
            .setValue(this)



    }

}