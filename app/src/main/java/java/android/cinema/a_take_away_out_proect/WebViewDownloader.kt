package java.android.cinema.a_take_away_out_proect

import android.os.Handler
import android.os.Looper
import android.webkit.WebView
import android.webkit.WebViewClient
import java.android.cinema.activity.MainActivity

object WebViewDownloader {

    fun download(url:String,webView: WebView){

        Handler(Looper.getMainLooper()).post {

            webView.settings.javaScriptEnabled = true
            webView.webViewClient = WebViewClient()
            webView.loadUrl(url)

        }

    }

}