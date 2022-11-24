package ru.lehandr.framehistoryrussia.epochs.comics

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.comics_image.*
import kotlinx.android.synthetic.main.comics_image.view.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import ru.lehandr.data.BuildConfig
import ru.lehandr.framehistoryrussia.data.firebase.firestore.models.ComicsModelData
import ru.lehandr.framehistoryrussia.databinding.FragmentComicsBinding

const val ARG_URL_EPOCH = "arg_url_epoch"

class ComicsFragment : Fragment() {

    val db = Firebase.firestore
    private var urlEpoch: String? = null
    private var _binding: FragmentComicsBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            urlEpoch = it.getString(ARG_URL_EPOCH)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentComicsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()

        if (urlEpoch != null) {
            val comicsList = ArrayList<ComicsModelData>()
            val docRef = db.collection(urlEpoch!!)

            val docRefListener = docRef.get().addOnSuccessListener { result ->
                for (i in 0 until result.size()) {
                    docRef.document(result.documents[i].id).get().addOnSuccessListener { documentSnapshot ->
                        documentSnapshot.toObject<ComicsModelData>()?.let { modelData ->
                            comicsList.add(
                                ComicsModelData(
                                    id = modelData.id, imageURL = modelData.imageURL,
                                    fullPath = documentSnapshot.reference.path + modelData.fullPath
                                )
                            )
                            if (comicsList.size == result.size()) {
                                MainScope().launch {
//                                    trySend(comicsList)
                                    val adapter = ComicsAdapter(comicsList)
                                    binding.comicsRv.adapter = adapter
                                }
                            }
                        }
                    }
                }
            }
                .addOnFailureListener {
                    if (BuildConfig.DEBUG) {
                        Log.e("env.ARG_ERROR", "Error getting documents - ${it.localizedMessage}")
                    }
                }

        } else {
            binding.emptyPage.visibility = View.VISIBLE
        }

        /* val adapter = ComicsAdapter()
         binding.comicsRv.layoutManager = LinearLayoutManager(requireContext())
         binding.comicsRv.adapter = adapter*/

        binding.toolbar.setNavigationOnClickListener {
            navController.popBackStack()
        }
    }
}

