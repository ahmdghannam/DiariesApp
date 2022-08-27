package fts.ahmed.diaryapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fts.ahmed.diaryapp.data.Repository.Repository
import fts.ahmed.diaryapp.pojo.Diary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val repository: Repository) :ViewModel(){

    // all data
    val allDiaries:LiveData<List<Diary>> =repository.getAllDiaries().asLiveData()

    //searched data
    private val _searchDiaries = MutableStateFlow<List<Diary>>(emptyList())
    val searchDiaries: StateFlow<List<Diary>> = _searchDiaries
    fun searchDiaries(name: String) {
        viewModelScope.launch {
            repository.searchDiary(name).collect {
                _searchDiaries.emit(it)
            }
        }
    }

    // one item operation
    fun insert(diary: Diary) {
        viewModelScope.launch {
            repository.insert(diary)
        }
    }
    fun delete (diary: Diary){
        viewModelScope.launch {
            repository.delete(diary)
        }
    }


}