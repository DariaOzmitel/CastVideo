package com.example.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.CastDeviceState
import com.example.domain.usecases.CastVideoUseCase
import com.example.domain.usecases.GetDeviceListUseCase
import com.example.domain.usecases.GetDeviceStateUseCase
import com.example.domain.usecases.SearchChromeCastDeviceUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class CastVideoScreenViewModel(
    private val searchChromeCastDeviceUseCase: SearchChromeCastDeviceUseCase,
    private val castVideoUseCase: CastVideoUseCase,
    private val getDeviceListUseCase: GetDeviceListUseCase,
    private val getDeviceStateUseCase: GetDeviceStateUseCase
) : ViewModel() {
    private val castVideoScreenStateMutable: MutableStateFlow<CastVideoScreenState> =
        MutableStateFlow(CastVideoScreenState(deviceState = CastDeviceState.INITIAL))
    private val castVideoScreenState: StateFlow<CastVideoScreenState> = castVideoScreenStateMutable

    init {
        getDeviceState()
        getDeviceList()
    }

    fun getCastVideoScreenState(): StateFlow<CastVideoScreenState> = castVideoScreenState

    fun searchChromeCastDevice() {
        searchChromeCastDeviceUseCase.invoke()
    }

    fun connect(id: String) {
        castVideoUseCase.invoke(id)
    }

    private fun getDeviceState() {
        viewModelScope.launch {
            getDeviceStateUseCase.invoke().collect { deviceState ->
                castVideoScreenStateMutable.update {
                    it.copy(deviceState = deviceState)
                }
            }
        }
    }

    private fun getDeviceList() {
        viewModelScope.launch {
            getDeviceListUseCase.invoke().collect{ deviceList ->
                castVideoScreenStateMutable.update {
                    it.copy(deviceList = deviceList)
                }
            }
        }
    }
}