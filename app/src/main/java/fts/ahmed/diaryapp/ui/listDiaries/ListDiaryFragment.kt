package fts.ahmed.diaryapp.ui.listDiaries

import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import fts.ahmed.diaryapp.ListDiaryAdapter
import fts.ahmed.diaryapp.R
import fts.ahmed.diaryapp.databinding.FragmentListDiariesBinding
import fts.ahmed.diaryapp.ui.MainActivity
import fts.ahmed.diaryapp.ui.MyViewModel

@AndroidEntryPoint
class ListDiaryFragment : Fragment() {

    lateinit var binding: FragmentListDiariesBinding
    lateinit var adapter: ListDiaryAdapter

    private val viewModel: MyViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterInit()
        searchFunctionality()
        lifecycleScope.launchWhenStarted {
            viewModel.searchDiaries.collect {
                adapter.differ.submitList(it)
            }
        }

    }

    private fun searchFunctionality() {
        binding.searchBox.clearFocus()
        binding.searchBox.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.searchDiaries(newText)
                return false
            }
        })
    }

    private fun adapterInit() {
        adapter = ListDiaryAdapter(
            requireContext(),
            { title, body ->
                val parent = (activity as MainActivity)
                parent.binding.bottomNavigation.selectedItemId = R.id.createDiaryFragment
                viewModel.initTitleAndBody(title, body)
            },
            {
                viewModel.delete(it)
            }

        )

        binding.rvDairies.adapter = adapter
        viewModel.allDiaries.observe(viewLifecycleOwner) {
            adapter.differ.submitList(it)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListDiariesBinding.inflate(layoutInflater)
        return binding.root
    }


}