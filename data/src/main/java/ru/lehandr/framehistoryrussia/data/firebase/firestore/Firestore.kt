package ru.lehandr.framehistoryrussia.data.firebase.firestore

import kotlinx.coroutines.flow.Flow
import ru.lehandr.framehistoryrussia.data.firebase.firestore.models.EpochsModelData

interface Firestore {

    fun getEpochListFlow(): Flow<List<EpochsModelData>>

}