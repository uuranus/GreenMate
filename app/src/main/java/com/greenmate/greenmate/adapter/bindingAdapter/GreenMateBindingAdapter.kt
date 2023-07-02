package com.greenmate.greenmate.adapter.bindingAdapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.greenmate.greenmate.R
import com.greenmate.greenmate.util.IMAGE_BASE_URL
import com.greenmate.greenmate.view.GreenAttributeView

@BindingAdapter("setImage")
fun setImage(view: ImageView, url: String?) {
    view.clipToOutline = true

    if (url == null || url.isEmpty()) {
        view.setImageResource(R.drawable.plant1)
        return
    }

    Glide.with(view.context)
        .load("${IMAGE_BASE_URL}${url}.jpeg")
        .error(R.drawable.plant1)
        .into(view)
}

@BindingAdapter("setByteImage")
fun setByteImage(view: ImageView, url: ByteArray?) {
    view.clipToOutline = true

    if (url == null || url.isEmpty()) {
        view.setImageResource(R.drawable.plant1)
        return
    }

    println("urllll $url")
    Glide.with(view.context)
        .load(url)
        .into(view)
}

@BindingAdapter("setDrawableImg")
fun setDrawableImg(view: ImageView, url: Int) {
    view.clipToOutline = true

    view.setImageResource(url)
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
    if (value == 1) view.setTextColor(
        ContextCompat.getColor(
            view.context,
            R.color.accent_text_color
        )
    )
    else view.setTextColor(ContextCompat.getColor(view.context, R.color.black))
}

@BindingAdapter("submitList")
fun <T> submitList(view: RecyclerView, data: List<T>) {
    val adapter = view.adapter as ListAdapter<T, RecyclerView.ViewHolder>

    adapter.submitList(data)
}

@BindingAdapter(value = ["isListEmpty", "isListFocus"], requireAll = true)
fun setTextVisible(view: TextView, isEmpty: Int, isFocused: Int) {

    view.isVisible = (isEmpty == 0 && isFocused == 1)
}

@BindingAdapter("showNeededItem")
fun showNeededItem(view: ImageView, status: String) {
    view.isVisible = status != "좋음"
}