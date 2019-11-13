package com.example.organizzeclone2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.organizzeclone2.R
import com.example.organizzeclone2.firebase.ConfiguracaoFirebase
import com.example.organizzeclone2.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var  autenticacao : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        click()
    }




    override fun onStart(){
        super.onStart()

       verificaUsuarioLogado()

    }

    fun click(){
        btCadastro.setOnClickListener(this)
        lblEntrar.setOnClickListener(this)
    }

    override fun onClick(v: View) {


        if (v.id == btCadastro.id){
            startActivity( Intent(this,
                CadastroActivity().javaClass)
            )
        }else if (v.id == lblEntrar.id){
            startActivity(
                Intent(this,
                    LoginActivity().javaClass)
            )
        }

 



    }



    fun abrirPrincipal(){
        startActivity(Intent(this, PrincipalActivity().javaClass))
        finish()
    }

    fun verificaUsuarioLogado(){

        autenticacao = FirebaseAuth.getInstance()
        //autenticacao.signOut()
        if (autenticacao.currentUser != null){
            abrirPrincipal()
        }

    }
}
