package com.iucoding.ktor.chat.android.data.remote.api

import com.iucoding.ktor.chat.android.data.remote.dto.MessageDto
import com.iucoding.ktor.chat.android.data.remote.dto.toMessage
import com.iucoding.ktor.chat.android.domain.model.Message
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class MessageServiceImpl(
    private val client: HttpClient
) : MessageService {

    override suspend fun getAllMessages(): List<Message> {
        return try {
            client.get<List<MessageDto>>(Endpoint.GetAllMessages.url)
                .map { it.toMessage() }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
