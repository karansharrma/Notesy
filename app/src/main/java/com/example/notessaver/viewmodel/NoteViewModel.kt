package com.example.notessaver.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notessaver.models.NotesRequest
import com.example.notessaver.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class NoteViewModel @Inject constructor(private val noteRepository: NoteRepository):ViewModel() {

    val notesLiveData get() = noteRepository.notesLiveData
    val statusLiveData get() = noteRepository.statusLiveData

    fun createNote(noteRequest: NotesRequest) {
        viewModelScope.launch {
            noteRepository.createNote(noteRequest)
        }
    }

    fun getAllNotes() {
        viewModelScope.launch {
            noteRepository.getNotes()
            Log.d("NoteViewModel", "SAARE NOTES AAGYE: ${noteRepository.notesLiveData.value}") }
    }

    fun updateNote(id: String, noteRequest: NotesRequest){
        viewModelScope.launch {
            noteRepository.updateNote(id, noteRequest)
        }
    }

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            noteRepository.deleteNote(noteId)
        }
    }

}