package blog.naver.com.hmetal7.domain.calendar.repository

import blog.naver.com.hmetal7.model.calendar.DayInfo

interface CalendarRepository {

    /**
     * 파라미터의 '양력 년,월'에 맞는 날짜 정보들을 반환.
     * ex) [year: 2025, month: 10] 이면 1일부터 31까지 총 31개의 DayInfo가 반환됨.
     */
    suspend fun getCalendarDays(year: Int, month: Int): List<DayInfo>
}