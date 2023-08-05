package com.himanshu.mvvm.data.models

import com.himanshu.mvvm.data.db.entities.Event
import java.io.Serializable

data class EventsByDates (val eventsByDates: List<Event>): Serializable