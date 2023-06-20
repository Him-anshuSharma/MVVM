package com.himanshu.mvvm.data.network.responses

import com.himanshu.mvvm.data.db.entities.Event

data class EventResponse(
    val isSuccessful: Boolean,
    val events: List<Event>
)


