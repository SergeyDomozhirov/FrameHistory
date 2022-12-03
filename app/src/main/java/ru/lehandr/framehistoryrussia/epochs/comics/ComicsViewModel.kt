package ru.lehandr.framehistoryrussia.epochs.comics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.lehandr.domain.model.ComicModel
import ru.lehandr.domain.useCase.ComicsListUseCase
import javax.inject.Inject

@HiltViewModel
class ComicsViewModel @Inject constructor(private val comicsListUseCase: ComicsListUseCase) : ViewModel() {

    private val comicsListMutable: MutableLiveData<List<ComicModel>> = MutableLiveData()
    val comicsListLiveData: LiveData<List<ComicModel>> = comicsListMutable

    fun getListComics(url: String) {
        viewModelScope.launch {
            comicsListUseCase.execute(url).collect {
                comicsListMutable.value = it
            }
        }
    }
}