package com.shunsukeshoji.recipeapp.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.shunsukeshoji.recipeapp.R
import com.shunsukeshoji.recipeapp.databinding.ActivityRecipeDetailBinding

class RecipeDetailActivity : AppCompatActivity() {

    private companion object {
        const val RECIPE_BASE_URL = "https://cookpad.com/recipe/"
    }

    private val args: RecipeDetailActivityArgs by navArgs<RecipeDetailActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = RECIPE_BASE_URL + args.recipeId
        val client = RecipeWebViewClient { uri ->
            // cookpad以外のサイトを開いた時の処理
            val intent = Intent(
                Intent.ACTION_VIEW,
                uri
            )
            startActivity(intent)
        }

        binding.webView.apply {
            webChromeClient = WebChromeClient()
            webViewClient = client
            settings.javaScriptEnabled = true
            loadUrl(url)
        }

    }

    class RecipeWebViewClient(private val callback: (url: Uri) -> Unit) : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            request?.url?.let {
                val urlString = it.toString()
                return if (!urlString.contains("https://cookpad.com/recipe/")
                    && (urlString.startsWith("http://") || urlString.startsWith("https://"))
                ) {
                    callback(it)
                    true
                } else {
                    false
                }
            }
            return false
        }
    }
}