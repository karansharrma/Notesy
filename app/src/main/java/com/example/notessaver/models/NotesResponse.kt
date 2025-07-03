package com.example.notessaver.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notes")
data class NotesResponse(
    @PrimaryKey(autoGenerate = false) val _id: String,
    val createdAt: String,
    val description: String,
    val title: String,
    val updatedAt: String,
    val userId: String
)