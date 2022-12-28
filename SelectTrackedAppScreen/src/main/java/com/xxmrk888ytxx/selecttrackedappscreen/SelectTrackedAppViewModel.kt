package com.xxmrk888ytxx.selecttrackedappscreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.coredeps.SharedInterfaces.PackageInfoProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.TrackedAppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import toState
import javax.inject.Inject

class SelectTrackedAppViewModel @Inject constructor(
    private val packageInfoProvider: PackageInfoProvider,
    private val trackedAppRepository: TrackedAppRepository
) : ViewModel() {

    private val _screenState:MutableState<ScreenState> = mutableStateOf(ScreenState.Loading)

    internal val screenState = _screenState.toState()

    internal val searchLineTest = mutableStateOf("")

    internal val trackedPackageNames : Flow<List<String>>
        get() = trackedAppRepository.getAllTrackedPackageNames()

    internal fun enableTrack(packageName:String) {
        viewModelScope.launch(Dispatchers.IO) {
            trackedAppRepository.addTrackedPackageName(packageName)
        }
    }

    internal fun disableTrack(packageName:String) {
        viewModelScope.launch(Dispatchers.IO) {
            trackedAppRepository.removeTrackedPackageName(packageName)
        }
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