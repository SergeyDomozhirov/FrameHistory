package ru.lehandr.framehistoryrussia.data.firebase.storage

import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface FireStorage {
 // TODO imageURL
    fun loadImageByURL(imageURL: String): Flow<Uri>

}
