package com.charlie.presentation.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.charlie.presentation.R
import com.charlie.presentation.databinding.FragmentListBinding
import com.charlie.presentation.ui.detail.DetailFragment
import com.charlie.presentation.ui.list.adapter.ListAdapter
import com.charlie.presentation.ui.list.adapter.ListDecoration
import com.charlie.presentation.util.doOnScrolled
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : Fragment(), ListUIEvent {
    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding
        get() = checkNotNull(_binding) {
            "_binding is Null"
        }
    private val listViewModel: ListViewModel by viewModels()

    // region Lifecycle
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        onObserveData()
    }

    private fun initView() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            vm = listViewModel
            event = this@ListFragment
            gridListRecyclerview.apply {
                adapter = ListAdapter()
                addItemDecoration(ListDecoration(10, 8))
                doOnScrolled { _, _, _ ->
                    val layoutManager = gridListRecyclerview.layoutManager as? GridLayoutManager
                    layoutManager?.let {
                        if (it.findLastCompletelyVisibleItemPosition() == it.itemCount - 1) {
                            listViewModel.onNextPage()
                        }
                    }
                }
            }
            reloadButton.setOnClickListener {
                listViewModel.onReload()
            }
        }
    }

    private fun onObserveData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
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
                        ListUIState.Loading,
                        -> Unit
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

    override fun onClickItem(id: Int) {
        parentFragmentManager.commit {
            add<DetailFragment>(
                R.id.fragment_container, args = DetailFragment.arguments(id)
            )
            addToBackStack(null)
        }
    }
}
