package fts.ahmed.diaryapp.data.Repository

import androidx.annotation.WorkerThread
import fts.ahmed.diaryapp.data.room.DiariesDao
import fts.ahmed.diaryapp.pojo.Diary
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(private val dao: DiariesDao) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(diary: Diary) {
        dao.insert(diary)
    }

    suspend fun delete(diary: Diary) {
        dao.delete(diary)
    }

    fun getAllDiaries(): Flow<List<Diary>> {
        return dao.getAll()
    }
    fun searchDiary(searchKey:String): Flow<List<Diary>> {
        return dao.searchDiary(searchKey)
    }

}