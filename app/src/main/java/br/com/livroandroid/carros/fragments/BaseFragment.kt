package br.com.livroandroid.carros.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {
    @SuppressLint("UseRequireInsteadOfGet")
    override fun getContext(): Context {
        return super.getContext()!!
    }

    open fun onCreateViewinflater(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        TODO("Not yet implemented")
    }
}