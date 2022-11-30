package ru.lehandr.domain.repository

import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface FirebaseStorageRepository {

    fun loadImageFromStorage(imageURL: String): Flow<Uri>

    fun loadCoverFromStorage(coverUrl: String): Flow<String>

}