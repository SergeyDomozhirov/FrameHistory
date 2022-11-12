package ru.lehandr.framehistoryrussia.data.firebase.storage

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class FireBaseStorageImpl() : FireBaseStorage {

    private val imageUriFlow = MutableSharedFlow<Uri>()

    private val storage: FirebaseStorage = Firebase.storage

    override fun loadImageByURL(imageURL: String) {
       storage.getReferenceFromUrl(imageURL).downloadUrl.addOnSuccessListener { uri ->
           MainScope().launch {
               imageUriFlow.emit(uri)
           }
       }
   }

    override fun getImageUriFlow(): Flow<Uri> {
       return imageUriFlow
   }

}