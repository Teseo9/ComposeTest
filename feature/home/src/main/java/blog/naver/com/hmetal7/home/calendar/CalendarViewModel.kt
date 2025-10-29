package blog.naver.com.hmetal7.home.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import blog.naver.com.hmetal7.domain.calendar.usecase.GetCalendarDayInfoUseCase
import blog.naver.com.hmetal7.model.calendar.DayInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getCalendarDayInfoUseCase: GetCalendarDayInfoUseCase
) : ViewModel() {
    private val baseYearMonth = YearMonth.now()

    private val _months = MutableStateFlow<Map<YearMonth, List<DayInfo>>>(emptyMap())
    val months = _months.asStateFlow()

    private val _currentYearMonth = MutableStateFlow(YearMonth.now())
    val currentYearMonth = _currentYearMonth.asStateFlow()

    init {
        preloadMonths(baseYearMonth)
    }

    fun preloadMonths(center: YearMonth) {
        viewModelScope.launch {
            val monthsToLoad = (-2L..2L).map { center.plusMonths(it) }

            // 이미 캐싱된 데이터 유지
            val currentCache = _months.value.toMutableMap()

            monthsToLoad.forEach { ym ->
                if (!currentCache.containsKey(ym)) {
                    val data = getCalendarDayInfoUseCase(ym.year, ym.monthValue)
                    currentCache[ym] = data
                }
            }

            // 캐시가 너무 커지지 않도록 오래된 데이터 정리
            val allowed = monthsToLoad.toSet()
            currentCache.keys.retainAll(allowed)

            _months.value = currentCache
        }
    }

    fun onPageChanged(newIndex: Int, baseIndex: Int) {
        val offset = newIndex - baseIndex
        val newMonth = baseYearMonth.plusMonths(offset.toLong())

        if (_currentYearMonth.value == newMonth) return

        _currentYearMonth.value = newMonth
        preloadMonths(newMonth)
    }
}