package com.example.organizzeclone2.helper

import java.text.DateFormat
import java.text.SimpleDateFormat

object DataUtil {

    fun dataAtual(): String{
        var date =  System.currentTimeMillis()
        var simpleDate = SimpleDateFormat("dd/MM/yyyy")
        var dateString = simpleDate.format(date)

        return dateString
    }

    fun retornoData(data : String):String{
        var dataEscolhida =   data.split("/")

        var dia = dataEscolhida[0]
        var mes = dataEscolhida[1]
        var ano = dataEscolhida[2]
        var mesAno = mes+ano

        return mesAno
    }

}