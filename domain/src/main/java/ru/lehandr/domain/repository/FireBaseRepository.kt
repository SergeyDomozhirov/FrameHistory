package ru.lehandr.domain.repository

import ru.lehandr.domain.model.EpochsModel

interface FireBaseRepository {

    fun getListEpochs(): ArrayList<EpochsModel>

}