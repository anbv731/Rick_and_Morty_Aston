package com.example.rickandmortyaston.presentation.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyaston.databinding.CharactersFragmentBinding
import java.util.*

class CharactersFragment: Fragment() {
    lateinit var viewModel: CharactersViewModel
    private lateinit var binding: CharactersFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: RecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CharactersFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
       // recyclerView = binding.recyclerCharacters
        searchView = binding.searchViewId
        progressBar = binding.progressBar
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RecyclerAdapter(requireContext()) { id -> toItem() }
       recyclerView.adapter = adapter
    }
    private  fun toItem(){}

    private fun setContent(query: String?) {
        if (query == null) {
            viewModel.characters.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    adapter.setList(it)
                    progressBar.visibility = View.INVISIBLE
                }
            }
            viewModel.errorMessage.observe(viewLifecycleOwner) {
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        } else {
            viewModel.characters.observe(viewLifecycleOwner) {
                adapter.setList(it.filter { character ->
                    character.name.lowercase(Locale.getDefault())
                        .contains(query.lowercase(Locale.getDefault()))
                })
            }
        }
    }
}