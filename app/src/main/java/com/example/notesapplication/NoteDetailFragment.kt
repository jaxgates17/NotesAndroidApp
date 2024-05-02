package com.example.notesapplication

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notesapplication.databinding.FragmentNoteDetailBinding

class NoteDetailFragment : Fragment() {
    private var _binding: FragmentNoteDetailBinding? = null
    private val binding get() = _binding!!
    private val args: NoteDetailFragmentArgs by navArgs()

    private val viewModel: NoteDetailViewModel by viewModels {
        NoteDetailViewModelFactory(args.noteId)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isDarkMode = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES

        val textColor = if (isDarkMode) Color.WHITE else Color.BLACK
        binding.editTextTitle.setTextColor(textColor)
        binding.editTextDescription.setTextColor(textColor)


        viewModel.note.observe(viewLifecycleOwner) { note ->

            note?.let { populateFields(it) }
        }

        binding.btnSave.setOnClickListener {
            saveNoteChanges()
        }

        binding.btnDeleteNote.setOnClickListener {

            showDeleteConfirmationDialog()
            //viewModel.deleteNote()
        }
    }
    private fun showDeleteConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete Note")
            .setMessage("Are you sure you want to delete this note?")
            .setPositiveButton("Yes") { dialog, _ ->
                viewModel.deleteNote()
                dialog.dismiss()
                findNavController().navigateUp()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun populateFields(note: Note) {
        binding.editTextTitle.setText(note.title)
        binding.editTextDescription.setText(note.description)
    }

    private fun saveNoteChanges() {
        val newTitle = binding.editTextTitle.text.toString()
        val newDescription = binding.editTextDescription.text.toString()

        if (newTitle.isNotEmpty() && newDescription.isNotEmpty()) {
            Log.d(TAG, newTitle +  newDescription)
            viewModel.updateNoteTitle(newTitle)
            viewModel.updateNoteDescription(newDescription)
            viewModel.saveNoteChanges()

            findNavController().navigateUp()
        } else {

            Toast.makeText(requireContext(), "Please provide both title and description", Toast.LENGTH_SHORT).show()
        }


        //Log.d(TAG, "Current note before saving changes: ${viewModel.note.value}")



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}