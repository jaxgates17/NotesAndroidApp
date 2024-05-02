package com.example.notesapplication.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.notesapplication.Note

@Database(entities = [ Note::class ], version=1)
@TypeConverters(NoteTypeConverters::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun NoteDao(): NoteDao

}