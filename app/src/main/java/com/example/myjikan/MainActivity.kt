package com.example.myjikan

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.ImageViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myjikan.Adapter.CommonAlertAdapter
import com.example.myjikan.Model.Daum
import com.example.myjikan.Model.MovieResponse
import com.example.myjikan.Utils.CommonUtils
import com.example.myjikan.Utils.CommonUtils.checkTypeCast
import com.example.myjikan.Utils.CommonUtils.dismissProgressDialog
import com.example.myjikan.Utils.CommonUtils.filterListByValue
import com.example.myjikan.Utils.CommonUtils.initializeRecyclerView
import com.example.myjikan.Utils.CommonUtils.showProgressDialog
import com.example.myjikan.Utils.CommonUtils.showToast
import com.example.myjikan.View.ViewSelectedMovie
import com.example.myjikan.ViewHolder.GenericViewHolder
import com.example.myjikan.ViewModel.MovieViewModel
import com.example.myjikan.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val movieViewModel: MovieViewModel by viewModels()

    lateinit var movieListRecView: RecyclerView
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    var backToExit: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        movieListRecView = initializeRecyclerView(
            binding.recViewMovieList,
            1,
            isReverseLayout = false,
            isItemDecoration = false,
            dividerDecoration = 0,
            ctx = this@MainActivity
        )

        mSwipeRefreshLayout = binding.swipeContainers
        mSwipeRefreshLayout.isRefreshing = false
        mSwipeRefreshLayout.setColorSchemeResources(
            R.color.colorPrimary,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark
        )

        clickHandleOnUi()
    }

    private fun clickHandleOnUi() {
        /*Call Network to get Movie List*/
        getMovieListFormServer()

        /*Refresh family list*/
        mSwipeRefreshLayout.setOnRefreshListener {
            mSwipeRefreshLayout.post(Runnable {
                mSwipeRefreshLayout.isRefreshing = true
                getMovieListFormServer()
                mSwipeRefreshLayout.isRefreshing = false
            })
        }
    }

    private fun getMovieListFormServer() {
        movieViewModel.getTopAnimList().observe(this@MainActivity, Observer {
            when (it.status) {
                CommonUtils.ApiStatus.LOADING -> {
                    showProgressDialog(this)
                }

                CommonUtils.ApiStatus.SUCCESS -> {
                    dismissProgressDialog()
                    val response = it.data as MovieResponse
                    response?.let {
                        if (it.data.isEmpty()) {
                            binding.linNoDataFound.isVisible = true
                            binding.swipeContainers.isGone = true
                        } else {
                            binding.linNoDataFound.isGone = true
                            binding.swipeContainers.isVisible = true
                            binding.fieldSearch.setQuery("", false)
                            binding.fieldSearch.clearFocus()
                            movieListRecView.adapter = viewMovieItemInRecView(it.data)

                            filterListByValue(
                                binding.fieldSearch,
                                "1",
                                it.data as ArrayList<Any>,
                                movieListRecView.adapter as CommonAlertAdapter<Any>
                            ) { arr ->
                                movieListRecView.adapter = viewMovieItemInRecView(arr)
                            }

                            /*View List To The Rec View*/
                            viewMovieItemInRecView(it.data)
                        }
                    }
                }

                CommonUtils.ApiStatus.FAILURE -> {
                    dismissProgressDialog()
                    showToast(this@MainActivity, it.message.toString())
                }
            }
        })
    }

    private fun viewMovieItemInRecView(data: List<Any>): CommonAlertAdapter<Any> {
        return object : CommonAlertAdapter<Any>(
            R.layout.common_layout_view_for_list,
            data as ArrayList<Any>
        ) {
            override fun bindData(holder: GenericViewHolder<Any>, item: Any) {
                checkTypeCast<Daum>(item)?.apply {
                    val viewLayout = holder.itemView

                    /*Movie Image Poster*/
                    Glide.with(this@MainActivity)
                        .load(images?.jpg?.image_url)
                        .thumbnail(
                            Glide.with(this@MainActivity)
                                .load(R.drawable.placeholder_image)
                                .centerInside()
                        )
                        .error(R.drawable.error_img)
                        .centerCrop()
                        .into(viewLayout.findViewById(R.id.imageViewMoviePoster))

                    /*Title Name*/
                    viewLayout.findViewById<AppCompatTextView>(R.id.textViewTitle).text = "${
                        title?.capitalize(
                            Locale.ROOT
                        )
                    }"

                    /*Episode Details*/
                    "$episodes Episode".also {
                        viewLayout.findViewById<AppCompatTextView>(R.id.textViewEpisode).text = it
                    }

                    /*Description*/
                    "$synopsis".also {
                        viewLayout.findViewById<AppCompatTextView>(R.id.textViewDescription).text =
                            it
                    }

                    /*Rating*/
                    "$score".also {
                        viewLayout.findViewById<AppCompatTextView>(R.id.textViewRating).text = it
                    }

                    /*Open New Tab*/
                    viewLayout.findViewById<LinearLayoutCompat>(R.id.linMovieItem)
                        .setOnClickListener {
                            goToNextPageForMoreDetails(mal_id)
                        }

                }
            }

            override fun clickHandler(pos: Int, item: Any, aView: View) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun goToNextPageForMoreDetails(malId: Long) {
        val intent = Intent(this@MainActivity, ViewSelectedMovie::class.java)
        intent.putExtra(
            "anim_id", malId
        )
        startActivity(intent)
    }

    @SuppressLint("MissingSuperCall")
    @Deprecated("needed")
    override fun onBackPressed() {
        if (backToExit) ActivityCompat.finishAffinity(this)
        this.backToExit = true
        showToast(this@MainActivity, "Press BACK again to exit")
        Handler().postDelayed(Runnable { backToExit = false }, 2000)
    }
}