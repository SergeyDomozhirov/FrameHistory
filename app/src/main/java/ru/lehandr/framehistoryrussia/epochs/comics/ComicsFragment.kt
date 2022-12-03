package ru.lehandr.framehistoryrussia.epochs.comics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.lehandr.domain.useCase.LoadImageUseCase
import ru.lehandr.framehistoryrussia.databinding.FragmentComicsBinding
import javax.inject.Inject

const val ARG_URL_EPOCH = "arg_url_epoch"

@AndroidEntryPoint
class ComicsFragment() : Fragment(), ComicsAdapter.ClickListener {

    private val viewModel: ComicsViewModel by viewModels()
    private var urlEpoch: String? = null
    private var _binding: FragmentComicsBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    @Inject
    lateinit var loadImageUseCaseHilt: LoadImageUseCase

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

        urlEpoch?.let {
            viewModel.getListComics(it)
        }

        viewModel.comicsListLiveData.observe(viewLifecycleOwner) { comicsList ->
            val adapter = ComicsAdapter(comicsList.sortedBy { it.id }, clickListener = this@ComicsFragment, loadImageUseCaseHilt)
            binding.comicsRv.adapter = adapter
        }

        binding.toolbar.setNavigationOnClickListener {
            navController.popBackStack()
        }
    }

    override fun onClick() {
        Toast.makeText(requireContext(), "Нажали на собаку", Toast.LENGTH_LONG).show()
    }
}

