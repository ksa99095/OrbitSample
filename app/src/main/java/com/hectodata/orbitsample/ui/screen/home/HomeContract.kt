package com.hectodata.orbitsample.ui.screen.home

class HomeContract {

    data class HomeState(
        val isVisible: Boolean = false,
        val count: Int = 0,
        val progress: Boolean = false
    )

    sealed class HomeEvent {
        data object OnClickCount: HomeEvent()
        data object OnClickIsVisibleButton: HomeEvent()
        data object OnClickNavigateSecondScreen: HomeEvent()
    }

    sealed class HomeEffect {
        data class Toast(val message: String): HomeEffect()
        data class Api(val api: String, val param: String): HomeEffect()
        data class Navigate(val route: String, val param: String = ""): HomeEffect()
    }
}