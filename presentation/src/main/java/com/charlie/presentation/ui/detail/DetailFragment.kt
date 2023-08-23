package com.charlie.presentation.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.charlie.presentation.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(), DetailUiEvent {
    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding
        get() = checkNotNull(_binding) {
            "_binding is Null"
        }
    private val detailViewModel: DetailViewModel by viewModels()

    //region Lifecycle
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater)
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
            vm = detailViewModel
            event = this@DetailFragment
            previousFloatingButton.setOnClickListener {
                detailViewModel.loadPrevious()
            }
            nextFloatingButton.setOnClickListener {
                detailViewModel.loadNext()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    //endregion

    override fun onClickUrl(
        url: String?,
    ) {
        url?.let {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(url),
                )
            )
        }
    }

    companion object {
        fun arguments(
            currentId: Int,
        ): Bundle {
            return Bundle().apply {
                putInt(DetailViewModel.CURRENT_IMAGE_ID, currentId)
            }
        }
    }
}
