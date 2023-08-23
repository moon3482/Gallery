package com.charlie.presentation.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.charlie.presentation.R
import com.charlie.presentation.databinding.FragmentListBinding
import com.charlie.presentation.ui.detail.DetailFragment
import com.charlie.presentation.ui.list.adapter.ListAdapter
import com.charlie.presentation.ui.list.adapter.ListDecoration
import com.charlie.presentation.util.doOnScrolled
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment(), ListUiEvent {
    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding
        get() = checkNotNull(_binding) {
            "_binding is Null"
        }
    private val listViewModel: ListViewModel by viewModels()

    //region Lifecycle
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
    }

    private fun initView() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            vm = listViewModel
            event = this@ListFragment
            gridListRecyclerview.apply {
                adapter = ListAdapter()
                addItemDecoration(
                    ListDecoration(
                        width = 10,
                        height = 8,
                    )
                )
                doOnScrolled { _, _, _ ->
                    val layoutManager = gridListRecyclerview.layoutManager as? GridLayoutManager
                    layoutManager?.let { layoutManager ->
                        if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.itemCount - 1) {
                            listViewModel.loadNextPage()
                        }
                    }
                }
            }
            reloadButton.setOnClickListener {
                listViewModel.reloadList()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    //endregion

    override fun onClickItem(
        id: Int,
    ) {
        parentFragmentManager.commit {
            add<DetailFragment>(
                containerViewId = R.id.fragment_container,
                args = DetailFragment.arguments(id),
            )
            addToBackStack(null)
        }
    }
}
