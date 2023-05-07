package com.greenmate.greenmate.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.greenmate.greenmate.R
import com.greenmate.greenmate.databinding.ViewGreenAttributeBinding

class GreenAttributeView(
    context: Context,
    attrs: AttributeSet
) : CardView(context, attrs) {

    private var binding: ViewGreenAttributeBinding? = null
    private var iconSrc: Int? = null
    private var labelValue: String? = null
    private var labelTitle: String? = null

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ViewGreenAttributeBinding.inflate(inflater, this, true)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.GreenAttributeView,
            0, 0
        ).apply {

            labelTitle = getString(R.styleable.GreenAttributeView_labelTitle).toString()
            labelValue = getString(R.styleable.GreenAttributeView_labelValue).toString()
            iconSrc = getResourceId(R.styleable.GreenAttributeView_iconSrc, R.drawable.icon_sun)

            binding?.run {
                attributeNameTextView.text = labelTitle
                attributeValueTextView.text = labelValue
                attributeImageView.setImageResource(iconSrc ?: R.drawable.icon_sun)
            }

        }
    }
}