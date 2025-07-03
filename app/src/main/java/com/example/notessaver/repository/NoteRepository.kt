package com.example.notessaver.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.notessaver.api.NotesApi
import com.example.notessaver.models.NotesRequest
import com.example.notessaver.models.NotesResponse
import com.example.notessaver.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteAPI: NotesApi) {

    private val _notesLiveData = MutableLiveData<NetworkResult<List<NotesResponse>>>()
    val notesLiveData get() = _notesLiveData

    private val _statusLiveData = MutableLiveData<NetworkResult<Pair<Boolean, String>>>()
    val statusLiveData get() = _statusLiveData

    suspend fun createNote(noteRequest: NotesRequest) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = noteAPI.createNote(noteRequest)
        handleResponse(response, "Note Created")
    }

    suspend fun getNotes() {
        _notesLiveData.postValue(NetworkResult.Loading())
        val response = noteAPI.getNotes()
        if (response.isSuccessful && response.body() != null) {
            val notesList = response.body()!!
//            notesDao.insertNotes(notesList)
            _notesLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _notesLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _notesLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }

    suspend fun deleteNote(noteId: String) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = noteAPI.deleteNote(noteId)
        if (response.isSuccessful) {
            getNotes()
        }
        Log.d("NOTEDELETE",response.toString())
        handleResponse(response, "Note Deleted")
    }

    suspend fun updateNote(id: String, noteRequest: NotesRequest) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = noteAPI.updateNote(id, noteRequest)
        if (response.isSuccessful) {
            getNotes()
        }
        handleResponse(response, "Note Updated")
    }

    private fun  handleResponse(response: Response<NotesResponse>, message: String) {
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResult.Success(Pair(true, message)))
        } else {
            _statusLiveData.postValue(NetworkResult.Success(Pair(false, "Something went wrong")))
        }
    }
}