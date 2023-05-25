package com.charlie.gallery.ui.detail

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.charlie.gallery.R
import com.charlie.gallery.databinding.FragmentDetailBinding

class DetailFragment : Fragment(), DetailUIEvent {
    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding
        get() = checkNotNull(_binding) {
            "_binding is Null"
        }

    private val detailViewModel: DetailViewModel by lazy {
        DetailViewModel(
            model = DetailModel(),
            initId = getCurrentId(arguments),
        )
    }

    //region Lifecycle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = detailViewModel
        binding.event = this
    }

    private fun initView() {
        binding.previousFloatingButton.setOnClickListener {
            detailViewModel.onClickPrevious()
        }
        binding.nextFloatingButton.setOnClickListener {
            detailViewModel.onClickNext()
        }
    }

    private fun observe() {
        detailViewModel.onFailedInit.observe(viewLifecycleOwner) {
            AlertDialog.Builder(requireContext())
                .setTitle(resources.getString(R.string.notification))
                .setMessage(resources.getString(R.string.can_not_show_you_image))
                .setPositiveButton(resources.getString(R.string.return_to_page)) { _, _ ->
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
                .setCancelable(false)
                .show()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
    //endregion

    override fun moveWebView(url: String?) {
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

        private const val CURRENT_IMAGE_ID = "currentId"

        fun newInstance(
            currentId: Int,
        ): DetailFragment {
            return DetailFragment().apply {
                arguments = arguments(currentId)
            }
        }

        fun arguments(
            currentId: Int,
        ): Bundle {
            return Bundle().apply {
                putInt(CURRENT_IMAGE_ID, currentId)
            }
        }

        fun getCurrentId(
            bundle: Bundle?,
        ): Int {
            return bundle?.getInt(CURRENT_IMAGE_ID) ?: -1
        }

    }

}
