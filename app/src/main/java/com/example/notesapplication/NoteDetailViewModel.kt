package com.example.notesapplication

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class NoteDetailViewModel(private val noteId: String) : ViewModel() {
    private val noteRepository = NoteRepository.get()

    private val _note: MutableLiveData<Note> = MutableLiveData()
    val note: LiveData<Note> get() = _note

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val uuidNoteId = UUID.fromString(noteId)
                val note = noteRepository.getNoteById(uuidNoteId)
                _note.postValue(note)
            }
        }
    }

    fun updateNoteTitle(newTitle: String) {
        _note.value?.let { oldNote ->
            val updatedNote = oldNote.copy(title = newTitle)
            _note.value = updatedNote
            Log.d(TAG, "Updated note title: $updatedNote")
        }
    }

    fun updateNoteDescription(newDescription: String) {
        _note.value?.let { oldNote ->
            val updatedNote = oldNote.copy(description = newDescription)
            _note.value = updatedNote
            Log.d(TAG, "Updated note description: $updatedNote")
        }
    }

    fun saveNoteChanges() {

        _note.value?.let { updatedNote ->
            Log.d(TAG, "Updated note before saving changes: $updatedNote")
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    noteRepository.updateNote(updatedNote)
                }
            }
        }
    }

    fun deleteNote() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val uuidNoteId = UUID.fromString(noteId)
                val note = noteRepository.getNoteById(uuidNoteId)
                noteRepository.deleteNote(note)
            }
        }
    }
}

class NoteDetailViewModelFactory(private val noteId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteDetailViewModel(noteId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}