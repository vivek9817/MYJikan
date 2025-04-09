package com.example.myjikan.Utils

import android.app.Activity
import android.app.Service
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myjikan.R

object CommonUtils {

    var progressDialog: AlertDialog? = null
    lateinit var wmlp: WindowManager.LayoutParams

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

}