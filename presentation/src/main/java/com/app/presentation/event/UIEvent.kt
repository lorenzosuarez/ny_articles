package com.app.presentation.event

sealed class UIEvent {
    data class ShowErrorMessage(val message: String) : UIEvent()

    data class OpenUrl(val url: String) : UIEvent()
}
