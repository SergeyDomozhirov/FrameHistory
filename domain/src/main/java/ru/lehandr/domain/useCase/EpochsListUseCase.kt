package ru.lehandr.domain.useCase

import ru.lehandr.domain.model.EpochsModel
import ru.lehandr.domain.repository.FireBaseRepository

class EpochsListUseCase(private val repository: FireBaseRepository) {

    fun execute(): ArrayList<EpochsModel> = repository.getListEpochs()

}