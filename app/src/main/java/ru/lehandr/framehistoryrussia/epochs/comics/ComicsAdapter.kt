package ru.lehandr.framehistoryrussia.epochs.comics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.comics_image.*
import kotlinx.android.synthetic.main.comics_image.view.*
import ru.lehandr.framehistoryrussia.R
import ru.lehandr.framehistoryrussia.data.firebase.firestore.models.ComicsModelData
import ru.lehandr.framehistoryrussia.databinding.ComicsImageBinding

//у любого класса должен быть конструктор - ()
class ComicsAdapter(private val comicsList: List<ComicsModelData>) : RecyclerView.Adapter<ComicsAdapter.ComicsHolder>() {

    //раздувает макет одной ячейки
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsHolder {
        return ComicsHolder(LayoutInflater.from(parent.context).inflate(R.layout.comics_image, parent, false))

    }

    //связывает одну/каждую ячейку с данными и передаёт заполнение данных во внутренний класс ComicsHolder
    override fun onBindViewHolder(holder: ComicsHolder, position: Int) {
        holder.bind()
    }

    //возвращает количество ячеек
    override fun getItemCount(): Int {
        return 10
    }




    //класс производит действия(вставляет данные. картинку и т.д.) в каждую ячейку
    class ComicsHolder(view: View) : RecyclerView.ViewHolder(view) {

        val binding: ComicsImageBinding = ComicsImageBinding.bind(view)


        //С помощью glide загрузить в imageComic собачку по данному url
        fun bind() {
            val dogImage = "https://gas-kvas.com/uploads/posts/2022-06/1655758774_1-gas-kvas-com-p-ovcharka-sobaka-foto-1.jpg"

            Glide.with(binding.imageComic)
                .load(dogImage)
                .into(binding.imageComic)

        }
    }
}