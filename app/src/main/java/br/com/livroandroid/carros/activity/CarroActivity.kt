package br.com.livroandroid.carros.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import br.com.livroandroid.carros.R
import br.com.livroandroid.carros.domain.Carro
import br.com.livroandroid.carros.domain.CarroService
import br.com.livroandroid.carros.domain.event.RefreshListEvent
import br.com.livroandroid.carros.extensions.loadUrl
import br.com.livroandroid.carros.extensions.setupToolbar
import kotlinx.android.synthetic.main.activity_carro.*
import kotlinx.android.synthetic.main.include_activity_carro.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.*

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
                //Mostra o alerta de confirmação
                alert("Confirmar exclusão do carro?"){
                    title = "Alert"
                    positiveButton(R.string.sim) {taskDeletar()}
                    negativeButton(R.string.nao) { }
                }.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //Deleta o carro
    private fun taskDeletar() {
        doAsync {
            val response = CarroService.delete(carro)
            uiThread {
                //Dispara o evento para atualizar a lista de carros
                EventBus.getDefault().post(RefreshListEvent())
                toast(response.msg)
                finish()
            }
        }
    }
}