package com.example.myjikan.View

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.myjikan.Adapter.CommonAlertAdapter
import com.example.myjikan.Model.AnimDetailsResponse
import com.example.myjikan.Model.AnimDetailsResponseObj
import com.example.myjikan.Model.MovieResponse
import com.example.myjikan.Model.Trailers
import com.example.myjikan.R
import com.example.myjikan.Utils.CommonUtils
import com.example.myjikan.Utils.CommonUtils.dismissProgressDialog
import com.example.myjikan.Utils.CommonUtils.filterListByValue
import com.example.myjikan.Utils.CommonUtils.showProgressDialog
import com.example.myjikan.Utils.CommonUtils.showToast
import com.example.myjikan.ViewModel.MovieViewModel
import com.example.myjikan.databinding.ActivityMainBinding
import com.example.myjikan.databinding.ActivityViewSelectedMovieBinding
import java.util.Locale

class ViewSelectedMovie : AppCompatActivity() {
    private lateinit var binding: ActivityViewSelectedMovieBinding
    private val movieViewModel: MovieViewModel by viewModels()

    private var anim_id: Long? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityViewSelectedMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getValueFromIntent()
        onClickOnUi()
    }

    private fun onClickOnUi() {
        movieViewModel.getAnimDetails(anim_id ?: 0).observe(this@ViewSelectedMovie, Observer {
            when (it.status) {
                CommonUtils.ApiStatus.LOADING -> {
                    showProgressDialog(this)
                }

                CommonUtils.ApiStatus.SUCCESS -> {
                    dismissProgressDialog()
                    val response = it.data as AnimDetailsResponse
                    response?.let {
                        /*Set Anim Details*/
                        setAnimDetails(response.data)
                    }
                }

                CommonUtils.ApiStatus.FAILURE -> {
                    dismissProgressDialog()
                    showToast(this@ViewSelectedMovie, it.message.toString())
                }
            }
        })
    }

    private fun setAnimDetails(data: AnimDetailsResponseObj) {
        binding.apply {

            /*Check If Video Found Then Play otherwise Set Poster*/
            data.trailer?.let {
                setVideoAndPosterAndPlay(it,data)
            }

            /*Set Genre and Rating*/
            val result = data.genres?.joinToString(" | ") { it.name }
            "$result     ⭐ ${data.score}".also { binding.txtGenreAndRating.text = it }

            /*Main Cast*/
            val mainCast = data.producers?.joinToString(" | ") { it.name }
            binding.txtMainCast.text = mainCast

            /*Episode*/
            "${data.episodes} Episode".also { binding.txtEpisode.text = it }

            /*Title*/
            txtTitle.text = data.title?.capitalize(Locale.ROOT)

            /*Synopsis*/
            txtDescription.text = data.synopsis
        }
    }

    private fun setVideoAndPosterAndPlay(it: Trailers,data: AnimDetailsResponseObj) {
        if (it.youtube_id.isNullOrBlank()) {
            binding.webViewTrailer.isGone = true
            binding.imageViewPoster.isVisible = true

            /*Set Poster*/
            Glide.with(this)
                .load(data?.images?.jpg?.large_image_url)
                .placeholder(R.drawable.placeholder_image)
                .into(binding.imageViewPoster)
        } else {
            binding.imageViewPoster.isGone = true
            binding.webViewTrailer.isVisible = true
            val webView = findViewById<WebView>(R.id.webViewTrailer)

            val videoId = it.youtube_id
            val html = """
            <html>
                <body style="margin:0;padding:0;">
                    <iframe width="100%" height="100%"
                        src="https://www.youtube.com/embed/$videoId"
                        frameborder="0"
                        allowfullscreen>
                    </iframe>
                </body>
            </html>""".trimIndent()

            webView.settings.javaScriptEnabled = true
            webView.settings.domStorageEnabled = true
            webView.settings.mediaPlaybackRequiresUserGesture = false
            webView.webChromeClient = WebChromeClient()
            webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null)
            webView.visibility = View.VISIBLE
        }
    }

    private fun getValueFromIntent() {
        intent.getLongExtra("anim_id", -1L)?.let {
            anim_id = it
        }
    }
}