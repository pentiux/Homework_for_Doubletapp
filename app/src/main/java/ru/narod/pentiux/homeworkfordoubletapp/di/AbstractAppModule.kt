package ru.narod.pentiux.homeworkfordoubletapp.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import ru.narod.pentiux.homeworkfordoubletapp.data.DataManager
import ru.narod.pentiux.homeworkfordoubletapp.data.DataManagerImp
import ru.narod.pentiux.homeworkfordoubletapp.data.dao.HabitsDao
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.model.HabitModel
import ru.narod.pentiux.homeworkfordoubletapp.mvvm.model.HabitModelImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AbstractAppModule {

    @Binds
    @Singleton
    abstract fun getHabitModel(habitModelImpl: HabitModelImpl): HabitModel

    @Singleton
    @Binds
    abstract fun provideDataManager(dataManagerImp: DataManagerImp): DataManager

}