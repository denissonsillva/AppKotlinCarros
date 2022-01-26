package br.com.livroandroid.carros.activity

import android.annotation.SuppressLint
import android.content.Context
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    protected val context: Context get() = this
    open fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }
}