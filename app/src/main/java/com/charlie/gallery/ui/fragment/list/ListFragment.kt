package com.charlie.gallery.ui.fragment.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.charlie.gallery.R
import com.charlie.gallery.databinding.FragmentListBinding
import com.charlie.gallery.network.RetrofitClient
import com.charlie.gallery.ui.fragment.detail.DetailFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await

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
                    add<DetailFragment>(
                        R.id.fragment_container,
                        args = DetailFragment.arguments(currentId)
                    )
                    addToBackStack(null)
                }
            }
            addItemDecoration(ListDecoration(10, 8))
        }
    }

    private fun getImageList() {

        lifecycleScope.launch {
            val imageList = runCatching {
                RetrofitClient
                    .galleryApi
                    .requestImageList()
                    .await()
            }
            imageList.onSuccess {
                withContext(Dispatchers.Main) {
                    (binding.gridListRecyclerview.adapter as? ListAdapter)?.initList(it)
                }
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