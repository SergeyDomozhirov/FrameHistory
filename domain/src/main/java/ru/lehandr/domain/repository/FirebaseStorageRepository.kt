package ru.lehandr.domain.repository


import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface FirebaseStorageRepository {

    fun loadImageFromStorage(imageURL: String)
    fun getImageUriFlow(): Flow<Uri>

}