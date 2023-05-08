package com.greenmate.greenmate.adapter.bindingAdapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.greenmate.greenmate.R
import com.greenmate.greenmate.view.GreenAttributeView

@BindingAdapter("setImage")
fun setImage(view: ImageView, url: Int?) {
    if (url == null) {
        view.setImageResource(R.drawable.plant1)
        return
    }

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