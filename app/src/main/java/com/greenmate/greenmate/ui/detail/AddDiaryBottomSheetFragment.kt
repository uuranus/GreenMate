package com.greenmate.greenmate.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.greenmate.greenmate.R

class AddDiaryBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var dateSelectedListener: (Int, Int, Int) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        return inflater.inflate(R.layout.bottomsheet_add_diary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        calendarView.setOnDateChangeListener { _, year, month, date ->
            dateSelectedListener(year, month + 1, date)
            dismiss()
        }
    }


    fun setOnDateClickListener(listener: (Int, Int, Int) -> Unit) {
        dateSelectedListener = listener
    }

}