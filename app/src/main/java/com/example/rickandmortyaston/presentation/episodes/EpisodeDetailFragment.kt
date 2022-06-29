package com.example.rickandmortyaston.presentation.episodes

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
import com.example.rickandmortyaston.databinding.EpisodeDetailBinding
import com.example.rickandmortyaston.di.CharactersComponentProvider
import com.example.rickandmortyaston.di.EpisodesComponentProvider
import com.example.rickandmortyaston.domain.characters.CharacterDomain
import com.example.rickandmortyaston.domain.episodes.EpisodeDomain
import com.example.rickandmortyaston.presentation.characters.CharacterDetailViewModel
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.episode_detail.*
import javax.inject.Inject

class EpisodeDetailFragment : Fragment() {
    companion object {
        const val ARGUMENT: String = "id"
    }

    @Inject
    lateinit var viewModel: EpisodesDetailViewModel
    private lateinit var binding: EpisodeDetailBinding
    private lateinit var textViewName: TextView
    private lateinit var textViewEpisode: TextView
    private lateinit var textViewAirDate: TextView
    private lateinit var appBar: MaterialToolbar

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as EpisodesComponentProvider).provideEpisodesComponent()
            .injectEpisodesDetailFragment(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = EpisodeDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        textViewAirDate = binding.textViewDetailAirDateData
        textViewEpisode = binding.textViewDetailEpisodeData
        textViewName = binding.textViewDetailNameData
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
            viewModel.episode.observe(
                viewLifecycleOwner
            ) { containView(it) }
        }
    }

    private fun containView(episode: EpisodeDomain) {
        textViewName.text = episode.name
        appBar.title = episode.name
        textViewEpisode.text = episode.episode
        textViewAirDate.text = episode.airDate
    }
}