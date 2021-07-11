package ru.narod.pentiux.homeworkfordoubletapp.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.narod.pentiux.homeworkfordoubletapp.data.DataManager
import ru.narod.pentiux.homeworkfordoubletapp.data.DataManagerImp
import ru.narod.pentiux.homeworkfordoubletapp.data.dao.HabitsDao
import ru.narod.pentiux.homeworkfordoubletapp.data.db.HabitsDatabase
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.model.HabitModel
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.model.HabitModelImpl
import ru.narod.pentiux.homeworkfordoubletapp.usecases.DataMapper
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

    @Singleton
    @Provides
    fun provideDataManager(habitsDao: HabitsDao):DataManager = DataManagerImp(habitsDao)

    @Singleton
    @Provides
    fun provideHabitModel(dataManager: DataManager): HabitModel = HabitModelImpl(dataManager)
}