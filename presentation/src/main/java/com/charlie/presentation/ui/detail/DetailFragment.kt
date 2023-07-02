package com.charlie.presentation.ui.detail

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.charlie.presentation.R
import com.charlie.presentation.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment(), DetailUIEvent {

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
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = detailViewModel
        binding.event = this
        onObserveData()
    }

    private fun initView() {
        binding.previousFloatingButton.setOnClickListener {
            detailViewModel.onClickPrevious()
        }
        binding.nextFloatingButton.setOnClickListener {
            detailViewModel.onClickNext()
        }
    }

    private fun onObserveData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailViewModel.detailUiState.collect {
                    when (it) {
                        DetailUiState.Fail -> {
                            AlertDialog.Builder(requireContext())
                                .setTitle(resources.getString(R.string.notification))
                                .setMessage(resources.getString(R.string.can_not_show_you_image))
                                .setPositiveButton(resources.getString(R.string.return_to_page)) { _, _ ->
                                    requireActivity().onBackPressedDispatcher.onBackPressed()
                                }
                                .setCancelable(false)
                                .show()
                        }

                        DetailUiState.None,
                        DetailUiState.Loading,
                        DetailUiState.Success -> Unit
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
    //endregion

    override fun onClickUrl(url: String?) {
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
