package br.com.livroandroid.carros.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.livroandroid.carros.R
import br.com.livroandroid.carros.activity.CarroActivity
import br.com.livroandroid.carros.adapter.CarroAdapter
import br.com.livroandroid.carros.domain.Carro
import br.com.livroandroid.carros.domain.CarroService
import br.com.livroandroid.carros.domain.TipoCarro
import kotlinx.android.synthetic.main.fragment_carros.*
import kotlinx.android.synthetic.main.include_progress.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread

@Suppress("DEPRECATION")
open class CarrosFragment : BaseFragment() {
    private var tipo: TipoCarro = TipoCarro.Classicos
    private var carros = listOf<Carro>()

    //Cria a View do Fragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_carros, container, false)
        //Lê o tipo dos argumentos
        this.tipo = arguments?.getSerializable("tipo") as TipoCarro
        return view
    }

    //Inicializa as Views (Kotlin Extensions funciona aqui)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Views
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize(true)
        taskCarros()
    }

   /* override fun onResume() {
        super.onResume()
    }*/

    private fun taskCarros() {
        //Liga a animação do ProgressBar
        progress.visibility = View.VISIBLE
        doAsync {
            //Busca carros
            carros = CarroService.getCarros(tipo)
            uiThread {
                //Atualiza a lista
                recyclerView.adapter = CarroAdapter(carros) { onClickCarro(it) }
                //Esconde o ProgressBar
                progress.visibility = View.INVISIBLE
            }
        }
    }

    //Trata o evento de clique no carro
    private fun onClickCarro(carro: Carro) {
        activity?.startActivity<CarroActivity>("carro" to carro)
    }
}