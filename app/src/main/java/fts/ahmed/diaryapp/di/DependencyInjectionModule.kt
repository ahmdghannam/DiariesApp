package fts.ahmed.diaryapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fts.ahmed.diaryapp.data.room.DiariesDao
import fts.ahmed.diaryapp.data.room.LocalDataBase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DependencyInjectionModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context:Context):LocalDataBase{
        return Room.databaseBuilder(
            context.applicationContext
            ,LocalDataBase::class.java
            ,"students_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideTheDao(dataBase: LocalDataBase):DiariesDao{
        return dataBase.diariesDao()
    }



}