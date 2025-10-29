package blog.naver.com.hmetal7.model.calendar

data class DayInfo(
    val solYear: Int,
    val solMonth: Int,
    val solDay: Int,
    val lunYear: Int,
    val lunMonth: Int,
    val lunDay: Int,
    val lunLeaf: String,
    val ganjiKorYear: String,
    val ganjiKorMonth: String,
    val ganjiKorDay: String,
    val ganjiChiYear: String,
    val ganjiChiMonth: String,
    val ganjiChiDay: String
)