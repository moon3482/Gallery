package com.charlie.presentation.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charlie.domain.usecase.GetImageListUseCase
import com.charlie.presentation.model.ListItemUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getImageListUseCase: GetImageListUseCase,
) : ViewModel() {
    private var page = 1

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _imageList: MutableLiveData<List<ListItemUiModel>> = MutableLiveData()
    val imageList: LiveData<List<ListItemUiModel>>
        get() = _imageList

    init {
        loadList()
    }

    fun loadNextPage() {
        page++
        loadList()
    }

    fun reloadList() {
        page = 1
        _imageList.value = emptyList()
        loadList()
    }

    private fun loadList() {
        getImageListUseCase(page)
            .onStart { _isLoading.value = true }
            .map { imageModelList ->
                imageModelList.map { imageModel -> ListItemUiModel(imageModel) }
            }
            .onEach { listItemUiModel ->
                _imageList.value = _imageList.value?.plus(listItemUiModel) ?: listItemUiModel
            }
            .onCompletion {
                _isLoading.value = false
            }
            .launchIn(viewModelScope)
    }
}
