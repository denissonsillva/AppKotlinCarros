package br.com.livroandroid.carros.domain

import android.content.Context
import android.util.Log
import br.com.livroandroid.carros.R
import org.json.JSONArray

object CarroService {
    private const val TAG = "livro"
    //Busca os carros por tipo
    fun getCarros(context: Context, tipo:TipoCarro): List<Carro> {
        //Este é o arquivo que deve ser lido
        val raw = getArquivoRaw(tipo)
        //Abre o arquivo para leitura
        val resources = context.resources
        val inputStream = resources.openRawResource(raw)
        inputStream.bufferedReader().use {
            //Lê o JSON e cria a lista de carros
            val json = it.readText()
            val carros = parserJson(json)
            return carros
        }
    }
    //Retorna o arquivo que deve ser lido para o tipo informado
    private fun getArquivoRaw(tipo: TipoCarro) = when(tipo) {
        TipoCarro.Classicos -> R.raw.carros_classicos
        TipoCarro.Esportivos -> R.raw.carros_esportivos
        else -> R.raw.carros_luxo
    }

    //Lê o json e cria a lista de carro
    private  fun parserJson(json: String): List<Carro> {
        val carros = mutableListOf<Carro>()
        //Cria um array com este JSON
        val array = JSONArray(json)
        //Percorre cada carro (JSON)
        for(i in 0 until array.length()) {
            //jason do carro
            val jsonCarro = array.getJSONObject(i)
            val c = Carro()
            //Lê as informações de cada carro
            c.nome = jsonCarro.optString("nome")
            c.desc = jsonCarro.optString("desc")
            c.urlFoto = jsonCarro.optString("url_foto")
            carros.add(c)
        }

    Log.d(TAG, "${carros.size} carros encontrados.")
    return carros
    }
}






