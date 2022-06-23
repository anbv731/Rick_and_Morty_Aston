package com.example.rickandmortyaston.presentation.characters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.rickandmortyaston.databinding.CharacterDetailsBinding
import com.example.rickandmortyaston.di.CharactersComponentProvider
import com.example.rickandmortyaston.domain.characters.CharacterDomain
import com.google.android.material.appbar.MaterialToolbar
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
    private lateinit var textViewCreated: TextView
    private lateinit var textViewSpecies: TextView
    private lateinit var image: ImageView
    private lateinit var appBar: MaterialToolbar

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as CharactersComponentProvider).provideCharactersComponent()
            .injectCharactersDetailFragment(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = CharacterDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        textViewCreated = binding.textViewDetailCreatedData
        textViewGender = binding.textViewDetailGenderData
        textViewName = binding.textViewDetailNameData
        textViewSpecies = binding.textViewDetailSpeciesData
        textViewStatus = binding.textViewDetailStatusData
        image = binding.imageViewCharacterDetail
        appBar = binding.topAppBarDetail
        appBar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val requestId: Int? = arguments?.getInt(ARGUMENT)
        if (requestId != null) {
            viewModel.getData(requestId)
            viewModel.character.observe(
                viewLifecycleOwner
            ) { containView(it) }
        }
    }

    private fun containView(character: CharacterDomain) {
        textViewName.text = character.name
        appBar.title = character.name
        textViewStatus.text = character.status
        textViewSpecies.text = character.species
        textViewGender.text = character.gender
        textViewCreated.text = character.created.substring(0, 10)
        Glide.with(requireContext())
            .load(character.image)
            .into(image)
    }
}