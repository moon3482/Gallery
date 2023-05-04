package com.charlie.gallery.ui.fragment.detail

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
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.charlie.gallery.R
import com.charlie.gallery.databinding.FragmentDetailBinding
import com.charlie.gallery.model.ImageData
import com.charlie.gallery.network.RetrofitClient
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding
        get() = checkNotNull(_binding) {
            "DetailFragment is Null"
        }
    private var currentId: Int = 0
    private val imageRequestExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, _ -> }

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

    private fun initView() = with(binding) {
        fun requestImageData(
            id: Int,
            onSuccess: (ImageData) -> Unit,
        ) {
            lifecycleScope.launch(Dispatchers.IO + imageRequestExceptionHandler) {
                val imageData = RetrofitClient.galleryApi.requestImage(id)
                withContext(Dispatchers.Main) {
                    onSuccess(imageData)
                }
            }
        }

        fun setImage(
            imageData: ImageData,
            imageView: ImageView,
        ) {
            Glide.with(imageView)
                .load(imageData.downloadUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.close)
                .into(imageView)
        }

        fun setInfo(imageData: ImageData) {
            authorNameTextview.text = imageData.author
            widthSizeTextview.text = imageData.width.toString()
            heightSizeTextview.text = imageData.height.toString()
            urlLinkTextview.text = imageData.url.let {
                SpannableString(it).apply {
                    setSpan(
                        UnderlineSpan(),
                        0,
                        it.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
            urlLinkTextview.setOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(imageData.url),
                    )
                )
            }
        }

        fun setPreview(currentId: Int) {
            this@DetailFragment.currentId = currentId
            requestImageData(currentId) {
                setImage(
                    it,
                    currentImageView,
                )
                setImage(
                    it,
                    detailImageView
                )
                setInfo(it)
            }
            requestImageData(currentId - 1) {
                setImage(
                    it,
                    previousImageView,
                )
            }
            requestImageData(currentId + 1) {
                setImage(
                    it,
                    nextImageView,
                )
            }
        }

        previousFloatingButton.setOnClickListener {
            setPreview(currentId - 1)
        }
        nextFloatingButton.setOnClickListener {
            setPreview(currentId + 1)
        }

        arguments?.let { bundle ->
            val currentId = getCurrentId(bundle)
            setPreview(currentId)
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

        private fun arguments(
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