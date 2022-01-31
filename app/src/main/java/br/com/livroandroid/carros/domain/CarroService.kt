package br.com.livroandroid.carros.domain

import android.content.Context
import br.com.livroandroid.carros.R
import br.com.livroandroid.carros.extensions.fromJson

object CarroService {
    private const val TAG = "livro"

    //Busca os carros por tipo
    fun getCarros(context: Context, tipo: TipoCarro): List<Carro> {
        //Este é o arquivo que deve ser lido
        val raw = getArquivoRaw(tipo)
        //Abre o arquivo para leitura
        val resources = context.resources
        val inputStream = resources.openRawResource(raw)
        inputStream.bufferedReader().use {
            //Lê o JSON e cria a lista de carros
            val json = it.readText()
            //Convert o JSON para List<Carro>
            val carros = fromJson<List<Carro>>(json)
            return carros
        }
    }

    //Retorna o arquivo que deve ser lido para o tipo informado
    private fun getArquivoRaw(tipo: TipoCarro) = when (tipo) {
        TipoCarro.Classicos -> R.raw.carros_classicos
        TipoCarro.Esportivos -> R.raw.carros_esportivos
        else -> R.raw.carros_luxo
    }
}







