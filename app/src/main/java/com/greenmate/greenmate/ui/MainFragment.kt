package com.greenmate.greenmate.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.greenmate.greenmate.R
import com.greenmate.greenmate.adapter.GreenMateListAdapter
import com.greenmate.greenmate.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = GreenMateListAdapter(onClickListener = {
            val intent = Intent(requireContext(), DetailActivity::class.java)
            var options: ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(
                requireActivity(), binding.greenMateImageView, "String"
            )
            startActivity(intent, options.toBundle())
//            findNavController().navigate(R.id.action_mainFragment_to_detailFragment)
        })

        val data =
            listOf("그리니", "그린조아", "greenMate", "greenJoa", "그리니", "그린조아", "greenMate", "greenJoa")
        binding.run {
            greenMateRecyclerView.adapter = adapter
            adapter.submitList(data)

            val string = SpannableString(myGreenMateTextView.text)
            string.setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.accent_text_color
                    )
                ), 2, string.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            myGreenMateTextView.text = string

            mainGreenMateCardView.setOnClickListener {
                val intent = Intent(requireContext(), DetailActivity::class.java)
                var options: ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(
                    requireActivity(), it, "String"
                )
                startActivity(intent, options.toBundle())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}