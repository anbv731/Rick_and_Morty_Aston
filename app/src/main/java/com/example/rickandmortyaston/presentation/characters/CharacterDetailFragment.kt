package com.example.rickandmortyaston.presentation.characters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmortyaston.R
import com.example.rickandmortyaston.databinding.CharacterDetailsBinding
import com.example.rickandmortyaston.di.RaMComponentProvider
import com.example.rickandmortyaston.domain.characters.CharacterDomain
import com.example.rickandmortyaston.presentation.episodes.EpisodeDetailFragment
import com.example.rickandmortyaston.presentation.locations.LocationDetailFragment
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class CharacterDetailFragment : Fragment() {
    companion object {
        const val ARGUMENT: String = "id"
    }

    @Inject
    lateinit var viewModel: CharacterDetailViewModel
    private lateinit var binding: CharacterDetailsBinding
    private lateinit var textViewName: TextView
    private lateinit var textViewStatus: TextView
    private lateinit var textViewGender: TextView
    private lateinit var textViewOrigin: TextView
    private lateinit var textViewLocation: TextView
    private lateinit var textViewSpecies: TextView
    private lateinit var image: ImageView
    private lateinit var appBar: MaterialToolbar
    private lateinit var recycler: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as RaMComponentProvider).provideRaMComponent()
            .injectCharactersDetailFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().nav_view.visibility=View.GONE

        binding = CharacterDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        textViewOrigin = binding.textViewDetailOriginData
        textViewLocation = binding.textViewDetailLocationData
        textViewGender = binding.textViewDetailGenderData
        textViewName = binding.textViewDetailNameData
        textViewSpecies = binding.textViewDetailSpeciesData
        textViewStatus = binding.textViewDetailStatusData
        image = binding.imageViewCharacterDetail
        appBar = binding.topAppBarDetail
        recycler=binding.recyclerId
        progressBar=binding.progressBar
        appBar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = RecyclerAdapterCharactersDetail(requireContext()) { id -> toItem(id) }
        recycler.adapter = adapter
        val requestId: Int? = arguments?.getInt(ARGUMENT)
        if (requestId != null) {
            viewModel.getData(requestId)
            viewModel.character.observe(
                viewLifecycleOwner
            ) {
                containView(it)
                viewModel.getEpisodes(it)
            }
            viewModel.episodes.observe(
                viewLifecycleOwner
            ) {
                adapter.setList(it)
                progressBar.visibility = View.INVISIBLE
            }
            viewModel.errorMessage.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                progressBar.visibility = View.INVISIBLE
            }
        }
    }

    private fun containView(character: CharacterDomain) {
        textViewName.text = character.name
        appBar.title = character.name
        textViewStatus.text = character.status
        textViewSpecies.text = character.species
        textViewGender.text = character.gender
        textViewOrigin.text = character.originName
        textViewOrigin.setOnClickListener { toLocation(character.origin) }
        textViewLocation.setOnClickListener { toLocation(character.location) }
        textViewLocation.text=character.locationName
        Glide.with(requireContext())
            .load(character.image)
            .into(image)
    }
    private fun toLocation(id: String) {
        if(id.isNotEmpty()) {
            val arg = Bundle()
            arg.putInt("id", id.toInt())
            val fragment = LocationDetailFragment()
            fragment.arguments = arg
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentPlace, fragment, null)
                ?.addToBackStack(null)
                ?.commit()
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
}