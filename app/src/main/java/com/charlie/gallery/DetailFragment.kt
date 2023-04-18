package com.charlie.gallery

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.charlie.gallery.databinding.FragmentDetailBinding
import com.charlie.gallery.model.DetailImageSet
import com.charlie.gallery.model.DetailScreen
import com.charlie.gallery.model.ImageData

class DetailFragment(
    private val onClickButton: (DetailFragment, DetailScreen) -> Unit,
) : Fragment() {
    private val binding by lazy { FragmentDetailBinding.inflate(layoutInflater) }
    private var imageData: ImageData? = null
    private var detailImageSet: DetailImageSet? = null
    private var currentIndex: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        loadData(savedInstanceState)
        setImage(imageData)
        setImageSet(detailImageSet)
        setOnClickListener()

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(BundleKey.IMAGE_DATA, imageData)
        outState.putParcelable(BundleKey.DETAIL_IMAGE_SET, detailImageSet)
        outState.putInt(BundleKey.IMAGE_INDEX, currentIndex)
    }

    private fun loadData(savedInstanceState: Bundle?) {
        arguments?.let {
            imageData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(BundleKey.IMAGE_DATA, ImageData::class.java)
            } else {
                it.getParcelable(BundleKey.IMAGE_DATA)
            }
            detailImageSet = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(BundleKey.DETAIL_IMAGE_SET, DetailImageSet::class.java)
            } else {
                it.getParcelable(BundleKey.DETAIL_IMAGE_SET)
            }
            currentIndex = it.getInt(BundleKey.IMAGE_INDEX)
        }

        savedInstanceState?.let {
            imageData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(BundleKey.IMAGE_DATA, ImageData::class.java)
            } else {
                it.getParcelable(BundleKey.IMAGE_DATA)
            }
            detailImageSet = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(BundleKey.DETAIL_IMAGE_SET, DetailImageSet::class.java)
            } else {
                it.getParcelable(BundleKey.DETAIL_IMAGE_SET)
            }
            currentIndex = it.getInt(BundleKey.IMAGE_INDEX)
        }
    }

    fun setImage(imageData: ImageData?, result: ((ImageData?) -> Unit)? = null) {
        this.imageData = imageData
        result?.invoke(imageData)
        Glide.with(this)
            .load(imageData?.downloadUrl)
            .thumbnail(
                Glide.with(this)
                    .load(R.drawable.loading)
            )
            .error(R.drawable.close)
            .timeout(1000)
            .into(binding.detailImageView)
        binding.authorNameTextview.text = imageData?.author
        binding.widthSizeTextview.text = imageData?.width.toString()
        binding.heightSizeTextview.text = imageData?.height.toString()
        binding.urlLinkTextview.text = imageData?.url?.let {
            SpannableString(it).apply {
                setSpan(UnderlineSpan(),0,it.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
    }

    fun setImageSet(detailImageSet: DetailImageSet?) {
        Glide.with(this)
            .load(detailImageSet?.previousImageData?.downloadUrl)
            .sizeMultiplier(0.3f)
            .into(binding.previousImageView)
        Glide.with(this)
            .load(detailImageSet?.currentImageData?.downloadUrl)
            .sizeMultiplier(0.3f)
            .into(binding.currentImageView)
        Glide.with(this)
            .load(detailImageSet?.nextImageData?.downloadUrl)
            .sizeMultiplier(0.3f)
            .into(binding.nextImageView)
    }

    private fun setOnClickListener() {
        binding.urlLinkTextview.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                val uri = Uri.parse(imageData?.url)
                data = uri
            }
            requireActivity().startActivity(intent)
        }
        binding.nextFloatingButton.setOnClickListener {
            imageData?.let {
                onClickButton.invoke(this, DetailScreen.OnClickNext(it))
            }

        }
        binding.previousFloatingButton.setOnClickListener {
            imageData?.let {
                if (currentIndex > 0)
                    onClickButton.invoke(this, DetailScreen.OnClickPrevious(it))
            }
        }
    }

    fun setIndex(index: Int) {
        this.currentIndex = index
    }
}