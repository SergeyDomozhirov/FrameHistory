package ru.lehandr.framehistoryrussia.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.lehandr.domain.model.ComicModel
import ru.lehandr.domain.model.EpochsModel
import ru.lehandr.domain.repository.FireBaseRepository
import ru.lehandr.framehistoryrussia.data.firebase.firestore.Firestore
import ru.lehandr.framehistoryrussia.data.utils.toComicsModelDomain
import ru.lehandr.framehistoryrussia.data.utils.toEpochModelDomain
import javax.inject.Inject

class FireBaseRepositoryImpl @Inject constructor(private val db: Firestore) : FireBaseRepository {

    override fun getEpochListFlow(): Flow<List<EpochsModel>> {
        return db.getEpochListFlow().map { listEpochsData ->
            val listEpochsDomain = ArrayList<EpochsModel>()
            for (item in listEpochsData) {
                listEpochsDomain.add(item.toEpochModelDomain())
            }
            return@map listEpochsDomain
        }
    }

    override fun getComicsListFlow(url: String): Flow<List<ComicModel>> {
        return db.getComicsListFlow(url).map { listComicsData ->
            val listComicsDomain = ArrayList<ComicModel>()
            listComicsData.forEach {
                listComicsDomain.add(it.toComicsModelDomain)
            }
            return@map listComicsDomain
        }
    }

}