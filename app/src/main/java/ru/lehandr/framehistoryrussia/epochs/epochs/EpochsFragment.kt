package ru.lehandr.framehistoryrussia.epochs.epochs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.lehandr.domain.useCase.EpochLoadImageUseCase
import ru.lehandr.framehistoryrussia.R
import ru.lehandr.framehistoryrussia.databinding.FragmentEpochsBinding
import javax.inject.Inject

// Hilt для fragmenta. И создание viewModel
@AndroidEntryPoint
class EpochsFragment : Fragment(), EpochsAdapter.Listener {

    private val viewModel: EpochViewModel by viewModels()
    private var _binding: FragmentEpochsBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private val TAG = "KAN"

    // Получаем зависимость
    @Inject
    lateinit var epochLoadImageUseCaseHilt: EpochLoadImageUseCase

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
        navController.navigate(R.id.comicsFragment)
    }
}