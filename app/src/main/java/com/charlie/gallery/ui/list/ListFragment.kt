package com.charlie.gallery.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.charlie.gallery.R
import com.charlie.gallery.databinding.FragmentListBinding
import com.charlie.gallery.ui.detail.DetailFragment
import com.charlie.gallery.ui.list.adapter.ListAdapter
import com.charlie.gallery.ui.list.adapter.ListDecoration
import com.charlie.gallery.util.doOnScrolled
import kotlinx.coroutines.launch

class ListFragment : Fragment(), ListUIEvent {

    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding
        get() = checkNotNull(_binding) {
            "_binding iS Null"
        }
    private val listViewModel: ListViewModel by lazy { ListViewModel(ListModel()) }

    // region Lifecycle
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
        observe()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = listViewModel
        binding.event = this
    }

    private fun initView() {
        binding.gridListRecyclerview.apply {
            adapter = ListAdapter()
            addItemDecoration(ListDecoration(10, 8))
            doOnScrolled { _, _, _ ->
                val layoutManager = binding.gridListRecyclerview.layoutManager as? LinearLayoutManager
                layoutManager?.let {
                    if (it.findLastVisibleItemPosition() == it.itemCount - 1) {
                        listViewModel.onNextPage()
                    }
                }
            }
        }
        binding.reloadButton.setOnClickListener {
            listViewModel.onReload()
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                listViewModel.uiState.collect {
                    when (it) {
                        is ListUIState.Fail -> {
                            Toast
                                .makeText(
                                    requireContext(),
                                    resources.getString(R.string.failed_load_image_list),
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }

                        ListUIState.None,
                        ListUIState.Success,
                        ListUIState.Loading -> Unit
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    // endregion

    override fun onClickItem(position: Int) {
        parentFragmentManager.commit {
            add<DetailFragment>(
                R.id.fragment_container, args = DetailFragment.arguments(position)
            )
            addToBackStack(null)
        }
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
