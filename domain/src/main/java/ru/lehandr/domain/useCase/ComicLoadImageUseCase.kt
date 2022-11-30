package ru.lehandr.domain.useCase

import kotlinx.coroutines.flow.Flow
import ru.lehandr.domain.repository.FirebaseStorageRepository
import javax.inject.Inject

class ComicLoadImageUseCase @Inject constructor(private val repository: FirebaseStorageRepository) {

    fun execute(coverURL: String): Flow<String> {
    return repository.loadCoverFromStorage(coverURL)
    }
}