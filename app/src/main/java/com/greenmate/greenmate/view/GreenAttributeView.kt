package com.greenmate.greenmate.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
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

            try {
                labelTitle = getString(R.styleable.GreenAttributeView_labelTitle).toString()
                labelValue = getString(R.styleable.GreenAttributeView_labelValue).toString()
                iconSrc =
                    getResourceId(R.styleable.GreenAttributeView_iconSrc, R.drawable.icon_light)
            } finally {
                recycle()
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        binding?.run {
            attributeNameTextView.text = labelTitle
            attributeValueTextView.text = labelValue
            attributeImageView.setImageResource(iconSrc ?: R.drawable.icon_light)
        }
    }

    fun setLabelValue(value: String) {
        labelValue = value
        invalidate()
        requestLayout()
    }
}