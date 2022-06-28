package com.example.rickandmortyaston.presentation.characters

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.rickandmortyaston.R
import com.example.rickandmortyaston.databinding.CharactersFragmentBinding
import com.example.rickandmortyaston.di.CharactersComponentProvider
import com.example.rickandmortyaston.domain.characters.Status
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersFragment : Fragment() {
    @Inject
    lateinit var viewModel: CharactersViewModel
    private lateinit var filter:ImageView
    private lateinit var binding: CharactersFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: RecyclerAdapter
    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var dialog:DialogFragment
    private lateinit var toolbar: MaterialToolbar
    private var filterId=0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as CharactersComponentProvider).provideCharactersComponent()
            .injectCharactersFragment(this)
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
        binding = CharactersFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        recyclerView = binding.recyclerCharacters
        searchView = binding.searchViewId
        progressBar = binding.progressBar
        swipe = binding.swipeCharacters
        toolbar=binding.AppBarId
        toolbar.setOnMenuItemClickListener {  when(it.itemId) {
            R.id.action_status -> filtration(0)
            R.id.action_gender->filtration(1)
            R.id.action_species->filtration(2)
            R.id.action_type->filtration(3)

        }
            true }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RecyclerAdapter(requireContext(), { id -> toItem(id) }, { nextPage() })
        recyclerView.adapter = adapter
        setContent()
        swipe.setOnRefreshListener {
            if (searchView.query.isEmpty()) {
                viewModel.refreshData()
            } else {
                viewModel.searchData(searchView.query.toString())
            }
            swipe.isRefreshing = false
        }
        var queryTextChangedJob: Job? = null
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(text: String): Boolean {
                queryTextChangedJob?.cancel()
                queryTextChangedJob = lifecycleScope.launch {
                    delay(1000)
                    viewModel.searchData(text)
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchData(query)
                return false
            }
        })
       // filter.setOnClickListener{filtration()}
    }

    private fun setContent() {
            viewModel.characters.observe(viewLifecycleOwner) {
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
        val fragment = CharacterDetailFragment()
        fragment.arguments = arg
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragmentPlace, fragment, null)
            ?.addToBackStack(null)
            ?.commit()
    }
    private fun nextPage(){
        progressBar.visibility = View.VISIBLE
        viewModel.nextPage()
    }
    private fun filtration (item:Int){
        var types = arrayListOf(
            "Genetic experiment",
            "Superhuman (Ghost trains summoner)",
            "Parasite",
            "Human with antennae",
            "Human with ants in his eyes",
            "Fish-Person",
            "Cromulon",
            "Self-aware arm",
            "Cat-Person",
            "Human with baby legs",
            "Bepisian",
            "Hivemind",
            "Mytholog",
            "Human with giant head",
            "Dog",
            "Bird-Person",
            "Korblock",
            "Boobloosian",
            "Elephant-Person",
            "Superhuman",
            "Gromflomite",
            "Centaur",
            "Organic gun",
            "Microverse inhabitant",
            "Vampire",
            "Light bulb-Alien",
            "Animal",
            "Robot-Crocodile hybrid",
            "Zigerion",
            "Giant",
            "Cone-nippled alien",
            "Demon",
            "Shapeshifter",
            "Game"
        )
        val args=Bundle()
        if (item==3){
            args.putStringArrayList("list", types)
            args.putInt("selected",2)
        }else if(item==0){
            val list= mutableListOf<String>()
                Status.values().forEach { list.add(it.name) }
            args.putStringArray("list",list.toTypedArray())

            args.putInt("selected",viewModel._request.status?.ordinal?:-1)
            println(args.getStringArray("list")?.size)
        }
        filterId=item
        dialog=Dialog()
       dialog.arguments=args
        println(args.getStringArray("list"))

       dialog.show(childFragmentManager,"Dialog")

    }
    fun input(value:Int){
        when(filterId){
            0-> viewModel.changeStatus(value)
            1->viewModel.changeGender(value)
            2->viewModel.changeSpecies(value)
        }
    }

}