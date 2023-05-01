package com.greenmate.greenmate.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.greenmate.greenmate.databinding.ViewGreenAttributeBinding

class GreenAttributeView(
    context: Context,
    attrs: AttributeSet
) : CardView(context, attrs) {

    private var binding: ViewGreenAttributeBinding? = null

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ViewGreenAttributeBinding.inflate(inflater, this, true)

        context.theme.obtainStyledAttributes(
            attrs,
            androidx.cardview.R.styleable.CardView,
            0, 0
        )
    }
}