package com.example.notesapplication

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID
import java.util.Date

@Entity
data class Note (
    @PrimaryKey val id: UUID,
    val title: String,
    val description: String,
    val date: Date,

)