package com.example.pull_sample

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.pull_sample.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WebView.setWebContentsDebuggingEnabled(true)
        binding.apply {
            webView.apply {
                settings.javaScriptEnabled = true
                webViewClient = TimeoutWebViewClient(100000) {
                    webView.loadUrl(TARGET_URL)
                }
                loadUrl(TARGET_URL)
            }

            swipeRefreshLayout.setOnRefreshListener {
                webView.reload()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TARGET_URL = "Set your URL here"
    }

    class TimeoutWebViewClient(
        private var timeoutMillis: Long,
        private val onTimeout: () -> Unit
    ) : WebViewClient() {

        private var handler: Handler? = null
        private var timeoutRunnable: Runnable? = null

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)

            handler?.removeCallbacks(timeoutRunnable!!)
            handler = Handler(Looper.getMainLooper())

            timeoutRunnable = Runnable {
                view?.stopLoading()
                onTimeout()
            }

            handler?.postDelayed(timeoutRunnable!!, timeoutMillis)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            handler?.removeCallbacks(timeoutRunnable!!)
        }
    }
}
