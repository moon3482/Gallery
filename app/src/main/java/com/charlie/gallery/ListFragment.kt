package com.charlie.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.charlie.gallery.databinding.FragmentListBinding
import com.charlie.gallery.exception.ResponseException
import com.charlie.gallery.model.ImageDataDto
import com.charlie.gallery.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ListFragment : Fragment() {

    private val binding by lazy { FragmentListBinding.inflate(layoutInflater) }
    private lateinit var listAdapter: ListAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private var page = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initRecyclerview()
        getImageList(page) { result ->
            result.onSuccess {
                listAdapter.initList(it)
            }.onFailure {
                when (it) {
                    is ResponseException -> {}
                    is IOException -> {}
                    else -> {}
                }
            }
        }
        return binding.root
    }

    override fun onDetach() {
        listAdapter.clearList()
        super.onDetach()
    }

    private fun initRecyclerview() {
        listAdapter = ListAdapter { imageDataDto ->
            requireActivity().supportFragmentManager.beginTransaction()
                .add(
                    R.id.fragment_container,
                    DetailFragment().apply {
                        arguments = Bundle().apply {
                            putParcelable("imageDataDto", imageDataDto)
                        }
                    }
                ).addToBackStack("detail").commit()
        }
        gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.gridListRecyclerview.apply {
            adapter = listAdapter
            layoutManager = gridLayoutManager
        }
        binding.gridListRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (gridLayoutManager.findLastVisibleItemPosition() > listAdapter.itemCount - 10) {
                    page++
                    getImageList(page) { result ->
                        result.onSuccess { imageList ->
                            listAdapter.addList(imageList)
                        }.onFailure {

                        }
                    }
                }
            }
        })
    }

    private fun getImageList(
        page: Int = 0,
        limit: Int = 30,
        callback: (Result<List<ImageDataDto>>) -> Unit
    ) {
        RetrofitClient.galleryApi.requestImageList(
            page = page,
            limit = limit,
        ).enqueue(object : Callback<List<ImageDataDto>> {
            override fun onResponse(
                call: Call<List<ImageDataDto>>,
                response: Response<List<ImageDataDto>>
            ) {
                if (!response.isSuccessful)
                    return

                response.body()?.let { imageList ->
                    callback(Result.success(imageList))
                } ?: callback(Result.failure(ResponseException("Response Body is Null")))
            }

            override fun onFailure(call: Call<List<ImageDataDto>>, t: Throwable) {
                callback(Result.failure(t))
            }
        })

    }
}