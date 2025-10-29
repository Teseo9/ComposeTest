package blog.naver.com.hmetal7.database.calendar

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CalendarEntity::class], version = 1)
abstract class CalendarDatabase : RoomDatabase() {
    abstract fun calendarDao(): CalendarDao
}