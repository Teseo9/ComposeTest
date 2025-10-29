package blog.naver.com.hmetal7.database.calendar

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "calendar_date", primaryKeys =["sol_year", "sol_month", "sol_day"])
data class CalendarEntity(
    @ColumnInfo(name = "sol_year") val solYear: Int,
    @ColumnInfo(name = "sol_month") val solMonth: Int,
    @ColumnInfo(name = "sol_day") val solDay: Int,
    @ColumnInfo(name = "lun_year") val lunYear: Int,
    @ColumnInfo(name = "lun_month") val lunMonth: Int,
    @ColumnInfo(name = "lun_day") val lunDay: Int,
    @ColumnInfo(name = "lun_leaf") val lunLeaf: String,
    @ColumnInfo(name = "gj_k_year") val ganjiKorYear: String,
    @ColumnInfo(name = "gj_k_month") val ganjiKorMonth: String,
    @ColumnInfo(name = "gj_k_day") val ganjiKorDay: String,
    @ColumnInfo(name = "gj_c_year") val ganjiChiYear: String,
    @ColumnInfo(name = "gj_c_month") val ganjiChiMonth: String,
    @ColumnInfo(name = "gj_c_day") val ganjiChiDay: String
)