package br.com.livroandroid.carros.activity

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import br.com.livroandroid.carros.R
import br.com.livroandroid.carros.domain.Carro
import br.com.livroandroid.carros.domain.CarroService
import br.com.livroandroid.carros.domain.event.RefreshListEvent
import br.com.livroandroid.carros.domain.FavoritosService
import br.com.livroandroid.carros.extensions.loadUrl
import br.com.livroandroid.carros.extensions.setupToolbar
import kotlinx.android.synthetic.main.activity_carro.*
import kotlinx.android.synthetic.main.include_activity_carro.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.*

class CarroActivity : BaseActivity() {
    val carro by lazy {intent.getParcelableExtra<Carro>("carro")}
    override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.activity_carro)
        //Configura a Toolbar
        setupToolbar(R.id.toolbar, carro?.nome, true)
        //Atualiza descrição do carro
        tDesc.text = carro?.desc
        //Mostra a foto do carro na image view
        appBarImg.loadUrl(carro?.urlFoto)

        // Variável gerada automaticamente pelo Kotlin Extensions
        fab.setOnClickListener { onClickFavoritar(carro) }
    }

    override fun onResume() {
        super.onResume()
        taskUpdateFavoritoColor()
    }

    // Busca no banco se o carro está favoritado e atualiza a cor do FAB
    private fun taskUpdateFavoritoColor() {
        doAsync {
            val b = FavoritosService.isFavorito(carro)
            uiThread {
                setFavoriteColor(b)
            }
        }
    }

    // Desenha a cor do FAB conforme está favoritado ou não.
    fun setFavoriteColor(favorito: Boolean) {
        // Troca a cor conforme o status do favoritos
        val fundo = ContextCompat.getColor(this, if (favorito) R.color.favorito_on else R.color.favorito_off)
        val cor = ContextCompat.getColor(this, if (favorito) R.color.yellow else R.color.favorito_on)
        fab.backgroundTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(fundo))
        fab.setColorFilter(cor)
    }

    //Adiciona ou remove carro dos favoritos
    private fun onClickFavoritar(carro: Carro?) {
        taskFavoritar(carro)
    }

    private fun taskFavoritar(carro: Carro?) {
        doAsync {
            val favoritado = FavoritosService.favoritar(carro)
            uiThread {
                // Dispara um evento para atualizar a lista
                EventBus.getDefault().post(RefreshListEvent())
                // Alerta de sucesso
                toast(if (favoritado) R.string.msg_carro_favoritado else R.string.msg_carro_desfavoritado)
            }
        }

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