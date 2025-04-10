package com.example.myjikan.Utils

import android.animation.Animator
import android.app.Activity
import android.app.Service
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.Window
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myjikan.Adapter.CommonAlertAdapter
import com.example.myjikan.R
import com.google.gson.Gson
import kotlin.math.hypot

object CommonUtils {

    var progressDialog: AlertDialog? = null

    enum class ApiStatus {
        LOADING, SUCCESS, FAILURE
    }

    fun showProgressDialog(mContext: Context) {
        progressDialog?.let {
            dismissProgressDialog()
        }

        val inflater = (mContext as Activity).layoutInflater
        val view = inflater.inflate(R.layout.custom_progress_dialog, null)

        val progressDialogBuilder = AlertDialog.Builder(mContext)
        progressDialog = progressDialogBuilder.setView(view).create()

        // Optional: Adjust width/height here for centered size
        val width = (mContext.resources.displayMetrics.widthPixels * 0.6).toInt()
        val height = (mContext.resources.displayMetrics.heightPixels * 0.3).toInt()

        progressDialog?.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            show()

            // Ensure window is non-fullscreen and centered
            window?.apply {
                setLayout(width, height)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setGravity(Gravity.CENTER)
            }
        }
    }

    fun dismissProgressDialog() {
        try {
            progressDialog?.let {
                if (progressDialog!!.isShowing) {
                    progressDialog!!.dismiss()
                    progressDialog = null
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**show toast bar*/
    fun showToast(context: Context, message: String) {
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.layout_custom_toast, null)
        val textView = layout.findViewById<TextView>(R.id.customToastMessage)
        textView.text = message

        val toast = Toast(context)
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 100)
        toast.show()
    }

    /**
     * Common RecyclerView Function
     * @param(0-> HORIZONTAL OR 1-> VERTICAL)
     * @param(orientation is 0 OR 1)
     * @param(isReverseLayout is TRUE OR FALSE)
     */
    fun initializeRecyclerView(
        view: RecyclerView,
        orientation: Int,
        isReverseLayout: Boolean,
        isItemDecoration: Boolean,
        dividerDecoration: Int,
        ctx: Context
    ): RecyclerView {
        view.layoutManager = LinearLayoutManager(
            ctx, orientation, isReverseLayout
        )
        view.setHasFixedSize(true)
        if (isItemDecoration) {
            when (dividerDecoration) {
                1 -> {
                    view.addItemDecoration(
                        DividerItemDecoration(
                            ctx, DividerItemDecoration.VERTICAL
                        )
                    )
                }

                0 -> {
                    view.addItemDecoration(
                        DividerItemDecoration(
                            ctx, DividerItemDecoration.HORIZONTAL
                        )
                    )
                }
            }
        }
        return view
    }

    inline fun <reified T> checkTypeCast(anything: Any): T? {
        return anything as? T
    }

    /**
     * Converts an object of type any to a provided class object for now
     * @param map An object of any type
     * @return an object of supplied type
     */
    inline fun <reified L> convertLinkedTreeMapToClass(map: Any): L {
        Gson().apply { return this.fromJson(this.toJsonTree(map).asJsonObject, L::class.java) }
    }

    /**
     * Filter a given arraylist with values
     */
    fun <T> filterListByValue(
        searchView: androidx.appcompat.widget.SearchView,
        tag: String,
        originalArrayList: ArrayList<T>,
        adapter: CommonAlertAdapter<T>,
        onSearchResultReceived: (ArrayList<T>) -> Unit
    ) {
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query == "") onSearchResultReceived(originalArrayList)
                else {
                    adapter.apply {
                        getFilter(tag) {
                            onSearchResultReceived(it)
                        }.filter(query)
                    }
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 == "") onSearchResultReceived(originalArrayList)
                else {
                    adapter.apply {
                        getFilter(tag) {
                            onSearchResultReceived(it)
                        }.filter(p0)
                    }
                }
                return true
            }
        })
    }

    fun circularRevealAView(
        view: View,
        duration: Long? = 750L,
        interpolator: Interpolator? = AccelerateDecelerateInterpolator(),
        onDone: (Boolean) -> Unit
    ) {
        view.post {
            val cx = view.width / 2
            val cy = view.height / 2
            val finalRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()
            val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0f, finalRadius)
            anim.duration = duration!!
            anim.interpolator = interpolator
            view.visibility = View.VISIBLE
            anim.start()
            anim.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    onDone.invoke(true)
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
        }
    }

    fun waitForSpecifiedTime(duration: Long = 500, onDone: () -> Unit) {
        val mHandler = Handler()
        mHandler.postDelayed({
            mHandler.removeCallbacksAndMessages(null)
            onDone.invoke()
        }, duration)
    }



}