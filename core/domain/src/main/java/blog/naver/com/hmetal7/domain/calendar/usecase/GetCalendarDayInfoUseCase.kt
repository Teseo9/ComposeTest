package blog.naver.com.hmetal7.domain.calendar.usecase

import blog.naver.com.hmetal7.domain.calendar.repository.CalendarRepository
import blog.naver.com.hmetal7.model.calendar.DayInfo
import javax.inject.Inject

class GetCalendarDayInfoUseCase @Inject constructor(
    private val repository: CalendarRepository
) {
    suspend operator fun invoke(year: Int, month: Int): List<DayInfo> {
        return repository.getCalendarDays(year, month)
    }
}