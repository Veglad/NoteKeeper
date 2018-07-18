package com.example.vlad.notekeeper

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {view ->
            val intent = Intent(this, EditNoteActivity::class.java)
            startActivity(intent)
        }

        listNotes.adapter = ArrayAdapter<NoteInfo>(
                this,
                android.R.layout.simple_list_item_1,
                DataManager.notes
                )

        listNotes.setOnItemClickListener{parent, view, position, id ->
            val intentEditNote = Intent(this, EditNoteActivity::class.java)
            intentEditNote.putExtra(EditNoteActivity.EXTRA_NOTE_POSITION, position)
            startActivity(intentEditNote)
        }
    }

    override fun onResume() {
        super.onResume()
        (listNotes.adapter as ArrayAdapter<NoteInfo>).notifyDataSetChanged()
    }
}
