package blog.naver.com.hmetal7.database.calendar

import androidx.room.Dao
import androidx.room.Query

@Dao
interface CalendarDao {
    @Query("SELECT * FROM calendar_date WHERE sol_year = :year AND sol_month = :month ORDER BY sol_day")
    suspend fun getCalendarDays(year: Int, month: Int): List<CalendarEntity>
}