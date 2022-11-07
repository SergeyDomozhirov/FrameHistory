package ru.lehandr.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.lehandr.domain.model.EpochsModel

interface FireBaseRepository {

    suspend fun getListEpochs(): Flow<List<EpochsModel>>

}