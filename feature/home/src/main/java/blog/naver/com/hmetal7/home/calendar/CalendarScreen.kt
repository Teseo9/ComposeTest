package blog.naver.com.hmetal7.home.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import blog.naver.com.hmetal7.model.calendar.DayInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val months by viewModel.months.collectAsState()
    val currentYearMonth by viewModel.currentYearMonth.collectAsState()
    val basePage = 1000
    val pagerState = rememberPagerState(
        initialPage = basePage,
        pageCount = { 2000 }
    )
    val coroutineScope = rememberCoroutineScope()

    val dayOfWeekHeightDp = 24.dp
    val spacerHeightDp = 8.dp
    val headerHeightDp = dayOfWeekHeightDp + spacerHeightDp

    // Page 변경 감지
    LaunchedEffect(pagerState.settledPage) {
        viewModel.onPageChanged(pagerState.settledPage, basePage)
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(top = 4.dp),
        verticalArrangement = Arrangement.Center
    ) {
        CalendarHeader(
            currentMonth = currentYearMonth,
            onPreviousMonth = {
                val prevPage = pagerState.currentPage - 1
                viewModel.onPageChanged(prevPage, basePage)
                coroutineScope.launch {
                    pagerState.animateScrollToPage(prevPage)
                }
            },
            onNextMonth = {
                val nextPage = pagerState.currentPage + 1
                viewModel.onPageChanged(nextPage, basePage)
                coroutineScope.launch {
                    pagerState.animateScrollToPage(nextPage)
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 요일 헤더
        Row(
            modifier = Modifier
                .fillMaxWidth().padding(start = 16.dp, end = 16.dp)
                .height(dayOfWeekHeightDp)
        ) {
            listOf("일", "월", "화", "수", "목", "금", "토").forEach {
                Text(
                    text = it,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(spacerHeightDp))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            // 현재 페이지에서 보여줄 달 계산
            val offset = page - basePage
            val targetMonth = remember(offset) { YearMonth.now().plusMonths(offset.toLong()) }

            // 해당월 데이터 조회
            val daysData = months[targetMonth] ?: emptyList()

            // 월별 달력 데이터 생성
            val days = generateMonthDays(targetMonth, daysData)

            // 행 수 계산 (targetMonth 기준)
            val rowCount = getRowCountForMonth(targetMonth)

            val today = remember { LocalDate.now() }

            BoxWithConstraints(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp).fillMaxSize()
            ) {
                val gridHeightDp = maxHeight - headerHeightDp
                val cellHeightDp = gridHeightDp / rowCount

                Column(modifier = Modifier.fillMaxSize()) {
                    // 날짜 그리드
                    LazyVerticalGrid(columns = GridCells.Fixed(7)) {
                        items(days) { day ->
                            if (day != null) {
                                val isToday = (day.solYear == today.year)
                                        && (day.solMonth == today.monthValue)
                                        && (day.solDay == today.dayOfMonth)
                                val isSelected = false // date == selectedDate

                                val backgroundColor = when {
                                    isSelected -> Color.Gray
                                    isToday -> Color(0xFF90CAF9)
                                    else -> Color.Transparent
                                }

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(cellHeightDp)
                                        .padding(2.dp)
                                        .background(backgroundColor, shape = RoundedCornerShape(6.dp)),
//                                    .clickable(enabled = date != null) {
//                                        date?.let { onDateClick(it) }
//                                    }
//                                        .padding(4.dp),
                                    verticalArrangement = Arrangement.Top,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = day.solDay.toString(),
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = day.lunDay.toString(),
                                        fontSize = 10.sp,
                                        color = Color.DarkGray,
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = day.ganjiKorDay,
                                        fontSize = 10.sp,
                                        color = Color.DarkGray,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun generateMonthDays(currentMonth: YearMonth, dayInfo: List<DayInfo>): List<DayInfo?> {
    val firstDay = currentMonth.atDay(1)
    val lastDay = currentMonth.atEndOfMonth()
    val startOffset = firstDay.dayOfWeek.value % 7

    val days = mutableListOf<DayInfo?>()

    // 앞에 비어 있는 칸
    repeat(startOffset) {
        days.add(null)
    }

    // 날짜 채우기
    days.addAll(dayInfo)

    // 7의 배수로 맞춰 빈칸 채우기
    while (days.size % 7 != 0) {
        days.add(null)
    }

    return days
}

fun getRowCountForMonth(currentMonth: YearMonth): Int {
    val firstDay = currentMonth.atDay(1)
    val lastDay = currentMonth.atEndOfMonth()
    val startOffset = firstDay.dayOfWeek.value % 7
    val totalDays = lastDay.dayOfMonth
    val totalCells = startOffset + totalDays
    return (totalCells + 6) / 7 // 올림 계산
}