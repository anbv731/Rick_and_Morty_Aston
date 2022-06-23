package com.example.rickandmortyaston.presentation.characters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.rickandmortyaston.R
import com.example.rickandmortyaston.databinding.CharactersFragmentBinding
import com.example.rickandmortyaston.di.CharactersComponentProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersFragment : Fragment() {
    @Inject
    lateinit var viewModel: CharactersViewModel
    private lateinit var binding: CharactersFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: RecyclerAdapter
    private lateinit var swipe: SwipeRefreshLayout

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as CharactersComponentProvider).provideCharactersComponent()
            .injectCharactersFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CharactersFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        recyclerView = binding.recyclerCharacters
        searchView = binding.searchViewId
        progressBar = binding.progressBar
        swipe = binding.swipeCharacters
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RecyclerAdapter(requireContext()) { id -> toItem(id) }
        recyclerView.adapter = adapter
        swipe.setOnRefreshListener {
           if(searchView.query==null) {viewModel.refreshData()}
            else{viewModel.searchData(searchView.query.toString())}

            swipe.isRefreshing = false
        }
        setContent(null)
        var queryTextChangedJob: Job? = null
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(text: String): Boolean {
                queryTextChangedJob?.cancel()
                queryTextChangedJob = lifecycleScope.launch {
                    delay(1000)
                    setContent(text)
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                setContent(query)
                return false
            }
        })
    }

    private fun setContent(query: String?) {
        if (query == null) {
            viewModel.characters.observe(viewLifecycleOwner) {
                adapter.setList(it)
                if(it.isEmpty()){Toast.makeText(requireContext(), R.string.nothingToShow, Toast.LENGTH_LONG).show()}
                progressBar.visibility = View.INVISIBLE
            }
            viewModel.errorMessage.observe(viewLifecycleOwner) {
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        } else {
            viewModel.searchData(query)
        }
    }
    private fun toItem(id: Int) {
        val arg = Bundle()
        arg.putInt("id",id)
        val fragment=CharacterDetailFragment()
        fragment.arguments=arg
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragmentPlace, fragment, null)
            ?.addToBackStack(null)
            ?.commit()
    }
}