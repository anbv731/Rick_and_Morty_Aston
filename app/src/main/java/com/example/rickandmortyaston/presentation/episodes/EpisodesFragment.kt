package com.example.rickandmortyaston.presentation.episodes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.rickandmortyaston.R
import com.example.rickandmortyaston.databinding.EpisodesFragmentBinding
import com.example.rickandmortyaston.di.CharactersComponentProvider
import com.example.rickandmortyaston.presentation.characters.Dialog
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodesFragment : Fragment() {
    @Inject
    lateinit var viewModel: EpisodesViewModel
    private lateinit var binding: EpisodesFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: RecyclerAdapterEpisodes
    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var dialog: DialogFragment
    private lateinit var toolbar: MaterialToolbar

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as CharactersComponentProvider).provideCharactersComponent()
            .injectEpisodesFragment(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EpisodesFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        recyclerView = binding.recycler
        searchView = binding.searchViewId
        progressBar = binding.progressBar
        swipe = binding.swipe
        toolbar = binding.AppBarId
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_season -> filtration()
            }
            true
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RecyclerAdapterEpisodes(requireContext(), { id -> toItem(id) }, { nextPage() })
        recyclerView.adapter = adapter
        setContent()
        swipe.setOnRefreshListener {
            viewModel.refreshData()
            swipe.isRefreshing = false
        }
        var queryTextChangedJob: Job? = null
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(text: String): Boolean {
                queryTextChangedJob?.cancel()
                queryTextChangedJob = lifecycleScope.launch {
                    delay(1000)
                    viewModel.changeName(text)
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.changeName(query)
                return false
            }
        })
    }

    private fun setContent() {
        viewModel.episodes.observe(viewLifecycleOwner) {
            adapter.setList(it)
            progressBar.visibility = View.INVISIBLE
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            progressBar.visibility = View.INVISIBLE
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }
    }

    private fun toItem(id: Int) {
        val arg = Bundle()
        arg.putInt("id", id)
        val fragment = EpisodeDetailFragment()
        fragment.arguments = arg
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragmentPlace, fragment, null)
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun nextPage() {
        progressBar.visibility = View.VISIBLE
        viewModel.nextPage()
    }

    private fun makeListSeasons(): Bundle {
        val args = Bundle()
        val list = mutableListOf<String>()
        for (i in 1..5) {
            list.add("Season $i")
        }
        args.putStringArray("list", list.toTypedArray())
        var selected = -1
        try {
            selected = viewModel.getSeason()
        } catch (e: Exception) {
        }
        args.putInt("selected", selected)
        return args
    }

    private fun filtration() {
        dialog = DialogEpisodes()
        dialog.arguments = makeListSeasons()
        dialog.show(childFragmentManager, "Dialog")
    }

    fun input(value: Int) {
        viewModel.changeEpisode(value)
    }
}