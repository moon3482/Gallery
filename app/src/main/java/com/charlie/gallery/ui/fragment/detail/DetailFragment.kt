package com.charlie.gallery.ui.fragment.detail

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.charlie.gallery.R
import com.charlie.gallery.databinding.FragmentDetailBinding
import com.charlie.gallery.model.ImageDetailData
import com.charlie.gallery.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding
        get() = checkNotNull(_binding) {
            "_binding is Null"
        }

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
    }

    private fun initView() {
        arguments?.let { bundle ->
            val currentId = getCurrentId(bundle)
            setScreen(currentId)
        }
    }

    private fun setScreen(id: Int) {
        fun setImage(
            imageDetailData: ImageDetailData,
            imageView: ImageView,
        ) {
            Glide.with(this)
                .load(imageDetailData.downloadUrl)
                .error(R.drawable.close)
                .into(imageView)
        }

        fun clearView(imageView: ImageView) {
            Glide
                .with(this@DetailFragment)
                .clear(imageView)
        }

        with(binding) {
            clearView(currentImageView)
            clearView(previousImageView)
            clearView(nextImageView)
            requestImageData(id) {
                setDetail(it)
                setImage(it, currentImageView)
            }

            requestImageData(id - 1) {
                setImage(it, previousImageView)
            }
            requestImageData(id + 1) {
                setImage(it, nextImageView)
            }
        }
    }

    private fun requestImageData(
        id: Int,
        onSuccess: (ImageDetailData) -> Unit,
    ) {
        if (id < 0 || id > 29)
            return

        lifecycleScope.launch {
            val imageData = runCatching {
                RetrofitClient
                    .galleryApi
                    .requestImage(id)
                    .await()
            }
            imageData
                .onSuccess {
                    withContext(Dispatchers.Main) {
                        onSuccess(it)
                    }
                }
        }
    }

    private fun setDetail(
        imageDetailData: ImageDetailData,
    ) {
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
            urlLinkTextview.setOnClickListener {
                startActivity(
                    android.content.Intent(
                        android.content.Intent.ACTION_VIEW,
                        android.net.Uri.parse(imageDetailData.url),
                    )
                )
            }

            previousFloatingButton.setOnClickListener {
                if (imageDetailData.id < 1)
                    return@setOnClickListener
                setScreen(imageDetailData.id - 1)
            }
            nextFloatingButton.setOnClickListener {
                if (imageDetailData.id > 28)
                    return@setOnClickListener
                setScreen(imageDetailData.id + 1)
            }
        }
    }

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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
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
            bundle: Bundle,
        ): Int {
            return bundle.getInt(CURRENT_IMAGE_ID)
        }

    }

}