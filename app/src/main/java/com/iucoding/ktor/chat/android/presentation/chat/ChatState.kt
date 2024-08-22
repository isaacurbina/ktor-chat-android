package com.iucoding.ktor.chat.android.presentation.chat

import com.iucoding.ktor.chat.android.domain.model.Message

data class ChatState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false
)
