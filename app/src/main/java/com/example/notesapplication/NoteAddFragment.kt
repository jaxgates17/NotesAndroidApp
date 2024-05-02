package com.example.notesapplication

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notesapplication.databinding.FragmentNoteAddBinding
import java.util.Calendar

class NoteAddFragment : Fragment() {

    private var _binding: FragmentNoteAddBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NoteAddViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isDarkMode = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES

        val textColor = if (isDarkMode) Color.WHITE else Color.BLACK
        binding.editTextTitle.setTextColor(textColor)
        binding.editTextDescription.setTextColor(textColor)

        binding.btnSave.setOnClickListener {
            val title = binding.editTextTitle.text.toString()
            val description = binding.editTextDescription.text.toString()
            val currentDate = Calendar.getInstance().time

            if (title.isNotEmpty() && description.isNotEmpty()) {
                viewModel.addNote(
                    title,
                    description,
                    currentDate
                )

                findNavController().popBackStack()
            } else {

                Toast.makeText(requireContext(), "Please provide both title and description", Toast.LENGTH_SHORT).show()
            }
        }

        
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}