package ru.lehandr.framehistoryrussia.epochs.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.lehandr.domain.model.EpochsModel
import ru.lehandr.domain.useCase.EpochsListUseCase
import javax.inject.Inject

@HiltViewModel
class EpochViewModel @Inject constructor(private val epochListUseCase: EpochsListUseCase) : ViewModel() {

    private val epochListMutable: MutableLiveData<List<EpochsModel>> = MutableLiveData()
    val epochListLiveData: LiveData<List<EpochsModel>> = epochListMutable

    fun initRecyclerServices() {
        viewModelScope.launch {
               epochListUseCase.execute().collect {
                   epochListMutable.value = it
               }
        }
    }
}