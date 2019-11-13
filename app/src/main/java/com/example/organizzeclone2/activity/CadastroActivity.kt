package com.example.organizzeclone2.activity

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.solver.widgets.ConstraintWidgetGroup
import com.example.organizzeclone2.R
import com.example.organizzeclone2.firebase.ConfiguracaoFirebase
import com.example.organizzeclone2.helper.Base64Custom
import com.example.organizzeclone2.model.Usuario
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_cadastro.*
import java.lang.Exception

class CadastroActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var autenticacao : FirebaseAuth
    lateinit var nome : String
    lateinit var email : String
    lateinit var senha : String
    lateinit var usuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)



        click()
    }

    fun click(){
        btCadastrar.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == btCadastrar.id){

            if (validaCampos()){
                nome = txtNome.text.toString()
                email = txtEmail.text.toString()
                senha = txtSenha.text.toString()

                usuario = Usuario()
                usuario.nome = nome
                usuario.email = email
                usuario.senha = senha



            cadastrarUsuario()

            }
        }

    }


    fun validaCampos():Boolean{

            if (!txtNome.text.isEmpty()){
                if (!txtEmail.text.isEmpty()){
                    if (!txtSenha.text.isEmpty()){
                        return true

                    }else{
                        Toast.makeText(this,"Preencha a senha",Toast.LENGTH_LONG).show()
                        return false
                    }

                }else{
                    Toast.makeText(this,"Preencha o Email",Toast.LENGTH_LONG).show()
                    return false
                }

            }else{
                Toast.makeText(this,"Preencha o nome",Toast.LENGTH_LONG).show()
                return false
            }

    }

    fun cadastrarUsuario(){

         autenticacao = FirebaseAuth.getInstance()
        try{

            autenticacao.createUserWithEmailAndPassword(usuario.email,usuario.senha)
                .addOnCompleteListener(this, object : OnCompleteListener<AuthResult> {
                    override fun onComplete(task: Task<AuthResult>) {

                        if (task.isSuccessful){



                            var idUsuario = autenticacao.uid
                            //Base64Custom.codificarBase64(usuario.email)
                            usuario.idUsuario = idUsuario.toString()
                            Toast.makeText(applicationContext,"Sucesso ao cadastrar", Toast.LENGTH_LONG).show()
                            usuario.salvar()

                            finish()
                        }else{
                            var excessao = ""
                            try{
                                throw task.exception!!
                            }catch (e : FirebaseAuthWeakPasswordException){
                                excessao = "Informe uma senha mais forte"
                            }catch (e : FirebaseAuthInvalidCredentialsException){
                                excessao = "Informe um email valido"
                            }catch (e : FirebaseAuthUserCollisionException){
                                excessao = "Email já cadastrado"
                            }catch (e : Exception){
                                excessao = "Erro ao cadastrar Usuario\n"+ e.message
                                e.printStackTrace()
                            }

                            Toast.makeText(applicationContext,excessao,Toast.LENGTH_LONG).show()
                        }

                      }
                })
        }catch (e : Exception){
            Toast.makeText(applicationContext,e.message,Toast.LENGTH_LONG).show()
        }
    }
}
