package br.com.livroandroid.carros.activity

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import br.com.livroandroid.carros.R
import br.com.livroandroid.carros.activity.dialogs.AboutDialog
import br.com.livroandroid.carros.extensions.setupToolbar
import kotlinx.android.synthetic.main.activity_site_livro.*

class SiteLivroActivity : BaseActivity() {
    private val URL_SOBRE = "https://www.djektech.com.br"

    override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.activity_site_livro)

        //toolbar
        val actionBar = setupToolbar(R.id.toolbar)
        actionBar.setDisplayHomeAsUpEnabled(true)

        /*executar javascript
        val settings = webview.settings
        settings.javaScriptEnabled = true
        webview.loadUrl("javascript:alert('Olá)")*/

        //WebView carrega a pagina
        setWebViewClient(webview)
        webview.loadUrl(URL_SOBRE)



        //Swipe to refresh
        swipeToRefresh.setOnRefreshListener {
            webview.reload()
        }
        // Cores da animação
        swipeToRefresh.setColorSchemeResources(
            R.color.refresh_progress_1,
            R.color.refresh_progress_2,
            R.color.refresh_progress_3)
    }

    private fun setWebViewClient(webview: WebView?) {
        webview?.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                // Liga o progress
                progress.visibility = View.VISIBLE
                // Termina a animação do Swipe to Refresh
            }

            override fun onPageFinished(view: WebView, url: String) {
                // Desliga o progress
                progress.visibility = View.INVISIBLE
                // Termina a animação do Swipe to Refresh
                swipeToRefresh.isRefreshing = false
            }

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url = request?.url.toString()
                if (url.endsWith("djektech.htm")) {
                    // Alerta customizado
                    AboutDialog.showAbout(supportFragmentManager)
                    // Retorna true para informar que interceptamos o evento
                    return true
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
    }
}