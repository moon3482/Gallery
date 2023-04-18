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
import com.charlie.gallery.model.DetailImageSet
import com.charlie.gallery.model.DetailScreen
import com.charlie.gallery.model.ImageData
import com.charlie.gallery.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListFragment : Fragment() {

    private val binding by lazy { FragmentListBinding.inflate(layoutInflater) }
    private lateinit var listAdapter: ListAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private var page = 1
    private lateinit var detailFragmentCallback: (DetailFragment, DetailScreen) -> Unit
    private lateinit var listAdapterCallback: (Int, ImageData) -> Unit
    private lateinit var detailFragment: DetailFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initCallBack()
        initView()
        getImageList(page) { result ->
            result.onSuccess {
                listAdapter.initList(it)
            }
        }
        return binding.root
    }

    override fun onDetach() {
        listAdapter.clearList()
        super.onDetach()
    }

    private fun initView() {
        detailFragment = DetailFragment(detailFragmentCallback)
        listAdapter = ListAdapter(listAdapterCallback)
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
                        }
                    }
                }
            }
        })
    }

    private fun initCallBack() {
        listAdapterCallback = { index, imageData ->
            requireActivity().supportFragmentManager.beginTransaction()
                .add(
                    R.id.fragment_container,
                    detailFragment.apply {
                        val detailImageSet = listAdapter.getCurrentImageSet(imageData)
                        arguments = createBundle(
                            index = index,
                            imageData = imageData,
                            detailImageSet = detailImageSet
                        )
                    },
                ).addToBackStack(null).commit()
        }

        detailFragmentCallback = { detailFragment, detailScreen ->
            val withCurrentIndexImageData = listAdapter.getDetailImageData(detailScreen)

            detailFragment.setIndex(withCurrentIndexImageData.index)

            if (gridLayoutManager.findLastCompletelyVisibleItemPosition() != withCurrentIndexImageData.index)
                binding.gridListRecyclerview.scrollToPosition(withCurrentIndexImageData.index)

            detailFragment.setImage(withCurrentIndexImageData.imageData) { imageData ->
                val imageSet = listAdapter.getCurrentImageSet(imageData)
                detailFragment.setImageSet(imageSet)
            }
        }
    }

    private fun createBundle(
        index: Int,
        imageData: ImageData,
        detailImageSet: DetailImageSet,
    ): Bundle {
        return Bundle().apply {
            putInt(BundleKey.IMAGE_INDEX, index)
            putParcelable(BundleKey.DETAIL_IMAGE_SET, detailImageSet)
            putParcelable(BundleKey.IMAGE_DATA, imageData)
        }
    }

    private fun getImageList(
        page: Int = 0,
        limit: Int = 30,
        callback: (Result<List<ImageData>>) -> Unit
    ) {
        RetrofitClient.galleryApi.requestImageList(
            page = page,
            limit = limit,
        ).enqueue(object : Callback<List<ImageData>> {
            override fun onResponse(
                call: Call<List<ImageData>>,
                response: Response<List<ImageData>>
            ) {
                if (!response.isSuccessful)
                    return

                response.body()?.let { imageList ->
                    callback(Result.success(imageList))
                } ?: callback(Result.failure(ResponseException("Response Body is Null")))
            }

            override fun onFailure(call: Call<List<ImageData>>, t: Throwable) {
                callback(Result.failure(t))
            }
        })

    }
}