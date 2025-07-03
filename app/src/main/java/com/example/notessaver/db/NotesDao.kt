//package com.example.notessaver.db
//
//import androidx.lifecycle.LiveData
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//
//
//
//import com.example.notessaver.models.NotesResponse
//
//
//@Dao
//interface NotesDao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertNotes(notesResponse: List<NotesResponse>)
//
//    @Query("SELECT * FROM notes")
//    fun getAllNotes(): LiveData<List<NotesResponse>>
//
//    @Query("DELETE FROM notes WHERE _id = :noteId")
//    suspend fun deleteNoteById(noteId: String)
//
//    @Query("UPDATE notes SET title = :title, description = :description WHERE _id = :noteId")
//    suspend fun updateNoteById(noteId: String, title: String, description: String)
//}
