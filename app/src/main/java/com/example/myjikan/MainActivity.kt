package com.example.myjikan

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myjikan.Model.MovieResponse
import com.example.myjikan.Utils.CommonUtils
import com.example.myjikan.Utils.CommonUtils.dismissProgressDialog
import com.example.myjikan.Utils.CommonUtils.initializeRecyclerView
import com.example.myjikan.Utils.CommonUtils.showProgressDialog
import com.example.myjikan.Utils.CommonUtils.showToast
import com.example.myjikan.ViewModel.MovieViewModel
import com.example.myjikan.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val movieViewModel: MovieViewModel by viewModels()

    lateinit var movieListRecView: RecyclerView
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout


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
                    it.data?.let {
                        (it as MovieResponse).let {
                            if(it.data.isEmpty()){
                                binding.linNoDataFound.isVisible = true
                                binding.swipeContainers.isGone = true
                            }else{
                                binding.linNoDataFound.isGone = true
                                binding.swipeContainers.isVisible = true

                                /*View List To The Rec View*/
                            }
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
}