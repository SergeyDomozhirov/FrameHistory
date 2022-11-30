package ru.lehandr.domain.useCase

import ru.lehandr.domain.repository.FireBaseRepository

class ComicsListUseCase(private val repository: FireBaseRepository) {

    fun execute() = repository.getComicsListFlow()
}