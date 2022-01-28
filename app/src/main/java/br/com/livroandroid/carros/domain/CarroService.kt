package br.com.livroandroid.carros.domain

import android.content.Context

object CarroService {
    //Busca os carros por tipo
    fun getCarros(context: Context?, tipo:TipoCarro): List<Carro> {
        val tipoString = context?.getString(tipo.string)
        //cria um array vazio de carros
        val carros = mutableListOf<Carro>()
        //cria 20 carros
        for (i in 1..20){
            val c = Carro()
            //Nome do carro dinâmico para brincar
            c.nome = "Carro $tipoString: $i"
            c.desc = "Desc $i"
            //URL da foto fixa por enquanto
            c.urlFoto = "https://www.imagensempng.com.br/wp-content/uploads/2020/12/006-1.png"
            carros.add(c)
        }
        return carros
    }
}
