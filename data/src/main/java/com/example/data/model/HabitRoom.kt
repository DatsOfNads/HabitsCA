package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.domain.model.Habit
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "habits_data")
data class HabitRoom(
    @PrimaryKey
    var uid: String,
    val title: String,
    val description: String,
    val priority: Int,
    val type: Int,
    val count: Int,
    val frequency: Int,
    val date: Long,

    @TypeConverters(HabitsConverter::class)
    @SerializedName("done_dates")
    var doneDates: List<String>?
): Serializable{

    class HabitsConverter {
        @TypeConverter
        fun fromHabits(habits: List<String>): String {
            return habits.joinToString(",")
        }

        @TypeConverter
        fun toHabits(data: String): List<String> {
            return data.split(",")
        }
    }
}