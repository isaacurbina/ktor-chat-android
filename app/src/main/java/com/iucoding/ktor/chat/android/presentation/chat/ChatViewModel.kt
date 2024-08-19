package com.iucoding.ktor.chat.android.presentation.chat

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iucoding.ktor.chat.android.data.remote.api.MessageService
import com.iucoding.ktor.chat.android.data.remote.socket.ChatSocketService
import com.iucoding.ktor.chat.android.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageService: MessageService,
    private val chatSocketService: ChatSocketService,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // region properties
    private val _messageText = mutableStateOf("")
    val messageText: State<String> = _messageText

    private val _state = mutableStateOf(ChatState())
    val state: State<ChatState> = _state

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()
    // endregion

    init {
        getAllMessages()
        savedStateHandle.get<String>("username")?.let {
            viewModelScope.launch {
                when (val result = chatSocketService.initSession(username = it)) {
                    is Resource.Success -> {
                        chatSocketService.observeMessages()
                            .onEach {
                                val newList = state.value.messages.toMutableList().apply {
                                    add(0, it)
                                }
                                _state.value = state.value.copy(messages = newList)
                            }
                    }

                    is Resource.Error ->
                        _toastEvent.emit(result.message ?: "Unknown error.")
                }
            }
        }
    }

    // region UI interaction
    fun onMessageChange(message: String) {
        _messageText.value = message
    }

    fun disconnect() = viewModelScope.launch {
        chatSocketService.closeSession()
    }

    fun getAllMessages() = viewModelScope.launch {
        _state.value = state.value.copy(isLoading = true)
        val result = messageService.getAllMessages()
        _state.value = state.value.copy(
            messages = result,
            isLoading = false
        )
    }

    fun sendMessage() = viewModelScope.launch {
        if (messageText.value.isNotBlank()) {
            chatSocketService.sendMessage(messageText.value)
        }
    }
    // endregion

    // region view model
    override fun onCleared() {
        super.onCleared()
        disconnect()
    }
    // endregion
}
