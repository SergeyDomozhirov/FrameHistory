package ru.lehandr.framehistoryrussia.data.utils

import ru.lehandr.domain.model.ComicModel
import ru.lehandr.domain.model.EpochsModel
import ru.lehandr.framehistoryrussia.data.firebase.firestore.models.ComicsModelData
import ru.lehandr.framehistoryrussia.data.firebase.firestore.models.EpochsModelData

fun EpochsModelData.toEpochModelDomain(): EpochsModel {
    return EpochsModel(id = this.id, imageURL = this.imageURL, fullPath = this.fullPath)
}

val ComicsModelData.toComicsModelDomain: ComicModel
    get() {
        return ComicModel(id = this.id, coverURL = this.coverURL, fullPath = this.fullPath, title = this.title)
    }