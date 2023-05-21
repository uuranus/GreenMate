package com.greenmate.greenmate.adapter.bindingAdapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.greenmate.greenmate.R
import com.greenmate.greenmate.view.GreenAttributeView

@BindingAdapter("setImage")
fun setImage(view: ImageView, url: Int?) {
    if (url == null) {
        view.setImageResource(R.drawable.plant1)
        return
    }

    view.setImageResource(url)
    view.clipToOutline = true
}

@BindingAdapter("setValue")
fun setValue(view: GreenAttributeView, value: String?) {
    if (value == null) {
        view.setLabelValue("")
        return
    }

    view.setLabelValue(value)
}

@BindingAdapter("setVisibility")
fun setVisibility(view: View, value: Int) {
    view.isVisible = value != 0
}

@BindingAdapter("setFocused")
fun setFocused(view: TextView, value: Int) {
    if (value == 1) view.setTextColor(ContextCompat.getColor(view.context,
        R.color.accent_text_color))
    else view.setTextColor(ContextCompat.getColor(view.context, R.color.black))
}

@BindingAdapter("submitList")
fun <T> submitList(view: RecyclerView, data: List<T>) {
    val adapter = view.adapter as ListAdapter<T, RecyclerView.ViewHolder>

    adapter.submitList(data)
}