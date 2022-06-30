package com.example.rickandmortyaston.presentation.locations

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
import com.example.rickandmortyaston.databinding.LocationsFragmentBinding
import com.example.rickandmortyaston.di.RaMComponentProvider
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationsFragment : Fragment() {
    @Inject
    lateinit var viewModel: LocationsViewModel
    private lateinit var binding: LocationsFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: RecyclerAdapterLocations
    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var dialog: DialogFragment
    private lateinit var toolbar: MaterialToolbar

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as RaMComponentProvider).provideRaMComponent()
            .injectLocationsFragment(this)
    }

    override fun onResume() {
        super.onResume()
        requireActivity().nav_view.visibility=View.VISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LocationsFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        recyclerView = binding.recycler
        searchView = binding.searchViewId
        progressBar = binding.progressBar
        swipe = binding.swipe
        toolbar = binding.AppBarId
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_type -> filtration()
                R.id.action_dimension -> filtration()
            }
            true
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RecyclerAdapterLocations(requireContext(), { id -> toItem(id) }, { nextPage() })
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
        viewModel.locations.observe(viewLifecycleOwner) {
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
//        val fragment = EpisodeDetailFragment()
//        fragment.arguments = arg
//        activity?.supportFragmentManager?.beginTransaction()
//            ?.replace(R.id.fragmentPlace, fragment, null)
//            ?.addToBackStack(null)
//            ?.commit()
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

        } catch (e: Exception) {
        }
        args.putInt("selected", selected)
        return args
    }

    private fun filtration() {
        dialog = DialogLocations()
        dialog.arguments = makeListSeasons()
        dialog.show(childFragmentManager, "Dialog")
    }

    fun input(value: Int) {
        viewModel.changeType(value)
    }
}