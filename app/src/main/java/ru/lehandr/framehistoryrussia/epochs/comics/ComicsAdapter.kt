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
import ru.lehandr.framehistoryrussia.R
import ru.lehandr.framehistoryrussia.data.firebase.firestore.models.ComicsModelData
import ru.lehandr.framehistoryrussia.databinding.ComicsImageBinding

//у любого класса должен быть конструктор - ()
class ComicsAdapter(
    private val comicsList: List<ComicsModelData>,
    private val clickListener: ClickListener
) : RecyclerView.Adapter<ComicsAdapter.ComicsHolder>() {

    interface ClickListener {
        fun onClick()
    }

    //раздувает макет одной ячейки
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsHolder {
        return ComicsHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.comics_image, parent, false), parent.context,
             clickListener)

    }

    //связывает одну/каждую ячейку с данными и передаёт заполнение данных во внутренний класс ComicsHolder
    override fun onBindViewHolder(holder: ComicsHolder, position: Int) {
        holder.bind(comicsList[position].coverURL)
    }

    //возвращает количество ячеек
    override fun getItemCount(): Int {
        return comicsList.size
    }


    //класс производит действия(вставляет данные. картинку и т.д.) в каждую ячейку
    class ComicsHolder(view: View,
                       private val context: Context,
                       private val clickListener: ClickListener) : RecyclerView.ViewHolder(view) {

        val binding: ComicsImageBinding = ComicsImageBinding.bind(view)


        //С помощью glide загрузить в imageComic собачку по данному url
        fun bind(coverURL: String?) {

            coverURL?.let {
                Firebase.storage.getReferenceFromUrl(it).downloadUrl.addOnSuccessListener { uri ->
                    Glide.with(context)
                        .load(uri)
                        .into(binding.imageComic)
                }



            }


//            Glide.with(binding.imageComic)
//                .load(dogImage)
//                .circleCrop()
//                .into(binding.imageComic)

////            Glide.with(binding.imageComic)
////                .asGif()
////                .load(dogImage)
////                .into(object : SimpleTarget<GifDrawable>() {
////                    override fun onResourceReady(
////                        resource: GifDrawable,
////                        transition: Transition<in GifDrawable>?
////                    ) {
////                        resource.start()
////                        binding.imageComic.setImageDrawable(resource)
//                    }
//                })

//            Glide.with(binding.imageComic)
//                .load(dogImage)
//                .placeholder(R.drawable.house_1)
//                .into(binding.imageComic)

            binding.imageComic.setOnClickListener {
                clickListener.onClick()
            }


        }
    }
}