package java.android.cinema.a_take_away_out_proect

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import java.android.cinema.databinding.FragmentWebViewBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.stream.Collectors

class WebViewFragment : Fragment() {

    lateinit var binding: FragmentWebViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebViewBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // как мы загружали страницу на уроке
        binding.download.setOnClickListener{

            binding.url.text.let {
                var myConnection: HttpURLConnection? = null
                val uri = URL(it.toString())

                myConnection = uri.openConnection() as HttpURLConnection

                myConnection.readTimeout = 5000

                //val handler = Handler(Looper.myLooper()!!)
                val handler = Handler(Looper.getMainLooper())

                Thread{

                    val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                    val result = getLines(reader)

                    handler.post {
                        binding.webView.loadDataWithBaseURL(
                            null,
                            result,
                            "text/html; charset=utf-8",
                            "utf-8",
                            null
                        )
                    }

                }.start()

            }
        }

        // простой способ
        binding.downloadSimple.setOnClickListener{
            val webView = binding.webView
            webView.settings.javaScriptEnabled = true
            webView.loadUrl(binding.url.text.toString())
            webView.webViewClient = WebViewClient()
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining(""))
    }

}