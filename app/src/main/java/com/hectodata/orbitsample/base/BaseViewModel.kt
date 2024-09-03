package com.hectodata.orbitsample.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel: ViewModel() {

    abstract fun handleException(throwable: Throwable)

    open fun launchViewModelScope(dispatcher: CoroutineDispatcher = Dispatchers.IO, doWork: suspend () -> Unit) =
        viewModelScope.launch(viewModelScope.coroutineContext + dispatcher + exceptionHandler) {
            doWork()
        }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        handleException(throwable)
    }

}