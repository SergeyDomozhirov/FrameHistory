package ru.lehandr.framehistoryrussia.data.firebase.firestore

import kotlinx.coroutines.flow.Flow
import ru.lehandr.framehistoryrussia.data.firebase.firestore.models.EpochsModelData

interface Firestore {

    fun initListEpochs()
    fun getEpochListFlow(): Flow<List<EpochsModelData>>

}