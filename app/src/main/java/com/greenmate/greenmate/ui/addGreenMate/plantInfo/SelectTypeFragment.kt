package com.greenmate.greenmate.ui.addGreenMate.plantInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.greenmate.greenmate.R
import com.greenmate.greenmate.adapter.addGreenMate.PlantTypeListAdapter
import com.greenmate.greenmate.databinding.FragmentSelectTypeBinding
import com.greenmate.greenmate.ui.addGreenMate.AddGreenMateViewModel

class SelectTypeFragment : Fragment() {

    private var _binding: FragmentSelectTypeBinding? = null
    private val binding: FragmentSelectTypeBinding get() = _binding!!
    private val addGreenMateViewModel: AddGreenMateViewModel by activityViewModels()
    private lateinit var plantTypeAdapter: PlantTypeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_select_type, container, false)
        plantTypeAdapter = PlantTypeListAdapter {
            addGreenMateViewModel.setCurrentPlantType(it)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            vm = addGreenMateViewModel
            lifecycleOwner = this@SelectTypeFragment.viewLifecycleOwner

            searchView.setOnQueryTextListener(object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(text: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(text: String?): Boolean {
                    text ?: return true
                    addGreenMateViewModel.search(text)
                    return true
                }
            })

            plantTypeRecyclerView.adapter = plantTypeAdapter

            continueButton.setOnClickListener {
                if (addGreenMateViewModel.isModuleAdded()) {
                    findNavController().navigate(R.id.action_selectTypeFragment_to_cameraCheckFragment)
                } else {
                    findNavController().navigate(R.id.action_selectTypeFragment2_to_cameraCheckFragment2)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}