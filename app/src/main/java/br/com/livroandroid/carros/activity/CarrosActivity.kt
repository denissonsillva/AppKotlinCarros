package br.com.livroandroid.carros.activity

import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.livroandroid.carros.R
import br.com.livroandroid.carros.adapter.CarroAdapter
import br.com.livroandroid.carros.domain.Carro
import br.com.livroandroid.carros.domain.CarroService
import br.com.livroandroid.carros.domain.TipoCarro
import br.com.livroandroid.carros.extensions.setupToolbar
import kotlinx.android.synthetic.main.activity_carros.*
import org.jetbrains.anko.startActivity

class CarrosActivity : BaseActivity() {

    private var tipo: TipoCarro = TipoCarro.Classicos
    private var carros = listOf<Carro>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carros)
        setupToolbar(R.id.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.tipo = intent.getSerializableExtra("tipo") as TipoCarro

        //RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize =(true)

        override fun onResume() {
            super.onResume()
            taskCarros()
        }

       fun taskCarros() {
            //Busca carros
            this.carros = CarroService.getCarros(context, tipo)
            //Atualiza a lista
            recyclerView.adapter = CarroAdapter(carros) {onClickCarro(it)}
       }

        //Trata o evento de clique no carro
        private fun onClickCarro(carro: Carro) {
            startActivity<CarroActivity>("carro" to carro)
        }
    }
}
