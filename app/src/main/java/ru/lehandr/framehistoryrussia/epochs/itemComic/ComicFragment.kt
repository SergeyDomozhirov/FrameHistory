package ru.lehandr.framehistoryrussia.epochs.itemComic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import ru.lehandr.framehistoryrussia.databinding.FragmentComicBinding

class ComicFragment : Fragment() {

    private val viewModel: ComicFragmentViewModel by viewModels()
    private var _binding: FragmentComicBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentComicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()


        binding.toolbar.setNavigationOnClickListener {
            navController.popBackStack()
        }

    }


}



