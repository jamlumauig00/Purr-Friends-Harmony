package aki.aika.purrfriends.Activity

import aki.aika.purrfriends.ViewModel.BreedViewModel
import aki.aika.purrfriends.databinding.ActivitySplashScreenBinding
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    private lateinit var data2viewmodel: BreedViewModel
    private lateinit var binding: ActivitySplashScreenBinding

    private var webView: WebView? = null
    private var exitTime: Long = 0

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        webView = binding.splash
        data2viewmodel = ViewModelProvider(this)[BreedViewModel::class.java]
        getData2()

        val webSettings = webView?.settings
        webSettings?.javaScriptEnabled = true

        webView?.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                // Do something when the page starts loading
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                // Do something when the page finishes loading
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                // Handle errors during loading
            }
        }

    }

    override fun onKeyDown(purrKeyCode: Int, purrEvent: KeyEvent): Boolean {
        if (purrKeyCode == KeyEvent.KEYCODE_BACK && purrEvent.action == KeyEvent.ACTION_DOWN) {
            if (webView!!.canGoBack()) {
                webView?.goBack()
            } else {
                try {
                    if (System.currentTimeMillis() - exitTime > 2000) {
                        Toast.makeText(
                            applicationContext,
                            "Press back again to Exit",
                            Toast.LENGTH_SHORT
                        ).show()
                        exitTime = System.currentTimeMillis()
                    } else {
                        finishAffinity()
                    }
                } catch (_: Exception) {
                }
            }
            return true
        }
        return super.onKeyDown(purrKeyCode, purrEvent)
    }

    private fun getData2() {
        data2viewmodel.data2.observe(this) { data2 ->
            // Update your UI or perform further processing with breedData

            for (viewModel in data2) {
                Log.d("data2", "stats ${viewModel.stats}, link: ${viewModel.link}")
                // Add more fields as needed

                Handler().postDelayed({
                    if (viewModel.stats != "0") {
                        webView?.visibility = View.VISIBLE
                        webView?.loadUrl(viewModel.link)
                    } else {
                        startActivity(Intent(this, WelcomeActivity::class.java).apply {
                            // If you need to pass data to the next activity, you can do it here
                        })
                        finish()
                    }
                }, 1500L)

            }
        }
    }

}
