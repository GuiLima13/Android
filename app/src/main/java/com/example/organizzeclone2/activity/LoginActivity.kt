package com.example.organizzeclone2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.organizzeclone2.R
import com.example.organizzeclone2.firebase.ConfiguracaoFirebase
import com.example.organizzeclone2.model.Usuario
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var  autenticacao : FirebaseAuth
    lateinit var nome : String
    lateinit var email : String
    lateinit var senha : String
    lateinit var usuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        click()
    }



    fun click(){
        btEntrar.setOnClickListener(this)
    }

    fun validaCampos():Boolean{


            if (!txtEmailLog.text.isEmpty()){
                if (!txtSenhaLog.text.isEmpty()){
                    return true

                }else{
                    Toast.makeText(this,"Preencha a senha", Toast.LENGTH_LONG).show()
                    return false
                }

            }else{
                Toast.makeText(this,"Preencha o Email", Toast.LENGTH_LONG).show()
                return false
            }
    }
    override fun onClick(v: View) {
            if (v.id == btEntrar.id){
               if (validaCampos()){
                   email = txtEmailLog.text.toString()
                   senha = txtSenhaLog.text.toString()

                   usuario = Usuario()

                   usuario.email = email
                   usuario.senha = senha

                   validaLogin()



               }
            }
        }

    fun validaLogin(){

        autenticacao = FirebaseAuth.getInstance()
        autenticacao.signInWithEmailAndPassword(
            usuario.email, usuario.senha
        ).addOnCompleteListener(this, object : OnCompleteListener<AuthResult>{
            override fun onComplete(task : Task<AuthResult>) {
                if (task.isSuccessful){
                   // Toast.makeText(applicationContext,"Sucesso ao realizar login",Toast.LENGTH_LONG).show()
                    abrirPrincipal()
                }else{

                    var excessao = ""
                    try{
                        throw task.exception!!
                    }catch (e : FirebaseAuthInvalidUserException){
                        excessao = "Usuario nao esta cadastrado"
                    }catch (e : FirebaseAuthInvalidCredentialsException){
                        excessao = "Email e/ou Senha não correspondem ao usuario cadastrado "
                    }catch (e : Exception){
                        excessao = "Erro ao realizar login\n"+e.message
                    }

                    Toast.makeText(applicationContext,excessao,Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    fun abrirPrincipal(){
        startActivity(Intent(this, PrincipalActivity().javaClass))
        finish()


    }



}
