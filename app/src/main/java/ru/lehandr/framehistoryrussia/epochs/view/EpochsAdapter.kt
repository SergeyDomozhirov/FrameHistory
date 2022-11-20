package ru.lehandr.framehistoryrussia.epochs.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import ru.lehandr.domain.model.EpochsModel
import ru.lehandr.domain.useCase.EpochLoadImageUseCase
import ru.lehandr.framehistoryrussia.R
import ru.lehandr.framehistoryrussia.databinding.ItemEpochBinding
// Создаем слушателя для загрузки изображения.
class EpochsAdapter (private var listEpochs: List<EpochsModel>,
                     private var listener: Listener,
                     private var epochLoadImageUseCaseHilt: EpochLoadImageUseCase
                     ) : RecyclerView.Adapter<EpochsAdapter.EpochHolder>() {

    interface Listener {
        fun onClick(uri: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpochHolder {
        return EpochHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_epoch, parent, false),
            parent.context, listener, epochLoadImageUseCaseHilt)
    }

    override fun onBindViewHolder(holder: EpochHolder, position: Int) {
        holder.bind(listEpochs[position])
    }

    override fun getItemCount(): Int {
        return listEpochs.size
    }

    class EpochHolder(view: View, private val context: Context,
                      private var listener: Listener,
                      private var epochLoadImageUseCaseHilt: EpochLoadImageUseCase) : RecyclerView.ViewHolder(view) {

        private val binding: ItemEpochBinding = ItemEpochBinding.bind(view)

        fun bind(item: EpochsModel?) {

            item?.imageURL?.let { imageUrl ->
                MainScope().launch {
                    epochLoadImageUseCaseHilt.execute(imageUrl).collect {
                        Glide.with(context).load(it).into(binding.imageEpoch)
                    }
                }
            }

            binding.imageEpoch.setOnClickListener {
                item?.fullPath?.let { epoch ->
                    listener.onClick(uri = epoch)
                }
            }


        }
    }
}