package blog.naver.com.hmetal7.database.calendar

import blog.naver.com.hmetal7.domain.calendar.repository.CalendarRepository
import blog.naver.com.hmetal7.model.calendar.DayInfo
import jakarta.inject.Inject

class CalendarRepositoryImpl @Inject constructor(
    private val dao: CalendarDao
) : CalendarRepository {

    override suspend fun getCalendarDays(year: Int, month: Int): List<DayInfo> {
        return dao.getCalendarDays(year, month).map {
            DayInfo(
                it.solYear, it.solMonth, it.solDay, it.lunYear, it.lunMonth, it.lunDay,
                it.lunLeaf, it.ganjiKorYear, it.ganjiKorMonth, it.ganjiKorDay,
                it.ganjiChiYear, it.ganjiChiMonth, it.ganjiChiDay)
        }
    }
}