package com.example.notesapplication

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class NotesAdapter(
    private var notes: List<Note> = mutableListOf(),
    private val onNoteClicked: (noteId: String) -> Unit,

) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.textViewDescription)
        private val dateTextView: TextView = itemView.findViewById(R.id.textViewDate)

        fun bind(note: Note) {
            titleTextView.text = note.title
            descriptionTextView.text = note.description
            dateTextView.text = note.date.toString()

            itemView.setOnClickListener {
                Log.d(ContentValues.TAG, "yes")
                val noteIdString = note.id.toString()
                onNoteClicked(noteIdString)
                Log.d(ContentValues.TAG, noteIdString)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_note, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
    }

    fun submitList(newNotes: List<Note>) {
        notes = newNotes

    }


    override fun getItemCount() = notes.size
}