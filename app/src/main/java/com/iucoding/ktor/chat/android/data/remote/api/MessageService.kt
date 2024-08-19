package com.iucoding.ktor.chat.android.data.remote.api

import com.iucoding.ktor.chat.android.domain.model.Message

interface MessageService {

    suspend fun getAllMessages(): List<Message>

    companion object {
        const val BASE_URL = "http://localhost:8080/"
    }
}
