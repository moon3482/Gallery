package com.charlie.gallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.charlie.gallery.databinding.ActivityMainBinding
import com.charlie.gallery.model.ImageDto
import com.charlie.gallery.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        RetrofitClient.galleryApi.getImageList().enqueue(object : Callback<List<ImageDto>> {
            override fun onResponse(
                call: Call<List<ImageDto>>,
                response: Response<List<ImageDto>>
            ) {
                if (response.isSuccessful)
                    return
                response.body()?.let {

                }

            }

            override fun onFailure(call: Call<List<ImageDto>>, t: Throwable) {

            }
        })
    }

    fun setClickListener(){

    }
}