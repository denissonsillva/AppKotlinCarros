package br.com.livroandroid.carros.domain

import android.os.Parcel
import android.os.Parcelable

class Carro : Parcelable {
    var id: Long = 0
    var tipo = ""
    var nome = ""
    var desc = ""
    //@SerializedName("url_foto")
    var urlFoto = ""
    //@SerializedName("url_info")
    var urlInfo = ""
   // @SerializedName("url_video")
    var urlVideo = ""
    var latitude = ""
    var longitude = ""

    override fun toString(): String {
        return "Carro(nome='$nome')"
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        // Escreve os dados para serem serializados
        dest.writeLong(id)
        dest.writeString(this.tipo)
        dest.writeString(this.nome)
        dest.writeString(this.desc)
        dest.writeString(this.urlFoto)
        dest.writeString(this.urlInfo)
        dest.writeString(this.urlVideo)
        dest.writeString(this.latitude)
        dest.writeString(this.longitude)
    }

    fun readFromParcel(parcel: Parcel) {
        // Lê os dados na mesma ordem em que foram escritos
        this.id = parcel.readLong()
        this.tipo = parcel.readString().toString()
        this.nome = parcel.readString().toString()
        this.desc = parcel.readString().toString()
        this.urlFoto = parcel.readString().toString()
        this.urlInfo = parcel.readString().toString()
        this.urlVideo = parcel.readString().toString()
        this.latitude = parcel.readString().toString()
        this.longitude = parcel.readString().toString()
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Carro> = object : Parcelable.Creator<Carro> {
            override fun createFromParcel(p: Parcel): Carro {
                // Cria o objeto carro com um Parcel
                val c = Carro()
                c.readFromParcel(p)
                return c
            }

            override fun newArray(size: Int): Array<Carro?> {
                // Retorna um array vazio
                return arrayOfNulls(size)
            }
        }
    }
}