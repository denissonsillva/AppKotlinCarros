package br.com.livroandroid.carros.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import br.com.livroandroid.carros.R
import br.com.livroandroid.carros.domain.Carro
import br.com.livroandroid.carros.domain.CarroService
import br.com.livroandroid.carros.domain.event.RefreshListEvent
import br.com.livroandroid.carros.extensions.setupToolbar
import br.com.livroandroid.carros.extensions.toast
import kotlinx.android.synthetic.main.activity_carro_form_contents.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class CarroFormActivity : BaseActivity() {
    // O carro pode ser nulo no caso de um Novo Carro
    val carro:Carro? by lazy { intent.getParcelableExtra<Carro>("carro") }
    override fun onCreate(saveInstanceState: Bundle?) {
        super.onCreate(saveInstanceState)
        setContentView(R.layout.activity_carro_form)
        // Título da Toolbar (Nome do carro ou Novo Carro)
        val title = carro?.nome ?: getString(R.string.novo_carro)
        setupToolbar(R.id.toolbar, title, true)
        // Liga o up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    // Adiciona as opções Salvar e Deletar no menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_form_carro, menu)
        return true
    }
    // Trata os eventos do menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_salvar -> {
                taskSalvar()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //Cria a trhead para salvar o carro
    private fun taskSalvar() {
        doAsync {
            val c = getCarroForm()
            //Salva carro
            val response = CarroService.save(c)
            uiThread {
                //Dispara o evento para atualizar a lista de carros
                EventBus.getDefault().post(RefreshListEvent())
                toast(response.msg)
                finish()
            }
        }
    }

    //Cria um carro com valores do formulário
    fun getCarroForm(): Carro {
        val c = carro ?: Carro()
        c.tipo = getTipo()
        c.nome = tNomeForm.getText().toString()
        c.desc = tDescForm.getText().toString()
        return c
    }

    //Converte o valor do radio para string
    fun getTipo(): String {
        when(radioTipo.getCheckedRadioButtonId()) {
            R.id.tipoEsportivo -> return getString(R.string.esportivos)
            R.id.tipoLuxo -> return getString(R.string.luxo)
        }
        return getString(R.string.classicos)
    }
}

