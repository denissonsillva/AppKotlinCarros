package br.com.livroandroid.carros.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import br.com.livroandroid.carros.R
import br.com.livroandroid.carros.domain.Carro
import br.com.livroandroid.carros.extensions.loadUrl
import br.com.livroandroid.carros.extensions.setupToolbar
import kotlinx.android.synthetic.main.activity_carro.*
import kotlinx.android.synthetic.main.include_activity_carro.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_carro, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_editar -> {
                //Abre a tela de cadastro(editar carro)
                //Passa o carro como parâmetro
                startActivity<CarroFormActivity>("carro" to carro)
                finish()
            }
            R.id.action_deletar -> {
                toast("deletar carro")
            }
        }
        return super.onOptionsItemSelected(item)
    }
}