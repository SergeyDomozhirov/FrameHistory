package ru.lehandr.framehistoryrussia.epochs.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.lehandr.domain.model.EpochsModel
import ru.lehandr.domain.useCase.EpochsListUseCase
import javax.inject.Inject

@HiltViewModel
class EpochViewModel @Inject constructor(private val epochListUseCase: EpochsListUseCase) : ViewModel() {

    private val epochListMutable: MutableLiveData<ArrayList<EpochsModel>> = MutableLiveData()
    val epochListLiveData: LiveData<ArrayList<EpochsModel>> = epochListMutable

    fun initRecyclerServices() {
        epochListMutable.value = epochListUseCase.execute()
    }
}