package fts.ahmed.diaryapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import fts.ahmed.diaryapp.pojo.Diary
import kotlinx.coroutines.flow.Flow

@Dao
interface DiariesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(diary: Diary)

    @Delete
    suspend fun delete(diary: Diary)

    @Query("select * from diaries_table")
    fun getAll(): Flow<List<Diary>>

    @Query("select * from diaries_table" +
            " where title like '%'||:searchKey||'%' or text like '%'||:searchKey||'%'")
    fun searchDiary(searchKey: String): Flow<List<Diary>>


}