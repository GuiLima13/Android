package com.example.organizzeclone2.helper

import android.util.Base64

object Base64Custom {
    fun codificarBase64(texto: String): String {
        return Base64.encodeToString(texto.toByteArray(), Base64.DEFAULT).replace("(\\n|\\r)","")
    }

    fun decodificarBase64(texto: String): String {
        return String(Base64.decode(texto.toByteArray(), Base64.DEFAULT))
    }
}
