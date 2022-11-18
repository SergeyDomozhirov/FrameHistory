package ru.lehandr.framehistoryrussia.epochs.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import ru.lehandr.framehistoryrussia.R
import ru.lehandr.framehistoryrussia.databinding.FragmentEpochsBinding
import ru.lehandr.domain.model.ComicModel
import ru.lehandr.domain.model.EpochsModel
import ru.lehandr.domain.useCase.EpochLoadImageUseCase
import javax.inject.Inject
// Hilt для fragmenta. И создание viewModel
@AndroidEntryPoint
class EpochsFragment: Fragment(), EpochsAdapter.Listener {

    private val viewModel: EpochViewModel by viewModels()
    private var _binding: FragmentEpochsBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private val TAG = "KAN"
// Получаем зависимость
    @Inject lateinit var epochLoadImageUseCaseHilt: EpochLoadImageUseCase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentEpochsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()
        viewModel.initRecyclerServices()

        viewModel.epochListLiveData.observe(viewLifecycleOwner) { epochList ->
            val adapter = EpochsAdapter(epochList.sortedBy { it.id }, this, epochLoadImageUseCaseHilt)
            binding.epochsRv.layoutManager = LinearLayoutManager(requireContext())
            binding.epochsRv.adapter = adapter
        }
    }

    override fun onClick(uri: String) {
    /*    db.collection("$uri/mobile").get().addOnSuccessListener { result ->
            val comicsList = ArrayList<ComicModel>()
            for (i in 0 until result.size()) {
                db.collection("$uri/mobile").document(result.documents[i].id).get().addOnSuccessListener { documentSnapshot ->
                    documentSnapshot.toObject<ComicModel>()?.let {
                        comicsList.add(ComicModel(id = it.id, imageURL = it.imageURL, title = it.title))
                        if (comicsList.size == result.size()) {
                            Toast.makeText(requireContext(), "картинка номер $uri", Toast.LENGTH_SHORT).show()
                            *//*val adapter = EpochsAdapter(epochsList.sortedByDescending { it.id }, this)
                            binding.epochsRv.layoutManager = LinearLayoutManager(requireContext())
                            binding.epochsRv.adapter = adapter*//*
                        }
                    }
                }
            }
        }*/
    }
}