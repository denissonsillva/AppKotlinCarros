package br.com.livroandroid.carros.domain

import br.com.livroandroid.carros.extensions.fromJson
import br.com.livroandroid.carros.extensions.toJson
import br.com.livroandroid.carros.utils.HttpHelper

object CarroService {
    private const val BASE_URL = "http://livrowebservices.com.br/rest/carros"

    //Busca os carros por tipo
    fun getCarros(tipo: TipoCarro): List<Carro> {
        //Cria a url para o tipo informado
        val url = "$BASE_URL/tipo/${tipo.name}"
        //Faz a requisição GET no webservice
        val json = HttpHelper.get(url)
        //Lê o JSON e cria a lista de carros
        val carros = fromJson<List<Carro>>(json)
        return carros
    }

    // Salva um carro
    fun save(carro: Carro): Response {
        // Faz POST do JSON carro
        val json = HttpHelper.post(BASE_URL, carro.toJson())
        val response = fromJson<Response>(json)
        return response
    }
}







