package com.example.atividade12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.bloco_cotacao.*
import kotlinx.android.synthetic.main.bloco_entrada.*
import kotlinx.android.synthetic.main.bloco_saida.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.alert
import org.jetbrains.anko.uiThread
import org.json.JSONObject

import java.text.NumberFormat
import java.net.URL
import java.util.*

class ConvertActivity : AppCompatActivity() {
    var cotacaoBitcoin: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_convert)
        buscarCotacao()

        //listener do botão calcular
        btnCalcular.setOnClickListener {
            calcular()
        }
    }

    fun buscarCotacao() {
        doAsync {
            // Acessar a API e buscar seu resultado
            val resposta = URL(API_URL).readText()
            cotacaoBitcoin = JSONObject(resposta).getJSONObject("ticker").getDouble("last")
            // Formatação da moeda
            val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
            val cotacaoFormatada = f.format(cotacaoBitcoin)


            uiThread {
                // alert(resposta).show()
                // alert("$cotacaoBitcoin").show()
                txtCotacao.setText("$cotacaoFormatada")
            }


        }

    }

    fun calcular() {
        if (txtValor.text.isEmpty()) {
            txtValor.error = "Preencha um valor"
            return
        }
        // Valor digitado pelo usuário
        val valorDigitado = txtValor.text.toString()
            .replace(",", ".").toDouble()
        // Caso não venha valor da API
        val resultado = if (cotacaoBitcoin > 0) valorDigitado / cotacaoBitcoin
        else 0.0
        // Atualizando o TextView com o resultado formatado com 8 casas decimais
        txtQtdBitcoins.text = "%.8f".format(resultado)
    }
}