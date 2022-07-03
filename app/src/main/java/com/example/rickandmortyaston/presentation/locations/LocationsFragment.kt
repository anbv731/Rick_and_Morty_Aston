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
import com.example.rickandmortyaston.domain.locations.RequestLocation
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
    private var filterId=0


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
                R.id.action_type -> filtration(0)
                R.id.action_dimension -> filtration(1)
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
        val fragment = LocationDetailFragment()
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

    private fun makeTypeList(): Bundle {
        val args = Bundle()
        val list = RequestLocation.typeLocations
        args.putStringArray("list", list)
        val selected = list.indexOf(viewModel.request.type)
        args.putInt("selected", selected)
        return args
    }
    private fun makeDimensionsList(): Bundle {
        val args = Bundle()
        val list = RequestLocation.dimension
        args.putStringArray("list", list)
        val selected = list.indexOf(viewModel.request.dimension)
        args.putInt("selected", selected)
        return args
    }

    private fun filtration(item:Int) {
        dialog = DialogLocations()
        when(item){
            0->dialog.arguments = makeTypeList()
            1->dialog.arguments = makeDimensionsList()
        }
        filterId=item
        dialog.show(childFragmentManager, "Dialog")
    }

    fun input(value: Int) {
        when(filterId) {
            0 -> viewModel.changeType(value)
            1 -> viewModel.changeDimension(value)
        }
    }
}