package com.example.notessaver.api

import com.example.notessaver.models.NotesRequest
import com.example.notessaver.models.NotesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotesApi {


    @POST("/notes")
    suspend fun createNote(@Body noteRequest: NotesRequest): Response<NotesResponse>

    @GET("/notes")
    suspend fun getNotes(): Response<List<NotesResponse>>

    @DELETE("/notes/{noteId}")
    suspend fun deleteNote(@Path("noteId") noteId: String) : Response<NotesResponse>

    @PUT("/notes/{noteId}")
    suspend fun updateNote(
        @Path("noteId") noteId: String,
        @Body noteRequest: NotesRequest
    ): Response<NotesResponse>
}