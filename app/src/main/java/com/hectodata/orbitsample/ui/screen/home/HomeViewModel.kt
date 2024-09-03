package com.hectodata.orbitsample.ui.screen.home

import android.net.http.HttpException
import android.os.Build
import android.os.ext.SdkExtensions
import com.hectodata.orbitsample.Screen
import com.hectodata.orbitsample.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import java.net.SocketException
import java.net.UnknownHostException
import javax.inject.Inject


const val HOME_SIDE_EFFECTS_KEY = "side-effects_key"

@HiltViewModel
class HomeViewModel @Inject constructor(

):ContainerHost<HomeContract.HomeState, HomeContract.HomeEffect>, BaseViewModel() {

    override val container = container<HomeContract.HomeState, HomeContract.HomeEffect>(HomeContract.HomeState())

    override fun handleException(throwable: Throwable) {
        val message = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && SdkExtensions.getExtensionVersion(Build.VERSION_CODES.S) >= 7) {
            when(throwable) {
                is SocketException -> {
                    "네트워크 연결을 확인해 주세요."
                }
                is HttpException -> {
                    "네트워크 연결을 확인해 주세요."
                }
                is UnknownHostException -> {
                    "네트워크 연결을 확인해 주세요."
                }
                is NullPointerException -> {
                    "오류가 발생하였습니다."
                }
                else -> {
                    "오류가 발생하였습니다."
                }
            }
        } else {
            when(throwable) {
                is SocketException -> {
                    "네트워크 연결을 확인해 주세요."
                }
                is UnknownHostException -> {
                    "네트워크 연결을 확인해 주세요."
                }
                is NullPointerException -> {
                    "오류가 발생하였습니다."
                }
                else -> {
                    "오류가 발생하였습니다."
                }
            }
        }

        intent {
            postSideEffect(HomeContract.HomeEffect.Navigate(Screen.Second.route))
            reduce { state.copy(progress = false) }
        }
    }


    fun setEvent(homeEvent: HomeContract.HomeEvent) {
        when(homeEvent) {
            is HomeContract.HomeEvent.OnClickCount -> {
                intent {
                    reduce {
                        if (state.count >= 10) {
                            intent {
                                postSideEffect(HomeContract.HomeEffect.Toast("카운트 증가는 10 까지 가능합니다."))
                            }
                            state.copy(isVisible = true)
                        } else {
                            state.copy(count = state.count + 1)
                        }
                    }
                }
            }

            is HomeContract.HomeEvent.OnClickIsVisibleButton -> {
                intent {
                    reduce {
                        state.copy(isVisible = !state.isVisible)
                    }
                }
            }

            is HomeContract.HomeEvent.OnClickNavigateSecondScreen -> {
                intent {
                    postSideEffect(HomeContract.HomeEffect.Navigate(Screen.Second.route))
                }
            }
        }
    }
}