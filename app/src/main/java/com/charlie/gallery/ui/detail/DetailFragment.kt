package com.charlie.gallery.ui.detail

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.charlie.gallery.R
import com.charlie.gallery.databinding.FragmentDetailBinding
import com.charlie.gallery.model.ImageDetailData
import com.charlie.gallery.model.ImageItemData
import com.charlie.gallery.util.toHyperLinkSpannable

class DetailFragment : Fragment(), DetailContract.View {
    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding
        get() = checkNotNull(_binding) {
            "_binding is Null"
        }

    private lateinit var presenter: DetailPresenter

    //region Lifecycle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        presenter = DetailPresenter(
            view = this,
            model = DetailModel(),
            currentId = getCurrentId(arguments),
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        presenter.start()
        binding.previousFloatingButton.setOnClickListener {
            presenter.onClickPrevious()
        }
        binding.nextFloatingButton.setOnClickListener {
            presenter.onClickNext()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
    //endregion

    //region DetailContract.View
    override fun showLoading() {
        binding.detailProgressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.detailProgressBar.visibility = View.GONE
    }

    override fun showDetail(imageDetailData: ImageDetailData?) {
        with(binding) {
            Glide.with(this@DetailFragment)
                .load(imageDetailData?.downloadUrl ?: R.drawable.close)
                .placeholder(R.drawable.loading)
                .error(R.drawable.close)
                .into(detailImageView)

            authorNameTextview.text = imageDetailData?.author
            widthSizeTextview.text = imageDetailData?.width?.toString().orEmpty()
            heightSizeTextview.text = imageDetailData?.height?.toString().orEmpty()
            urlLinkTextview.text = imageDetailData?.url.toHyperLinkSpannable()
            urlLinkTextview.setOnClickListener {
                imageDetailData?.let {
                    presenter.onClickUrl(it.url)
                }
            }
        }
    }

    override fun showCurrentPreview(imageItemData: ImageItemData?) {
        setImage(
            imageItemData = imageItemData,
            imageView = binding.currentImageView,
        )
    }

    override fun showPreviousPreview(imageItemData: ImageItemData?) {
        setImage(
            imageItemData = imageItemData,
            imageView = binding.previousImageView,
        )
    }

    override fun showNextPreview(imageItemData: ImageItemData?) {
        setImage(
            imageItemData = imageItemData,
            imageView = binding.nextImageView,
        )
    }

    override fun enablePreviousButton() {
        binding.previousFloatingButton.isEnabled = true
    }

    override fun disablePreviousButton() {
        binding.previousFloatingButton.isEnabled = false
    }

    override fun moveWebView(url: String) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url),
            )
        )
    }

    override fun exit() {
        AlertDialog.Builder(requireContext())
            .setTitle(resources.getString(R.string.notification))
            .setMessage(resources.getString(R.string.can_not_show_you_image))
            .setPositiveButton(resources.getString(R.string.return_to_page)) { _, _ ->
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            .setCancelable(false)
            .show()
    }

    private fun setImage(
        imageItemData: ImageItemData?,
        imageView: ImageView,
    ) {
        Glide.with(this)
            .load(imageItemData?.downloadUrl)
            .into(imageView)
    }

    //endregion


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
