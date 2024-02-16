package com.gomaping.navigation.ui.events.repository

import com.gomaping.navigation.ui.events.model.EventModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EventRepositoryImpl: EventRepository {
    private var lastId = 0L

    private val data: List<EventModel> = listOf(
        EventModel(
            id = ++lastId,
            title = "Цирк Никулина \n на Цветном бульваре",
            link = "link",
            descriptions = "Самый старый цирк в нашей стране, известный далеко за её пределами. Сильнейшие мастера....",
            schedule = "Ежедневно 11:00 - 19:00",
            breakTime = "Перерыв 15:00 - 16:00",
            reviews = "4.6",
            timeTo = "20 мин",
            distance = "10 км"
        ),
        EventModel(
            id = ++lastId,
            title = "Цирк Никулина \n на Цветном бульваре",
            link = "link",
            descriptions = "Самый старый цирк в нашей стране, известный далеко за её пределами. Сильнейшие мастера....",
            schedule = "Ежедневно 11:00 - 19:00",
            breakTime = "Перерыв 15:00 - 16:00",
            reviews = "4.5",
            timeTo = "30 мин",
            distance = "50 км"
        ),
        EventModel(
            id = ++lastId,
            title = "Цирк Никулина \n на Цветном бульваре",
            link = "link",
            descriptions = "Самый старый цирк в нашей стране, известный далеко за её пределами. Сильнейшие мастера....",
            schedule = "Ежедневно 11:00 - 19:00",
            breakTime = "Перерыв 15:00 - 16:00",
            reviews = "4.1",
            timeTo = "40 мин",
            distance = "40 км"
        ),
        EventModel(
            id = ++lastId,
            title = "Цирк Никулина \n на Цветном бульваре",
            link = "link",
            descriptions = "Самый старый цирк в нашей стране, известный далеко за её пределами. Сильнейшие мастера....",
            schedule = "Ежедневно 11:00 - 19:00",
            breakTime = "Перерыв 15:00 - 16:00",
            reviews = "1.6",
            timeTo = "50 мин",
            distance = "30 км"
        ),
        EventModel(
            id = ++lastId,
            title = "Цирк Никулина \n на Цветном бульваре",
            link = "link",
            descriptions = "Самый старый цирк в нашей стране, известный далеко за её пределами. Сильнейшие мастера....",
            schedule = "Ежедневно 11:00 - 19:00",
            breakTime = "Перерыв 15:00 - 16:00",
            reviews = "3.6",
            timeTo = "10 мин",
            distance = "20 км"
        ),
    )

    init {
        lastId = data.last().id
    }
    override suspend fun getEvents(): Flow<List<EventModel>>  = flow{
        emit(data)
    }
}