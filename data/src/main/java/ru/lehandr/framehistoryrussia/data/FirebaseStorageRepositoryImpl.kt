package ru.lehandr.framehistoryrussia.data

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import ru.lehandr.domain.repository.FirebaseStorageRepository
import ru.lehandr.framehistoryrussia.data.firebase.storage.FireBaseStorage
import javax.inject.Inject

class FirebaseStorageRepositoryImpl @Inject constructor (private val storage : FireBaseStorage) : FirebaseStorageRepository {

    override fun loadImageFromStorage(imageURL: String) {
        storage.loadImageByURL(imageURL)
    }

    override fun getImageUriFlow(): Flow<Uri> {
        return storage.getImageUriFlow()
    }


}