package ru.narod.pentiux.homeworkfordoubletapp.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.narod.pentiux.homeworkfordoubletapp.data.db.HabitsDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext applicationContext: Context
    ) = Room.databaseBuilder(
        applicationContext,
        HabitsDatabase::class.java,
        "habits_database"
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideDao(db: HabitsDatabase) = db.getDao()
}