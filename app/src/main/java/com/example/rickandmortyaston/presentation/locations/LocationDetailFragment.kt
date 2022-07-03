package com.example.rickandmortyaston.presentation.locations

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyaston.R
import com.example.rickandmortyaston.databinding.EpisodeDetailBinding
import com.example.rickandmortyaston.databinding.LocationsDetailBinding
import com.example.rickandmortyaston.di.RaMComponentProvider
import com.example.rickandmortyaston.domain.episodes.EpisodeDomain
import com.example.rickandmortyaston.domain.locations.LocationDomain
import com.example.rickandmortyaston.presentation.characters.CharacterDetailFragment
import com.example.rickandmortyaston.presentation.episodes.RecyclerAdapterEpisodesDetail
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class LocationDetailFragment : Fragment() {
    companion object {
        const val ARGUMENT: String = "id"
    }

    @Inject
    lateinit var viewModel: LocationDetailViewModel
    private lateinit var binding: LocationsDetailBinding
    private lateinit var textViewName: TextView
    private lateinit var textViewType: TextView
    private lateinit var textViewDimension: TextView
    private lateinit var appBar: MaterialToolbar
    private lateinit var recycler: RecyclerView
    private  lateinit var progressBar:ProgressBar

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as RaMComponentProvider).provideRaMComponent()
            .injectLocationDetailFragment(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().nav_view.visibility=View.GONE
        binding = LocationsDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        textViewType = binding.textViewDetailTypeData
        textViewDimension = binding.textViewDetailDimensionData
        textViewName = binding.textViewDetailNameData
        recycler = binding.recyclerId
        appBar = binding.topAppBarDetail
        progressBar=binding.progressBar
        appBar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = RecyclerAdapterEpisodesDetail(requireContext()) { id -> toItem(id) }
        recycler.adapter=adapter
        val requestId: Int? = arguments?.getInt(ARGUMENT)
        if (requestId != null) {
            viewModel.getData(requestId)
            viewModel.location.observe(
                viewLifecycleOwner
            ) {
                containView(it)
                viewModel.getCharacters(it)
            }
            viewModel.characters.observe(
                viewLifecycleOwner
            ) {adapter.setList(it)
                progressBar.visibility=View.INVISIBLE
            }
            viewModel.errorMessage.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                progressBar.visibility=View.INVISIBLE
            }
        }
    }

    private fun containView(location:LocationDomain) {
        textViewName.text = location.name
        appBar.title = location.name
        textViewType.text = location.type
        textViewDimension.text = location.dimension
    }

    private fun toItem(id: Int) {
        val arg = Bundle()
        arg.putInt("id", id)
        val fragment = CharacterDetailFragment()
        fragment.arguments = arg
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragmentPlace, fragment, null)
            ?.addToBackStack(null)
            ?.commit()
    }
}