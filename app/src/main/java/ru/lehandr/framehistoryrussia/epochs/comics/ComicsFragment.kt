package ru.lehandr.framehistoryrussia.epochs.comics

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import ru.lehandr.data.BuildConfig
import ru.lehandr.domain.useCase.ComicLoadImageUseCase
import ru.lehandr.framehistoryrussia.R
import ru.lehandr.framehistoryrussia.data.firebase.firestore.models.ComicsModelData
import ru.lehandr.framehistoryrussia.databinding.FragmentComicsBinding
import javax.inject.Inject

const val ARG_URL_EPOCH = "arg_url_epoch"

class ComicsFragment : Fragment(), ComicsAdapter.ClickListener {

    val db = Firebase.firestore
    private val viewModel: ComicsViewModel by viewModels()
    private var urlEpoch: String? = null
    private var _binding: FragmentComicsBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

//    @Inject
//    latenir var comicLoadImageUseCaseHilt: ComicLoadImageUseCase

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
                                    id = modelData.id, coverURL = modelData.coverURL,
                                    fullPath = documentSnapshot.reference.path + modelData.fullPath
                                )
                            )
                            if (comicsList.size == result.size()) {
                                MainScope().launch {
//                                    trySend(comicsList)
                                    val adapter = ComicsAdapter(comicsList.sortedBy { it.id }, clickListener = this@ComicsFragment)
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

        binding.toolbar.setNavigationOnClickListener {
            navController.popBackStack()
        }
    }

    override fun onClick() {
        Toast.makeText(requireContext(),"Нажали на собаку", Toast.LENGTH_LONG).show()
    }
}

