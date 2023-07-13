package com.charlie.domain.usecase

import com.charlie.data.repository.ImageRepository
import com.charlie.domain.model.ImageModel
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@Reusable
class GetImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
) {
    operator fun invoke(
        id: Int,
    ): Flow<ImageModel> {
        return imageRepository
            .getImage(id = id)
            .map { imageDataModel ->
                ImageModel(imageDataModel)
            }
    }
}
