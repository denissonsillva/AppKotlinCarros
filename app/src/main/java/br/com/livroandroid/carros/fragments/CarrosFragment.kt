package br.com.livroandroid.carros.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.livroandroid.carros.R
import br.com.livroandroid.carros.activity.CarroActivity
import br.com.livroandroid.carros.adapter.CarroAdapter
import br.com.livroandroid.carros.domain.Carro
import br.com.livroandroid.carros.domain.CarroService
import br.com.livroandroid.carros.domain.TipoCarro
import br.com.livroandroid.carros.domain.event.RefreshListEvent
import br.com.livroandroid.carros.utils.AndroidUtils
import kotlinx.android.synthetic.main.fragment_carros.*
import kotlinx.android.synthetic.main.include_progress.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread

@Suppress("DEPRECATION")
open class CarrosFragment : BaseFragment() {
    private var tipo: TipoCarro = TipoCarro.Classicos
    protected var carros = listOf<Carro>()

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        if (arguments != null) {
            tipo = arguments?.getSerializable("tipo") as TipoCarro
        }
        //Registra no Bus de Eventos
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        //Cancela o registro no Bus de Eventos
        EventBus.getDefault().unregister(this)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: RefreshListEvent) {
        //Recebeu o envento
        taskCarros()
    }


    //Cria a View do Fragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View? {
        // Retorna a view /res/layout/fragment_carros.xml
        return inflater.inflate(R.layout.fragment_carros, container, false)
    }

    //Inicializa as Views (Kotlin Extensions funciona aqui)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
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

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    open fun taskCarros() {
        //Liga a animação do ProgressBar
        progress.visibility = View.VISIBLE
        //Verifica a disponibilidade de internet
        val internetOk = AndroidUtils.isNetworkAvailable(context)
        if(internetOk) { //Em caso de bug, remover esse IF
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
    }

    //Trata o evento de clique no carro
    open fun onClickCarro(carro: Carro) {
        activity?.startActivity<CarroActivity>("carro" to carro)
    }
}