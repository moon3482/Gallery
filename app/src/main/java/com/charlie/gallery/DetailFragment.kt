package com.charlie.gallery

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.charlie.gallery.databinding.FragmentDetailBinding
import com.charlie.gallery.model.ImageDataDto

class DetailFragment : Fragment() {

    private val binding by lazy { FragmentDetailBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val data = arguments?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable("imageDataDto", ImageDataDto::class.java)
            } else {
                it.getParcelable("imageDataDto")
            }
        }

        data?.let {
            Glide.with(this)
                .load(it.downloadUrl)
                .into(binding.detailImageView)

            binding.authorNameTextview.text = it.author
            binding.widthSizeTextview.text = it.width.toString()
            binding.heightSizeTextview.text = it.height.toString()
            binding.urlLinkTextview.text = it.url
        }

        return binding.root
    }
}