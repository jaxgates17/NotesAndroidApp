package com.example.notesapplication
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapplication.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotesListViewModel : ViewModel() {

    private val noteRepository = NoteRepository.get()

    private val _notes: MutableStateFlow<List<Note>> = MutableStateFlow(emptyList())
    val notes: StateFlow<List<Note>> get() = _notes

    init {
        fetchNotes()
    }
    private fun fetchNotes() {
        viewModelScope.launch {
            noteRepository.getNotes().collect { notes ->
                _notes.value = notes
            }
        }
    }
}