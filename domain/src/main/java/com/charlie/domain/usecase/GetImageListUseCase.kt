package com.charlie.domain.usecase

import com.charlie.data.repository.GalleryRepository
import com.charlie.domain.model.ImageModel
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@Reusable
class GetImageListUseCase @Inject constructor(
    private val galleryRepository: GalleryRepository,
) {
    operator fun invoke(page: Int): Flow<List<ImageModel>> {
        return galleryRepository
            .getImageList(page = page)
            .map { imageDataModelList ->
                imageDataModelList.map { imageDataModel -> ImageModel(imageDataModel) }
            }
    }
}
