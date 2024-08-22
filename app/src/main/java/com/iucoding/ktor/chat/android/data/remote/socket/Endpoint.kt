package com.iucoding.ktor.chat.android.data.remote.socket

import com.iucoding.ktor.chat.android.data.remote.api.MessageService.Companion.BASE_URL

sealed class Endpoint(val url: String) {
    data object ChatSocket : Endpoint("$BASE_URL/chat-socket")
}
