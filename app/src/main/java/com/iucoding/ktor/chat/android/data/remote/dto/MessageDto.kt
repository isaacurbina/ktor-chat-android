package com.iucoding.ktor.chat.android.data.remote.dto

import com.iucoding.ktor.chat.android.domain.model.Message
import kotlinx.serialization.Serializable
import java.text.DateFormat
import java.util.Date

@Serializable
data class MessageDto(
    val text: String,
    val timestamp: Long,
    val username: String,
    val id: String,
)

fun MessageDto.toMessage(): Message {
    return Message(
        text = text,
        formattedTime = timestamp.toFormattedDate(),
        username = username
    )
}

private fun Long.toFormattedDate(): String {
    val date = Date(this)
    return DateFormat
        .getDateInstance(DateFormat.DEFAULT)
        .format(date)
}
