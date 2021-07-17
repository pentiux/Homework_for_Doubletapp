package ru.narod.pentiux.homeworkfordoubletapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import ru.narod.pentiux.homeworkfordoubletapp.MyApplication
import ru.narod.pentiux.homeworkfordoubletapp.data.DataManager
import ru.narod.pentiux.homeworkfordoubletapp.data.DataManagerImp
import ru.narod.pentiux.homeworkfordoubletapp.data.dao.HabitsDao
import ru.narod.pentiux.homeworkfordoubletapp.data.db.HabitsDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext applicationContext: Context): HabitsDatabase =
        Room.databaseBuilder(
            applicationContext,
            HabitsDatabase::class.java,
            "habits_database1"
        ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideDao(db: HabitsDatabase): HabitsDao = db.getDao()

}