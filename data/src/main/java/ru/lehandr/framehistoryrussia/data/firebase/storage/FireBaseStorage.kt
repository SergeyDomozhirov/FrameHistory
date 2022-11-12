package ru.lehandr.framehistoryrussia.data.firebase.storage

import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface FireBaseStorage {

    fun loadImageByURL(imageURL: String)
    fun getImageUriFlow(): Flow<Uri>

}
