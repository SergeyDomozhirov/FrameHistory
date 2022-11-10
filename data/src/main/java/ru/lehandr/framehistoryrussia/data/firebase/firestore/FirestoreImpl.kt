package ru.lehandr.framehistoryrussia.data.firebase.firestore

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import ru.lehandr.data.BuildConfig
import ru.lehandr.domain.model.EpochsModel
import ru.lehandr.domain.setting.env.Environment
import ru.lehandr.framehistoryrussia.data.firebase.firestore.models.EpochsModelData
import javax.inject.Inject

class FirestoreImpl @Inject constructor(private val db: FirebaseFirestore,
                                        private val env: Environment.Companion) : Firestore {

    private val epochListFlow = MutableSharedFlow<List<EpochsModelData>>()

    override fun initListEpochs() {
        val epochsList = ArrayList<EpochsModelData>()
        val docRef = db.collection("language").document("rus").collection("platform")
            .document("mobile").collection("section").document("epochs").collection("icons")

        docRef.get().addOnSuccessListener { result ->
            for (i in 0 until result.size()) {
                docRef.document(result.documents[i].id).get().addOnSuccessListener { documentSnapshot ->
                    documentSnapshot.toObject<EpochsModelData>()?.let {
                        epochsList.add(EpochsModelData(id = it.id, imageURL = it.imageURL, fullPath = documentSnapshot.reference.path))
                        if (epochsList.size == result.size()) {
                            MainScope().launch {
                                epochListFlow.emit(epochsList)
                            }
                        }
                    }
                }
            }
        }
            .addOnFailureListener {
                if (BuildConfig.DEBUG) {
                    Log.e(env.ARG_ERROR, "Error getting documents - ${it.localizedMessage}")
                }
            }
    }

    override fun getEpochListFlow(): Flow<List<EpochsModelData>> {
        initListEpochs()
        return epochListFlow
    }


}