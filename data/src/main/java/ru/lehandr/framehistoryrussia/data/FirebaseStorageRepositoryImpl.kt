package ru.lehandr.framehistoryrussia.data

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import ru.lehandr.domain.repository.FirebaseStorageRepository
import javax.inject.Inject

class FirebaseStorageRepositoryImpl @Inject constructor (private val storage : FirebaseStorage) : FirebaseStorageRepository {

    override fun loadImageFromStorage(): Flow<Uri> {
        TODO("Not yet implemented")
    }


}