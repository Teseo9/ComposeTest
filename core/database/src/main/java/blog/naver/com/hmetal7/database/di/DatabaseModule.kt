package blog.naver.com.hmetal7.database.di

import android.app.Application
import androidx.room.Room
import blog.naver.com.hmetal7.database.calendar.CalendarDao
import blog.naver.com.hmetal7.database.calendar.CalendarDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): CalendarDatabase =
        Room.databaseBuilder(app, CalendarDatabase::class.java, "calendar.db")
            .createFromAsset("calendar.db") // 이미 준비된 DB 사용 시
            .build()

    @Provides
    fun provideCalendarDao(db: CalendarDatabase): CalendarDao = db.calendarDao()
}