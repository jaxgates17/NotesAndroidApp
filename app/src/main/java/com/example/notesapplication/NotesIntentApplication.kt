package com.example.notesapplication

import android.app.Application

class NotesIntentApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        NoteRepository.initialize(this)
    }
}