package ru.lehandr.framehistoryrussia.epochs.comics

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import ru.lehandr.domain.model.ComicModel
import ru.lehandr.domain.useCase.LoadImageUseCase
import ru.lehandr.framehistoryrussia.R
import ru.lehandr.framehistoryrussia.data.firebase.firestore.models.ComicsModelData
import ru.lehandr.framehistoryrussia.databinding.ComicsImageBinding

class ComicsAdapter(
    private val comicsList: List<ComicModel>,
    private val clickListener: ClickListener,
    private var loadImageUseCaseHilt: LoadImageUseCase
) : RecyclerView.Adapter<ComicsAdapter.ComicsHolder>() {

    interface ClickListener {
        fun onClick(nameComic: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsHolder {
        return ComicsHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.comics_image, parent, false), parent.context,
            clickListener, loadImageUseCaseHilt
        )
    }

    override fun onBindViewHolder(holder: ComicsHolder, position: Int) {
        holder.bind(comicsList[position])
    }

    override fun getItemCount(): Int {
        return comicsList.size
    }

    class ComicsHolder(
        view: View,
        private val context: Context,
        private val clickListener: ClickListener,
        private var loadImageUseCaseHilt: LoadImageUseCase
    ) : RecyclerView.ViewHolder(view) {

        private val binding: ComicsImageBinding = ComicsImageBinding.bind(view)

        fun bind(model: ComicModel) {

            model.coverURL?.let { url ->
                MainScope().launch {
                    loadImageUseCaseHilt.execute(url).collect {
                        Glide.with(context).load(it).into(binding.imageComic)
                        binding.imageComic.clipToOutline = true
                        binding.imageTitle.text = model.title
                    }
                }
            }

            binding.imageComic.setOnClickListener {
                model.title?.let {
                    clickListener.onClick(it)
                }
            }
        }
    }
}