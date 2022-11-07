package ru.lehandr.framehistoryrussia.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.lehandr.domain.model.EpochsModel
import ru.lehandr.domain.repository.FireBaseRepository
import javax.inject.Inject

class FireBaseRepositoryImpl @Inject constructor(private val db: FirebaseFirestore) : FireBaseRepository {

    override suspend fun getListEpochs(): List<EpochsModel> {
        val epochsList = ArrayList<EpochsModel>()
        val docRef = db.collection("language").document("rus").collection("platform")
            .document("mobile").collection("section").document("epochs").collection("icons")


        /*  return flow<List<EpochsModel>> {
              emit(epochsList)
          }*/

            MainScope().launch {
                val deffered = async {
                    docRef.get().addOnSuccessListener { result ->
                        for (i in 0 until result.size()) {
                            docRef.document(result.documents[i].id).get().addOnSuccessListener { documentSnapshot ->
                                documentSnapshot.toObject<EpochsModel>()?.let {
                                    epochsList.add(EpochsModel(id = it.id, imageURL = it.imageURL, fullPath = documentSnapshot.reference.path))
                                    if (epochsList.size == result.size()) {

                                    }
                                }
                            }
                        }
                    }
                }

            }

            /*   .addOnFailureListener { exception ->
//                Log.w(TAG, "Error getting documents.", exception)
               }
       emit(epochsList)*/
        }
    }

}