package br.com.livroandroid.carros.activity

import android.os.Bundle
import android.widget.TextView
import br.com.livroandroid.carros.R
import br.com.livroandroid.carros.domain.TipoCarro
import br.com.livroandroid.carros.extensions.addFragment
import br.com.livroandroid.carros.extensions.setupToolbar

class CarrosActivity : BaseActivity() {

    private var tipo: TipoCarro = TipoCarro.Classicos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carros)
        setupToolbar(R.id.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this.tipo = intent.getSerializableExtra("tipo") as TipoCarro
        val s = context.getString(tipo.string)
        supportActionBar?.title = s
        val text = findViewById<TextView>(R.id.text)
        text.text = "Carros $s"
    }
}
