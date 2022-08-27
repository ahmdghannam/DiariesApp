package fts.ahmed.diaryapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import fts.ahmed.diaryapp.pojo.Diary

@Database(entities = [Diary::class], version = 3, exportSchema = false)
abstract class LocalDataBase :RoomDatabase() {
    abstract fun diariesDao(): DiariesDao
}