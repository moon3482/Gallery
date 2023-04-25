package com.charlie.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import com.charlie.gallery.databinding.FragmentListBinding
import com.charlie.gallery.model.ImageData
import com.charlie.gallery.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "ListFragment binding iS Null"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getImageList()
    }

    private fun initView() {
        with(binding) {
            gridListRecyclerview.apply {
                adapter = ListAdapter { previousId, currentId, nextId ->
                    parentFragmentManager.commit {
                        add(
                            R.id.fragment_container,
                            DetailFragment.newInstance(
                                previousId = previousId,
                                currentId = currentId,
                                nextId = nextId
                            )
                        )
                        addToBackStack(null)
                    }
                }
                layoutManager = GridLayoutManager(requireContext(), 2)
            }
        }
    }

    private fun getImageList(
        page: Int = 1,
        limit: Int = 30
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
                    (binding.gridListRecyclerview.adapter as? ListAdapter)?.initList(imageList)
                } ?: run {
                    (binding.gridListRecyclerview.adapter as? ListAdapter)?.initList(emptyList())
                }
            }

            override fun onFailure(call: Call<List<ImageData>>, t: Throwable) = Unit
        })
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        fun newInstance(): ListFragment {
            val args = Bundle()

            val fragment = ListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}