package com.charlie.gallery.ui.detail

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.UnderlineSpan
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
        presenter = DetailPresenter(view = this, model = DetailModel(), getCurrentId(arguments))
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

    override fun showSuccessDetail(imageDetailData: ImageDetailData) {
        with(binding) {
            Glide.with(this@DetailFragment)
                .load(imageDetailData.downloadUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.close)
                .into(detailImageView)

            authorNameTextview.text = imageDetailData.author
            widthSizeTextview.text = imageDetailData.width.toString()
            heightSizeTextview.text = imageDetailData.height.toString()
            urlLinkTextview.text = imageDetailData.url.toHyperLinkSpannable()
        }
    }

    override fun showFailedDetail() {
        with(binding) {
            Glide.with(this@DetailFragment)
                .load(R.drawable.close)
                .placeholder(R.drawable.loading)
                .into(detailImageView)
            authorNameTextview.text = ""
            widthSizeTextview.text = ""
            heightSizeTextview.text = ""
            urlLinkTextview.text = ""
        }
    }

    override fun showCurrentPreview(imageItemData: ImageItemData) {
        setImage(
            imageItemData = imageItemData,
            imageView = binding.currentImageView,
        )
    }

    override fun clearCurrentPreview() {
        binding.currentImageView.setImageBitmap(null)
    }

    override fun showPreviousPreview(imageItemData: ImageItemData) {
        setImage(
            imageItemData = imageItemData,
            imageView = binding.previousImageView,
        )
    }

    override fun clearPreviousPreview() {
        binding.previousImageView.setImageBitmap(null)
    }

    override fun showNextPreview(imageItemData: ImageItemData) {
        setImage(
            imageItemData = imageItemData,
            imageView = binding.nextImageView,
        )
    }

    override fun clearNextPreview() {
        binding.nextImageView.setImageBitmap(null)
    }

    override fun showPreviousButton() {
        binding.previousFloatingButton.visibility = View.VISIBLE
    }

    override fun hidePreviousButton() {
        binding.previousFloatingButton.visibility = View.INVISIBLE
    }

    override fun setOnClickUrl(url: String) {
        binding.urlLinkTextview.setOnClickListener {
            presenter.onClickUrl(url = url)
        }
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
            .error(R.drawable.close)
            .into(imageView)
    }

    //endregion

    private fun String?.toHyperLinkSpannable(): SpannableString {
        return SpannableString(
            this.orEmpty()
        ).apply {
            setSpan(
                UnderlineSpan(),
                0,
                this.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
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
