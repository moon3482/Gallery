package com.charlie.gallery.ui.fragment.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.charlie.gallery.R
import com.charlie.gallery.databinding.FragmentListBinding
import com.charlie.gallery.network.RetrofitClient
import com.charlie.gallery.ui.fragment.detail.DetailFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding
        get() = checkNotNull(_binding) {
            "_binding iS Null"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getImageList()
    }

    private fun initView() {

        binding.gridListRecyclerview.apply {
            adapter = ListAdapter { currentId ->
                parentFragmentManager.commit {
                    add(
                        R.id.fragment_container,
                        DetailFragment.newInstance(
                            currentId = currentId,
                        )
                    )
                    addToBackStack(null)
                }
            }
            layoutManager = GridLayoutManager(requireContext(), 2)
            addItemDecoration(ListDecoration())
        }
    }

    private fun getImageList(
        page: Int = 0,
        limit: Int = 30,
    ) {
        lifecycleScope.launch(Dispatchers.IO) {
            val imageList = RetrofitClient
                .galleryApi
                .requestImageList()
            withContext(Dispatchers.Main) {
                (binding.gridListRecyclerview.adapter as? ListAdapter)
                    ?.initList(imageList)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {

        fun newInstance(): ListFragment {

            val fragment = ListFragment()
            fragment.arguments = arguments()
            return fragment
        }

        private fun arguments(): Bundle {
            return Bundle()
        }
    }
}