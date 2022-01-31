package br.com.livroandroid.carros.activity

import android.os.Bundle
import br.com.livroandroid.carros.R
import br.com.livroandroid.carros.domain.Carro
import br.com.livroandroid.carros.extensions.loadUrl
import br.com.livroandroid.carros.extensions.setupToolbar
import kotlinx.android.synthetic.main.activity_carro.*
import kotlinx.android.synthetic.main.include_activity_carro.*

class CarroActivity : BaseActivity() {
    val carro by lazy {intent.getParcelableExtra<Carro>("carro")}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carro)
        //Configura a Toolbar
        setupToolbar(R.id.toolbar, carro?.nome, true)
        //Atualiza descrição do carro
        tDesc.text = carro?.desc
        //Mostra a foto do carro na image view
        appBarImg.loadUrl(carro?.urlFoto)
    }
}