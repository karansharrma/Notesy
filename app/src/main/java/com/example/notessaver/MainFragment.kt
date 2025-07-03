    package com.example.notessaver

    import android.os.Bundle
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.Toast
    import androidx.core.view.isVisible
    import androidx.fragment.app.viewModels
    import androidx.navigation.fragment.findNavController
    import androidx.recyclerview.widget.StaggeredGridLayoutManager
    import com.example.notessaver.databinding.FragmentMainBinding
    import com.example.notessaver.models.NotesResponse
    import com.example.notessaver.utils.NetworkResult
    import com.example.notessaver.viewmodel.NoteViewModel
    import com.google.gson.Gson
    import dagger.hilt.android.AndroidEntryPoint


    @AndroidEntryPoint
    class MainFragment : Fragment() {

        //    @Inject
    //    lateinit var notesApi: NotesApi
        private var _binding: FragmentMainBinding? = null
        private val binding get() = _binding!!

        private val noteViewModel by viewModels<NoteViewModel>()


        private lateinit var adapter: NoteAdapter
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
    //        CoroutineScope(Dispatchers.IO).launch{
    //            val response= notesApi.getNotes()
    //            Log.d(TAG,response.body().toString())
    //        }
            adapter = NoteAdapter(::onNoteClicked)
            _binding = FragmentMainBinding.inflate(inflater, container, false)
            binding.lottieLoader.isVisible = false
            return binding.root
        }


        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            bindObserver()
            noteViewModel.getAllNotes()

            binding.profileImage.setOnClickListener{
                findNavController().navigate(R.id.action_mainFragment_to_profileFragment)
            }
            binding.noteListRecycler.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            binding.noteListRecycler.adapter = adapter
            binding.addNote.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_noteFragment)
            }
        }

        private fun bindObserver() {
            binding.lottieLoader.isVisible = false
            noteViewModel.notesLiveData.observe(viewLifecycleOwner, {
                when (it) {
                    is NetworkResult.Success -> {
                        binding.lottieLoader.isVisible = false
                        adapter.submitList(it.data)
                    }

                    is NetworkResult.Loading -> {
                        binding.lottieLoader.isVisible = true
                        binding.lottieLoader.playAnimation()
                    }

                    is NetworkResult.Error -> {
                        binding.lottieLoader.isVisible = false
                        Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
        }

        private fun onNoteClicked(noteResponse: NotesResponse) {
            val bundle = Bundle()
            bundle.putString("note", Gson().toJson(noteResponse))
            findNavController().navigate(R.id.action_mainFragment_to_noteFragment, bundle)
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }