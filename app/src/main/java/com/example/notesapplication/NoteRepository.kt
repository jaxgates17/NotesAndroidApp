package com.example.notesapplication

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.notesapplication.Database.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

private const val DATABASE_NAME = "note-database2"

class NoteRepository private constructor(context: Context, private val coroutineScope: CoroutineScope = GlobalScope) {

    private val database: NoteDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            DATABASE_NAME
        )
        .build()

    fun getNotes(): Flow<List<Note>> = database.NoteDao().getNotes()
    suspend fun getNoteById(id: UUID): Note = database.NoteDao().getNote(id)

    fun addNote(note: Note) {
        coroutineScope.launch {
            database.NoteDao().addNote(note)
        }
    }

    fun updateNote(note: Note) {
        coroutineScope.launch {
            Log.d(TAG, "Updating note: $note")
            database.NoteDao().updateNote(note)
            Log.d(TAG, "Note updated successfully")
        }
    }

    fun deleteNote(note: Note) {
        coroutineScope.launch {
            database.NoteDao().deleteNote(note)
        }
    }

    companion object {
        private var INSTANCE: NoteRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = NoteRepository(context)
            }
        }

        fun get(): NoteRepository {
            return INSTANCE ?: throw IllegalStateException("NoteRepository must be initialized")
        }
    }
}