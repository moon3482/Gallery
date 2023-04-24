package com.charlie.gallery

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.charlie.gallery.databinding.FragmentDetailBinding
import com.charlie.gallery.model.ImageData
import com.charlie.gallery.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = checkNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            bundle.getInt(BundleKey.PREVIOUS_IMAGE_INDEX, -1).let {
                RetrofitClient
                    .galleryApi
                    .requestImage(it)
                    .enqueue(object : Callback<ImageData> {
                        override fun onResponse(
                            call: Call<ImageData>,
                            response: Response<ImageData>
                        ) {
                            if (!response.isSuccessful)
                                return
                            response.body()?.let { imageData ->
                                Glide.with(this@DetailFragment)
                                    .load(imageData.downloadUrl)
                                    .into(binding.previousImageView)
                            }
                        }

                        override fun onFailure(call: Call<ImageData>, t: Throwable) = Unit
                    })
            }
            bundle.getInt(BundleKey.CURRENT_IMAGE_INDEX).let {
                RetrofitClient
                    .galleryApi
                    .requestImage(it)
                    .enqueue(object : Callback<ImageData> {
                        override fun onResponse(
                            call: Call<ImageData>,
                            response: Response<ImageData>
                        ) {
                            if (!response.isSuccessful)
                                return
                            response.body()?.let { imageData ->
                                setImage(imageData)
                                Glide.with(this@DetailFragment)
                                    .load(imageData.downloadUrl)
                                    .error(R.drawable.close)
                                    .into(binding.currentImageView)
                            }
                        }

                        override fun onFailure(call: Call<ImageData>, t: Throwable) = Unit
                    })
            }
            bundle.getInt(BundleKey.NEXT_IMAGE_INDEX, -1).let {
                RetrofitClient
                    .galleryApi
                    .requestImage(it)
                    .enqueue(object : Callback<ImageData> {
                        override fun onResponse(
                            call: Call<ImageData>,
                            response: Response<ImageData>
                        ) {
                            if (!response.isSuccessful)
                                return
                            response.body()?.let { imageData ->
                                Glide
                                    .with(this@DetailFragment)
                                    .load(imageData.downloadUrl)
                                    .error(R.drawable.close)
                                    .into(binding.nextImageView)
                            }
                        }

                        override fun onFailure(call: Call<ImageData>, t: Throwable) = Unit
                    })
            }

        }
    }

    fun setImage(imageData: ImageData) {
        Glide.with(this)
            .load(imageData.downloadUrl)
            .placeholder(R.drawable.loading)
            .error(R.drawable.close)
            .into(binding.detailImageView)

        binding.authorNameTextview.text = imageData.author
        binding.widthSizeTextview.text = imageData.width.toString()
        binding.heightSizeTextview.text = imageData.height.toString()
        binding.urlLinkTextview.text = imageData.url.let {
            SpannableString(it).apply {
                setSpan(
                    UnderlineSpan(),
                    0,
                    it.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
    }

    companion object {
        fun newInstance(
            previousId: Int?,
            currentId: Int,
            nextId: Int?,
        ): DetailFragment {
            val args = Bundle().apply {
                previousId?.let {
                    putInt(BundleKey.PREVIOUS_IMAGE_INDEX, it)
                }
                putInt(BundleKey.CURRENT_IMAGE_INDEX, currentId)
                nextId?.let {
                    putInt(BundleKey.NEXT_IMAGE_INDEX, it)
                }
            }

            val fragment = DetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

}