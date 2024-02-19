package com.app.data.local

import androidx.room.TypeConverter
import com.app.data.model.dto.MediaDto
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromMediaDtoList(mediaList: List<MediaDto>): String {
        return json.encodeToString(mediaList)
    }

    @TypeConverter
    fun toMediaDtoList(mediaListString: String): List<MediaDto> {
        return json.decodeFromString(mediaListString)
    }
}
