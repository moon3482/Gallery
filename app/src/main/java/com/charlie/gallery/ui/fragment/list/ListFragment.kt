package com.charlie.gallery.ui.fragment.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.charlie.gallery.R
import com.charlie.gallery.databinding.FragmentListBinding
import com.charlie.gallery.model.ImageItemData
import com.charlie.gallery.ui.fragment.detail.DetailFragment
import com.charlie.gallery.ui.fragment.list.adapter.ListAdapter
import com.charlie.gallery.ui.fragment.list.adapter.ListDecoration

class ListFragment : Fragment(), ListContract.View {

    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding
        get() = checkNotNull(_binding) {
            "_binding iS Null"
        }
    private lateinit var presenter: ListContract.Presenter

    // region Lifecycle
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentListBinding.inflate(layoutInflater)
        presenter = ListPresenter(this, ListModel())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        presenter.start()
    }

    private fun initView() {
        binding.gridListRecyclerview.apply {
            adapter = ListAdapter { currentId ->
                presenter.onClickItem(currentId)
            }
            addItemDecoration(ListDecoration(10, 8))
        }
        binding.reloadButton.setOnClickListener {
            presenter.onClickReload()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    // endregion

    //region ListContractor.View
    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    override fun showList(imageItemDataList: List<ImageItemData>) {
        (binding.gridListRecyclerview.adapter as? ListAdapter)?.initList(imageItemDataList)
    }

    override fun showDetailFragment(currentId: Int) {
        parentFragmentManager.commit {
            add<DetailFragment>(
                R.id.fragment_container,
                args = DetailFragment.arguments(currentId)
            )
            addToBackStack(null)
        }
    }

    override fun showLoadingFailed() {
        binding.failedLoadingLayout.visibility = View.VISIBLE
    }

    override fun hiedLoadingFailed() {
        binding.failedLoadingLayout.visibility = View.GONE
    }

    override fun showFailedToast() {
        Toast.makeText(requireContext(), "이미지를 불러오는데\n실패 하였습니다.", Toast.LENGTH_SHORT).show()
    }

    //endregion

    companion object {

        fun newInstance(): ListFragment {

            val fragment = ListFragment()
            fragment.arguments = arguments()
            return fragment
        }

        private fun arguments(): Bundle {
            return Bundle()
        }
    }
}
