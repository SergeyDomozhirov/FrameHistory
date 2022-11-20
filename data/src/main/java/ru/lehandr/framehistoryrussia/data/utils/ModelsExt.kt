package ru.lehandr.framehistoryrussia.data.utils

import ru.lehandr.domain.model.EpochsModel
import ru.lehandr.framehistoryrussia.data.firebase.firestore.models.EpochsModelData

fun EpochsModelData.toModelDomain(): EpochsModel {
    return EpochsModel(id = this.id, imageURL = this.imageURL, fullPath = this.fullPath)
}