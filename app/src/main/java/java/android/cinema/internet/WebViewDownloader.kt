package java.android.cinema.internet

import android.os.Handler
import android.os.Looper
import android.webkit.WebView
import android.webkit.WebViewClient
import java.android.cinema.activity.MainActivity

object WebViewDownloader {

    fun download(url:String,webView: WebView){
        webView.settings.javaScriptEnabled = true

        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)
    }

    fun download2(url:String,webView: WebView){
        MainActivity.activityMain.myThread.handler?.let {
            it.post{
                Handler(Looper.getMainLooper()).post {
                    webView.settings.javaScriptEnabled = true
                    webView.webViewClient = WebViewClient()
                    webView.loadUrl(url)
                }
            }
        }

    }

}