package com.example.notessaver

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notessaver.databinding.NoteItemBinding
import com.example.notessaver.models.NotesResponse



class NoteAdapter(private val onNoteClicked: (NotesResponse) -> Unit) :
    ListAdapter<NotesResponse, NoteAdapter.NoteViewHolder>(ComparatorDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        note?.let {
            holder.bind(it)
        }
    }

    inner class NoteViewHolder(private val binding: NoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(note: NotesResponse) {
            val colorTint = getRandomColor()
            binding.root.background.setTint(colorTint)
            binding.title.text = note.title
            binding.description.text = note.description
            binding.root.setOnClickListener {
                onNoteClicked(note)
            }
        }

    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<NotesResponse>() {
        override fun areItemsTheSame(oldItem: NotesResponse, newItem: NotesResponse): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: NotesResponse, newItem: NotesResponse): Boolean {
            return oldItem == newItem
        }
    }


    private fun getRandomColor(): Int {
        val colors = listOf(
            0xFFE3F2FD.toInt(), // Light Blue
            0xFFFFF9C4.toInt(), // Light Yellow
            0xFFC8E6C9.toInt(), // Light Green
            0xFFFFCDD2.toInt(), // Light Red
            0xFFD1C4E9.toInt(), // Light Purple
            0xFFF0F4C3.toInt(),
            0xFFB2DFDB.toInt(),
            0xFFF8BBD0.toInt(),
            0xFFE1BEE7.toInt(),
            0xFFDCEDC8.toInt(),
            0xFFBBDEFB.toInt(),
            0xFFFFECB3.toInt(),
            0xFFC5CAE9.toInt(),
            0xFFFBE9E7.toInt(),
            0xFFE0F7FA.toInt(),
            0xFFE8F5E9.toInt(),
            0xFFFFF8E1.toInt(),
            0xFFEDE7F6.toInt(),
            0xFFF3E5F5.toInt(),
            0xFFFFEBEE.toInt(),
            0xFFE0F2F1.toInt(),
            0xFFFFFDE7.toInt(),
            0xFFE6EE9C.toInt(),
            0xFFFFCCBC.toInt(),
            0xFFD7CCC8.toInt(),
            0xFFF9FBE7.toInt(),
            0xFFE1F5FE.toInt(),
            0xFFFCE4EC.toInt(),
            0xFFF1F8E9.toInt()
        )
        return colors.random()
    }

}