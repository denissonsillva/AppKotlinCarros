package br.com.livroandroid.carros.activity

import android.os.Bundle
import br.com.livroandroid.carros.R
import br.com.livroandroid.carros.domain.Carro
import br.com.livroandroid.carros.domain.TipoCarro
import br.com.livroandroid.carros.extensions.setupToolbar
import br.com.livroandroid.carros.fragments.CarrosFragment

class CarrosActivity : BaseActivity() {
    var tipo = TipoCarro.Classicos
    var carros = listOf<Carro>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carros)
        //Configura a Toolbar
        setupToolbar(R.id.toolbar)
        //Liga o up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //Lê o tipo de argumentos
        this.tipo = intent.getSerializableExtra("tipo") as TipoCarro
        //Título
        val s = context.getString(tipo.string)
        supportActionBar?.title = s

        //Adiciona o fragment no layout
        if (savedInstanceState == null) {
            //Cria uma instância do fragment e configura os argumentos
            val frag = CarrosFragment()
            // Dentre os argumentos que foram passados para a activity, está o tipo do carro.
            frag.arguments = intent.extras
            //Adicona o fragmente no layout de marcação
            supportFragmentManager.beginTransaction().add(R.id.container, frag).commit()
        }
    }
}
