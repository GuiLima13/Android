package com.example.organizzeclone2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.graphics.red
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.organizzeclone2.R
import com.example.organizzeclone2.model.Movimentacao
import com.example.organizzeclone2.model.Usuario
import com.example.playerlist.adapter.AdapterListMusica
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener
import kotlinx.android.synthetic.main.activity_principal.*
import kotlinx.android.synthetic.main.fragment_principal.*
import java.text.DecimalFormat

class PrincipalActivity : AppCompatActivity(), View.OnClickListener {


    var autenticacao = FirebaseAuth.getInstance()
    var mFirebaseReference = FirebaseDatabase.getInstance().getReference()

    lateinit var usuarioPesquisa : DatabaseReference
    lateinit var valueEventListenerUsuario: ValueEventListener

    lateinit var mFirebaseReferenceMovimentacao : DatabaseReference
    lateinit var valueEventListenerMovimentacao: ValueEventListener
    var listMovimentacao = ArrayList<Movimentacao>()

    var despesaTotal = 0.0
    var receitaTotal = 0.0
    var total = 0.0
    lateinit var mesSelecionado : String

    lateinit var adapterMovimentacao : AdapterListMusica
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        click()

        supportActionBar?.title = null
        supportActionBar?.elevation = 0F

        configuraCalendarView()


        //Configurando RecyclerView
        var mLayoutManager = LinearLayoutManager(this)
        rvMovimento.layoutManager = mLayoutManager

        //Configurando adapter
        adapterMovimentacao = AdapterListMusica(listMovimentacao, this)
        rvMovimento.adapter = adapterMovimentacao


    }

    override fun onStart() {
        super.onStart()
        buscarUsuario()
        recuperarMovimentacoes()

    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.itemSair) {
            autenticacao.signOut()
            startActivity(Intent(this, MainActivity().javaClass))
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun click() {
        menu_despesa.setOnClickListener(this)
        menu_receita.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == menu_despesa.id) {
            startActivity(Intent(this, DespesaActivity().javaClass))
        } else if (v.id == menu_receita.id) {


            startActivity(Intent(this, ReceitaActivity().javaClass))
        }
    }

    fun buscarUsuario() {
        usuarioPesquisa = mFirebaseReference
            .child("usuarios")
            .child(autenticacao.uid.toString())

        valueEventListenerUsuario = usuarioPesquisa.addValueEventListener( object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(task: DataSnapshot) {
                var usuario  = task.getValue(Usuario :: class.java)

                 despesaTotal = usuario?.despesaTotal!!
                 receitaTotal = usuario?.receitaTotal
                 total = (receitaTotal?.minus(despesaTotal!!))

                if(total < 0){
                    lblValor.setTextColor(android.graphics.Color.RED)
                }else{
                    lblValor.setTextColor(android.graphics.Color.WHITE)
                }

                var decimalFormat = DecimalFormat("##,##0.##")
                var totalFormatado = decimalFormat.format(total)
                totalFormatado.replace(".",",").replace(",",".")
                lblSaudacao.setText("Ola, "+usuario?.nome)
                lblValor.setText("R$ $totalFormatado")

            }

        })
    }

    fun recuperarMovimentacoes(){
        mFirebaseReferenceMovimentacao = mFirebaseReference
        mFirebaseReferenceMovimentacao.child("movimentacao")
            .child(autenticacao.uid.toString())
            .child(mesSelecionado)

        valueEventListenerMovimentacao = mFirebaseReferenceMovimentacao.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot : DataSnapshot) {
                listMovimentacao.clear()
/*
                var movimentacao = Movimentacao()
                movimentacao.valor = 100.00
                movimentacao.idUsuario = " dfkjadkfj"
                movimentacao.tipo = "d"
                movimentacao.descricao = "Ola"
                movimentacao.data = "10/10/10"
                movimentacao.categoria = "Sie"
                listMovimentacao.add(movimentacao)

 */

                for (dados  in dataSnapshot.child("movimentacao").child(autenticacao.uid.toString()).child(mesSelecionado).children){
                    var movimentacao = dados.getValue(Movimentacao :: class.java)
                   listMovimentacao.add(movimentacao!!)
                    Log.i("MOVIMENTACAO",dados.toString())
                }

                 /*

                dataSnapshot.child("movimentacao").child(autenticacao.uid.toString()).child(mesSelecionado).children.forEach {
                    var movimentacao = it.getValue(Movimentacao :: class.java)
                    listMovimentacao.add(movimentacao!!)
                }

                 */

                adapterMovimentacao.notifyDataSetChanged()
            }
        })
    }

    fun configuraCalendarView(){
        //Exibindo Meses em portugues
        var meses = "Janeiro/Fevereiro/Março/Abril/Maio/Junho/Julho/Agosto/" +
                "Setembro/Outubro/Novembro/Dezembro"
        var mesesEditado = meses.split("/")

        calendarView.setTitleMonths(mesesEditado.toTypedArray())

        var dataAtual = calendarView.currentDate
        var mesSelecionadoFormatado = String.format("%02d",dataAtual.month?.plus(1))
        mesSelecionado = mesSelecionadoFormatado + dataAtual.year.toString()

        calendarView.setOnMonthChangedListener(object :
            OnMonthChangedListener {
            override fun onMonthChanged(widget: MaterialCalendarView?, date: CalendarDay?) {
                var mesSelecionadoFormatado = String.format("%02d",date?.month?.plus(1))
                mesSelecionado = mesSelecionadoFormatado+date?.year.toString()
                Toast.makeText(applicationContext,mesSelecionado,Toast.LENGTH_LONG).show()
                mFirebaseReferenceMovimentacao.removeEventListener(valueEventListenerMovimentacao)
                recuperarMovimentacoes()
            }


        })

    }

    override fun onStop() {
        super.onStop()
        usuarioPesquisa.removeEventListener(valueEventListenerUsuario)
        mFirebaseReferenceMovimentacao.removeEventListener(valueEventListenerMovimentacao)
    }

}
