package com.charlie.domain.usecase

import com.charlie.data.repository.GalleryRepository
import com.charlie.domain.model.ImageModel
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@Reusable
class GetImageUseCase @Inject constructor(
    private val galleryRepository: GalleryRepository,
) {
    operator fun invoke(id: Int): Flow<ImageModel?> {
        return galleryRepository
            .getImage(id = id)
            .map { imageDataModel ->
                imageDataModel?.let { ImageModel(it) }
            }
    }
}
