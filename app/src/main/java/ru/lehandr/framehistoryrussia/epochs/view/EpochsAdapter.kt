package ru.lehandr.framehistoryrussia.epochs.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.AndroidEntryPoint
import ru.lehandr.framehistoryrussia.R
import ru.lehandr.framehistoryrussia.databinding.ItemEpochBinding
import ru.lehandr.domain.model.EpochsModel
import ru.lehandr.domain.useCase.EpochLoadImageUseCase
import ru.lehandr.domain.useCase.EpochsListUseCase
import ru.lehandr.framehistoryrussia.data.FirebaseStorageRepositoryImpl
import javax.inject.Inject

class EpochsAdapter (private var listEpochs: List<EpochsModel>, private var listener: Listener) : RecyclerView.Adapter<EpochsAdapter.EpochHolder>() {

    interface Listener {
        fun onClick(uri: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpochHolder {
        return EpochHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_epoch, parent, false),
            parent.context, listener)
    }

    override fun onBindViewHolder(holder: EpochHolder, position: Int) {
        holder.bind(listEpochs[position])
    }

    override fun getItemCount(): Int {
        return listEpochs.size
    }

    class EpochHolder(view: View, private val context: Context, private var listener: Listener) : RecyclerView.ViewHolder(view) {

        private val binding: ItemEpochBinding = ItemEpochBinding.bind(view)

        private val storage = Firebase.storage
        /*private val repository = FirebaseStorageRepositoryImpl(storage)
        private val epochLoadImageUseCase: EpochLoadImageUseCase = EpochLoadImageUseCase(repository)*/

//        @Inject lateinit var epochLoadImageUseCaseHilt: EpochLoadImageUseCase

        fun bind(item: EpochsModel?) {
//            epochLoadImageUseCase.execute()
//            epochLoadImageUseCaseHilt.execute()

            item?.imageURL?.let { storage.getReferenceFromUrl(it).downloadUrl.addOnSuccessListener { uri ->
                Glide.with(context).load(uri).into(binding.imageEpoch) }
            }
            binding.imageEpoch.setOnClickListener {
                item?.fullPath?.let { epoch -> listener.onClick(uri = epoch) }
            }
        }
    }
}