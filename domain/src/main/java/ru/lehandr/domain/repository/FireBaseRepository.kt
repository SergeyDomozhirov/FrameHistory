package ru.lehandr.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.lehandr.domain.model.ComicModel
import ru.lehandr.domain.model.EpochsModel

interface FireBaseRepository {

    fun getEpochListFlow(): Flow<List<EpochsModel>>

    fun getComicsListFlow(url: String): Flow<List<ComicModel>>

}