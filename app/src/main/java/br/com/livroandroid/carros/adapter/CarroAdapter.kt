package br.com.livroandroid.carros.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.livroandroid.carros.R
import br.com.livroandroid.carros.domain.Carro
import br.com.livroandroid.carros.extensions.loadUrl
import kotlinx.android.synthetic.main.adapter_carro.view.*

// Define o construtor que recebe (carros,onClick)
class CarroAdapter (
    val carros: List<Carro>,
    val onClick: (Carro) -> Unit) :
    RecyclerView.Adapter<CarroAdapter.CarrosViewHolder>() {

    // Retorna a quantidade de carros na lista
    override fun getItemCount(): Int {
        return this.carros.size
    }

    // Infla o layout do adapter e retorna o ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarrosViewHolder {
        // Infla a view do adapter
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_carro, parent, false)
        // Retorna o ViewHolder que contém todas as views
        val holder = CarrosViewHolder(view)
        return holder
    }
    // Faz o bind para atualizar o valor das views com os dados do Carro
    override fun onBindViewHolder(holder: CarrosViewHolder, position: Int) {
        // Recupera o objeto carro
        val carro = carros[position]
        //Declara a variável view para facilitar o acesso abaixo
        //A view contém as variáveis definidas no XML (lembrar o nome de cada id)
        val view = holder.itemView
        // Atualiza os dados do carro
        with(view) {
            tNome.text = carro.nome
            //Faz o download da foto e mostra o ProgressBar
            img.loadUrl(carro.urlFoto, view.progress)
            //Adiciona o evento de click na linha
            setOnClickListener { onClick(carro) }
        }
    }
    //ViewHolder fica vazio pois foi usado o import do Android KotlinExtensions
    class CarrosViewHolder(view: View) : RecyclerView.ViewHolder(view) {}
}