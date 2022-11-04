package ru.lehandr.framehistoryrussia.epochs.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import ru.lehandr.framehistoryrussia.R
import ru.lehandr.framehistoryrussia.databinding.FragmentEpochsBinding
import ru.lehandr.framehistoryrussia.model.ComicModel
import ru.lehandr.framehistoryrussia.model.EpochsModel

class EpochsFragment : Fragment(), EpochsAdapter.Listener {

    private var _binding: FragmentEpochsBinding? = null
    private val binding get() = _binding!!
    private val db = Firebase.firestore
    private lateinit var navController: NavController
    private val TAG = "KAN"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentEpochsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()
        initRecyclerServices()
    }

    private fun initRecyclerServices() {
        val docRef = db.collection("language").document("rus").collection("platform")
            .document("mobile").collection("section").document("epochs").collection("icons")
        docRef.get().addOnSuccessListener { result ->
            val epochsList = ArrayList<EpochsModel>()
            for (i in 0 until result.size()) {
                docRef.document(result.documents[i].id).get().addOnSuccessListener { documentSnapshot ->
                    documentSnapshot.toObject<EpochsModel>()?.let {
                        epochsList.add(EpochsModel(id = it.id, imageURL = it.imageURL, fullPath = documentSnapshot.reference.path ))
                        if (epochsList.size == result.size()) {
                            val adapter = EpochsAdapter(epochsList.sortedBy { it.id }, this)
                            binding.epochsRv.layoutManager = LinearLayoutManager(requireContext())
                            binding.epochsRv.adapter = adapter
                        }
                    }
                }
            }
        }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    override fun onClick(uri: String) {
        db.collection("$uri/mobile").get().addOnSuccessListener { result ->
            val comicsList = ArrayList<ComicModel>()
            for (i in 0 until result.size()) {
                db.collection("$uri/mobile").document(result.documents[i].id).get().addOnSuccessListener { documentSnapshot ->
                    documentSnapshot.toObject<ComicModel>()?.let {
                        comicsList.add(ComicModel(id = it.id, imageURL = it.imageURL, title = it.title))
                        if (comicsList.size == result.size()) {
                            Toast.makeText(requireContext(), "картинка номер $uri", Toast.LENGTH_SHORT).show()
                            /*val adapter = EpochsAdapter(epochsList.sortedByDescending { it.id }, this)
                            binding.epochsRv.layoutManager = LinearLayoutManager(requireContext())
                            binding.epochsRv.adapter = adapter*/
                        }
                    }
                }
            }
        }
    }
}