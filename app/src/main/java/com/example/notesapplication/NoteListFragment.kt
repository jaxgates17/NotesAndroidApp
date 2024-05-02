package com.example.notesapplication

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapplication.databinding.FragmentNotesListBinding
import kotlinx.coroutines.launch
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController


class NoteListFragment : Fragment() {

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!
    private val notes: List<Note> = mutableListOf()

    private val notesListViewModel: NotesListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewNotes.layoutManager = LinearLayoutManager(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                notesListViewModel.notes.collect { notes ->
                    binding.recyclerViewNotes.adapter = NotesAdapter(notes) { noteId ->
                        navigateToNoteDetail(noteId)
                    }
                }
            }
        }

        binding.btnAddNote.setOnClickListener {
            Log.d(TAG, "Clicked note id")
            findNavController().navigate(R.id.action_notesListFragment_to_noteAddFragment)
        }
        binding.btnToggleDarkMode.setOnClickListener {
            toggleDarkMode()

        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToNoteDetail(noteId: String) {
        val action = NoteListFragmentDirections.actionNoteListFragmentToNoteDetailFragment(noteId)
        findNavController().navigate(action)
    }

    private fun toggleDarkMode(): Boolean {
        val isDarkMode = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        val newMode =
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES
        AppCompatDelegate.setDefaultNightMode(newMode)

        binding.btnToggleDarkMode.text =
            if (newMode == AppCompatDelegate.MODE_NIGHT_YES) "Light Mode" else "Dark Mode"

        return !isDarkMode
    }
}