package com.example.organizzeclone2.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.organizzeclone2.R
import com.example.organizzeclone2.helper.DataUtil
import com.example.organizzeclone2.model.Movimentacao
import com.example.organizzeclone2.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_despesa.*
import java.util.*

class ReceitaActivity : AppCompatActivity() {

    var movimentacao = Movimentacao()
    var autenticacao = FirebaseAuth.getInstance()
    var mFirebaseReference = FirebaseDatabase.getInstance().getReference()
    var receitaTotal = 0.0
    var receitaAtualizada = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receita)

        supportActionBar?.title = "Receita"

        txtData.setText(DataUtil.dataAtual())
        recuperarReceitaTotal()
    }

    fun cadastrarReceitas(view : View) {
        if (validaCampos()){
            var valor = txtValor.text.toString()
            var data = txtData.text.toString()
            movimentacao.categoria = txtCategoria.text.toString()
            movimentacao.descricao = txtDescricao.text.toString()
            movimentacao.valor = valor.toDouble()
            movimentacao.tipo = "r"
            movimentacao.data = data
            receitaAtualizada = receitaTotal + movimentacao.valor
            movimentacao.salvar(data)
            atualizarReceitaTotal()
            finish()
        }
    }


    fun validaCampos():Boolean{
        if (!txtValor.text.isEmpty()){
            if (!txtData.text.isEmpty()){
                if (!txtCategoria.text.isEmpty()){
                    if(!txtDescricao.text.isEmpty()){
                        return true

                    }else{
                        Toast.makeText(this,"Descrição nao foi preenchida", Toast.LENGTH_LONG).show()
                        return false
                    }

                }else{
                    Toast.makeText(this,"Categoria nao foi preenchida", Toast.LENGTH_LONG).show()
                    return false
                }

            }else{
                Toast.makeText(this,"Data não foi preenchida", Toast.LENGTH_LONG).show()
                return false
            }

        }else{
            Toast.makeText(this,"Valor não foi preenchido", Toast.LENGTH_LONG).show()
            return false
        }

    }

    fun recuperarReceitaTotal(){
        var usuarioPesquisa = mFirebaseReference
            .child("usuarios")
            .child(autenticacao.uid.toString())

        usuarioPesquisa.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(task: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(task: DataSnapshot) {
                var usuario  = task.getValue(Usuario :: class.java)

                receitaTotal = usuario?.receitaTotal!!
            }
        })

    }

    fun atualizarReceitaTotal(){
        mFirebaseReference
            .child("usuarios")
            .child(autenticacao.uid.toString())
            .child("receitaTotal")
            .setValue(receitaAtualizada)


    }

}
