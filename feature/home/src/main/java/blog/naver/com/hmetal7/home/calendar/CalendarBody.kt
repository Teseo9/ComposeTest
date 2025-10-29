package blog.naver.com.hmetal7.home.calendar

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.time.YearMonth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.sp
import java.time.LocalDate

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun CalendarBody(
    modifier: Modifier = Modifier,
    currentMonth: YearMonth,
    selectedDate: LocalDate?,
    onDateClick: (LocalDate) -> Unit = {}
) {
    val days = remember(currentMonth) { generateMonthDays(currentMonth) }
    val rowCount = remember(currentMonth) { getRowCountForMonth(currentMonth) }
    val today = remember { LocalDate.now() }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val density = LocalDensity.current
        val dayOfWeekHeightDp = 24.dp
        val spacerHeightDp = 8.dp
        val headerHeightDp = dayOfWeekHeightDp + spacerHeightDp

        val gridHeightDp = maxHeight - headerHeightDp
        val cellHeightDp = gridHeightDp / rowCount

        Column(modifier = Modifier.fillMaxSize()) {
            // 날짜 그리드
            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                modifier = Modifier.height(gridHeightDp),
                userScrollEnabled = false
            ) {
                items(days) { date ->
                    val isToday = date == today
                    val isSelected = date == selectedDate

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
                            .background(backgroundColor, shape = RoundedCornerShape(6.dp))
                            .clickable(enabled = date != null) {
                                date?.let { onDateClick(it) }
                            }
                            .padding(4.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = date?.dayOfMonth?.toString() ?: "",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (date != null) "음력 5.3" else "",
                            fontSize = 10.sp,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = if (date != null && date.dayOfMonth == 17) "생일🎂" else "",
                            fontSize = 10.sp,
                            color = Color.Red,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

fun generateMonthDays(currentMonth: YearMonth): List<LocalDate?> {
    val firstDay = currentMonth.atDay(1)
    val lastDay = currentMonth.atEndOfMonth()
    val startOffset = firstDay.dayOfWeek.value % 7
    val totalDays = lastDay.dayOfMonth

    val days = mutableListOf<LocalDate?>()

    // 앞에 비어 있는 칸
    repeat(startOffset) {
        days.add(null)
    }

    // 날짜 채우기
    for (i in 1..totalDays) {
        days.add(currentMonth.atDay(i))
    }

    // 7의 배수로 맞춰 빈칸 채우기
    while (days.size % 7 != 0) {
        days.add(null)
    }

    return days
}

//fun getRowCountForMonth(currentMonth: YearMonth): Int {
//    val firstDay = currentMonth.atDay(1)
//    val lastDay = currentMonth.atEndOfMonth()
//    val startOffset = firstDay.dayOfWeek.value % 7
//    val totalDays = lastDay.dayOfMonth
//    val totalCells = startOffset + totalDays
//    return (totalCells + 6) / 7 // 올림 계산
//}