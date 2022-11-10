package ru.lehandr.domain.useCase

import ru.lehandr.domain.repository.FirebaseStorageRepository

class EpochLoadImageUseCase(private val repository: FirebaseStorageRepository) {

    fun execute() = repository.loadImageFromStorage()

}