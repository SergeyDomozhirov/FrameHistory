package ru.lehandr.domain.useCase

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import ru.lehandr.domain.repository.FirebaseStorageRepository
import javax.inject.Inject

class LoadImageUseCase @Inject constructor (private val repository: FirebaseStorageRepository) {

    fun execute(imageURL: String): Flow<Uri> {
        return repository.loadImageFromStorage(imageURL)
    }

}
