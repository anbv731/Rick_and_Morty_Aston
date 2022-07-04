package com.example.rickandmortyaston.presentation.locations

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment


class DialogLocations : DialogFragment() {
    private lateinit var list: Array<String>
    private var checkedItem = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println(arguments?.getStringArray("list")?.size)
        list = arguments?.getStringArray("list") as Array<String>
        checkedItem = arguments?.getInt("selected") as Int
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val fragment: LocationsFragment? =
                requireActivity().supportFragmentManager.findFragmentByTag("LocationsFragment") as LocationsFragment?
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Filters")
                .setSingleChoiceItems(
                    list, checkedItem
                ) { dialog, item ->
                    fragment?.input(item)
                }
                .setPositiveButton("Ok") { id, dialog ->
                }
                .setNegativeButton("Clear") { dialog, id ->
                    fragment?.input(-1)
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}