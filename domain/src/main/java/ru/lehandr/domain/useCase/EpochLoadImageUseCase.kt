package ru.lehandr.domain.useCase

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import ru.lehandr.domain.repository.FirebaseStorageRepository

class EpochLoadImageUseCase(private val repository: FirebaseStorageRepository) {

    fun execute(imageURL: String) {
        repository.loadImageFromStorage(imageURL)
    }

    fun execute(): Flow<Uri> {
        return repository.getImageUriFlow()
    }

}
