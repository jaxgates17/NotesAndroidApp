package com.example.notesapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapplication.Note
import com.example.notesapplication.NoteRepository
import kotlinx.coroutines.launch
import java.util.*


class NoteAddViewModel : ViewModel() {

    fun addNote(title: String, description: String, date: Date) {
        val newNote = Note(
            id = UUID.randomUUID(),
            title = title,
            description = description,
            date = date
        )

        viewModelScope.launch {
            NoteRepository.get().addNote(newNote)
        }
    }
}