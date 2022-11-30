package ru.lehandr.framehistoryrussia.data

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import ru.lehandr.domain.repository.FirebaseStorageRepository
import ru.lehandr.framehistoryrussia.data.firebase.storage.FireStorage
import javax.inject.Inject

class FirebaseStorageRepositoryImpl @Inject constructor(private val storage: FireStorage) : FirebaseStorageRepository {

//TODO
    override fun loadImageFromStorage(imageURL: String): Flow<Uri> {
        return storage.loadImageByURL(imageURL)
    }

    override fun loadCoverFromStorage(coverUrl: String): Flow<String> {
        TODO("Not yet implemented")
    }

}