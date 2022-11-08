package ru.lehandr.domain.useCase

import kotlinx.coroutines.flow.Flow
import ru.lehandr.domain.model.EpochsModel
import ru.lehandr.domain.repository.FireBaseRepository

class EpochsListUseCase(private val repository: FireBaseRepository) {

    fun execute() = repository.getEpochListFlow()



}