package ru.lehandr.framehistoryrussia.data.firebase.storage

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FireStorageImpl(private val storage: FirebaseStorage) : FireStorage {
// TODO
    override fun loadImageByURL(imageURL: String): Flow<Uri> {
        return callbackFlow {
            val storageUri = storage.getReferenceFromUrl(imageURL).downloadUrl.addOnSuccessListener { uri ->
                trySend(uri)
            }
            awaitClose {
                storageUri
            }
        }
   }
}