package com.example.vlad.notekeeper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_edit_note.*
import kotlinx.android.synthetic.main.edit_note_content.*

class EditNoteActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NOTE_POSITION = "EXTRA_NOTE_POSITION"
        const val POSITION_NOT_SET = -1
    }

    private var notePosition = POSITION_NOT_SET

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        //toolbar init

        setSupportActionBar(toolbar_edit_note)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //spinner init
        val spinnerAdapter = ArrayAdapter<CourseInfo>(
                this,
                android.R.layout.simple_spinner_item,
                DataManager.courses.values.toList())
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner_courses.adapter = spinnerAdapter

        //Get extras
        notePosition = savedInstanceState?.getInt(EXTRA_NOTE_POSITION, POSITION_NOT_SET) ?:
                intent.getIntExtra(EXTRA_NOTE_POSITION, POSITION_NOT_SET)

        if(notePosition != POSITION_NOT_SET)
            displayNote()
        else{
            DataManager.notes.add(NoteInfo())
            notePosition = DataManager.notes.lastIndex
        }
    }

    override fun onPause() {
        super.onPause()
        saveNote()
    }

    private fun saveNote() {
        val note = DataManager.notes[notePosition]
        note.title = et_title.text.toString()
        note.text = et_text.text.toString()
        note.course = spinner_courses.selectedItem as CourseInfo
    }

    private fun displayNote() {
        val note = DataManager.notes[notePosition]

        et_title.setText(note.title)
        et_text.setText(note.text)

        var courseSpinnerPosition = DataManager.courses.values.indexOf(note.course)
        spinner_courses.setSelection(courseSpinnerPosition)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_next -> {
                moveToNextNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun moveToNextNote() {
        ++notePosition
        displayNote()
        invalidateOptionsMenu()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(EXTRA_NOTE_POSITION, notePosition)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if(notePosition >= DataManager.notes.lastIndex){
            val menuItem = menu?.findItem(R.id.action_next)
            if(menuItem != null){
                menuItem.icon = ContextCompat.getDrawable(this, R.drawable.ic_block_white_24dp)
                menuItem.isEnabled = false
            }
        }

        return super.onPrepareOptionsMenu(menu)
    }
}
