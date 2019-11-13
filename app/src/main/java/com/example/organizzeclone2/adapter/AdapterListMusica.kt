package com.example.playerlist.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.organizzeclone2.R
import com.example.organizzeclone2.model.Movimentacao
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_principal.view.*
import kotlinx.android.synthetic.main.fragment_principal.view.lblValor
import kotlinx.android.synthetic.main.layout_list_movimentacoes.view.*

class AdapterListMusica(var listMovimentacao: List<Movimentacao>, var context : Context) : RecyclerView.Adapter<AdapterListMusica.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //Agora nos pegamos o layout e trazemos para a aplicação
        var movimentacao = LayoutInflater.from(context).inflate(R.layout.layout_list_movimentacoes,parent,false)

        //Aqui nos damos o retorno do tipo MyViewHolder, que o metodo pede, passando o lyaout como parametro
        return MyViewHolder(movimentacao)
       }

    override fun getItemCount(): Int {
        return listMovimentacao.size
      }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var movimentacao = listMovimentacao[position]
        Log.i("POSICAO",position.toString())
        holder.categoria.text =  movimentacao.categoria
        holder.descricao.text = movimentacao.descricao
        holder.valor.text = movimentacao.valor.toString()

        if (movimentacao.tipo.equals("d")){
            holder.valor.setTextColor(android.graphics.Color.RED)
            holder.valor.text = ("-"+movimentacao.valor.toString())
        }


       }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var categoria = itemView.lblCategoria
        var descricao = itemView.lblDescricao
        var valor = itemView.lblValorList
    }
}