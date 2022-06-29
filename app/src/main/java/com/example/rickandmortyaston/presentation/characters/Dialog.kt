package com.example.rickandmortyaston.presentation.characters

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment


class Dialog : DialogFragment() {
    private lateinit var list:Array<String>
   private var checkedItem=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println(arguments?.getStringArray("list")?.size)
        list=arguments?.getStringArray("list") as Array<String>
        checkedItem=arguments?.getInt("selected")as Int
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val charactersFragment: CharactersFragment? =
                requireActivity().supportFragmentManager.findFragmentByTag("CharactersFragment") as CharactersFragment?
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Filters")
                .setSingleChoiceItems(
                    list, checkedItem
                ) { dialog, item ->
                    charactersFragment?.input(item)
                }
                .setPositiveButton("Ok"){id,dialog->
                }
                .setNegativeButton("Clear") { dialog, id ->
                    charactersFragment?.input(-1)
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}