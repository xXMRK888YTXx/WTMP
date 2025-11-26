package com.xxmrk888ytxx.selecttrackedappscreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.coredeps.SharedInterfaces.PackageInfoProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.TrackedAppRepository
import com.xxmrk888ytxx.coredeps.defaultViewModelStateIn
import com.xxmrk888ytxx.selecttrackedappscreen.model.DialogState
import com.xxmrk888ytxx.selecttrackedappscreen.model.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import toState
import javax.inject.Inject

class SelectTrackedAppViewModel @Inject constructor(
    private val packageInfoProvider: PackageInfoProvider,
    private val trackedAppRepository: TrackedAppRepository
) : ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading)
    internal val screenState = _screenState.asStateFlow().map { currentState ->
        if (currentState !is ScreenState.ShopAppListState) return@map currentState
        currentState.copy(appList = currentState.appList.filterSearch(currentState.searchLineText))
    }.defaultViewModelStateIn(viewModelScope, ScreenState.Loading)

    private val _dialogState = MutableStateFlow<DialogState>(DialogState.None)
    val dialogState = _dialogState.asStateFlow()

    internal val trackedPackageNames: Flow<Set<String>>
        get() = trackedAppRepository.getAllTrackedPackageNames().map { it.toSet() }
            .defaultViewModelStateIn(viewModelScope, emptySet())

    internal fun enableTrack(packageName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            trackedAppRepository.addTrackedPackageName(packageName)
        }
    }

    internal fun disableTrack(packageName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            trackedAppRepository.removeTrackedPackageName(packageName)
        }
    }

    fun onSearchLineTextChanged(text: String) {
        _screenState.update {
            if (it is ScreenState.ShopAppListState) it.copy(searchLineText = text) else it
        }
    }

    fun showAddPackageDialog() {
        _dialogState.update { DialogState.AddPackageDialog("") }
    }

    fun hideAddPackageDialog() {
        _dialogState.update { DialogState.None }
    }

    fun onAddPackageNameDialogTextChanged(text: String) {
        _dialogState.update {
            if (it is DialogState.AddPackageDialog) it.copy(packageName = text) else it
        }
    }

    fun addNewPackage() {
        hideAddPackageDialog()
    }


    init {
        viewModelScope.launch(Dispatchers.Default) {
            val appList = packageInfoProvider.getAllApplicationInfo()
            withContext(Dispatchers.Main) {
                _screenState.value = ScreenState.ShopAppListState(appList)
            }
        }
    }
}