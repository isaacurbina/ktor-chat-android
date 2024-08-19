package com.iucoding.ktor.chat.android.data.remote.api

import com.iucoding.ktor.chat.android.data.remote.api.MessageService.Companion.BASE_URL

sealed class Endpoint(val url: String) {
    data object GetAllMessages : Endpoint("$BASE_URL/messages")
}
