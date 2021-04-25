package com.sanjay.imagesearchapp.ui.gallery

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.sanjay.imagesearchapp.R
import com.sanjay.imagesearchapp.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.unsplash_photo_load_state_footer.*

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    private val viewModel: GalleryViewModel by viewModels()

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private lateinit var photoAdapter: UnsplashPhotoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentGalleryBinding.bind(view)

        photoAdapter = UnsplashPhotoAdapter()
        binding.recyclerView.apply {
            setHasFixedSize(true)
            itemAnimator = null
            adapter = photoAdapter.withLoadStateHeaderAndFooter(
                header = UnsplashPhotoLoadStateAdapter { photoAdapter.retry() },
                footer = UnsplashPhotoLoadStateAdapter { photoAdapter.retry() }
            )
        }
        binding.buttonRetry.setOnClickListener {
            photoAdapter.retry()
        }


        viewModel.photos.observe(viewLifecycleOwner) {
            photoAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        photoAdapter.addLoadStateListener { loadstate ->
            /**loadstate of type CombinedLoadStates which combines the loadstate of different
             * scenarios into this one object. When refresh the dataset or when we append new
             * data to it and we can use it check for it and make our views visible or
             * invisible according to it.*/
            binding.apply {
                progressBar.isVisible = loadstate.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadstate.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadstate.source.refresh is LoadState.Error
                textViewError.isVisible = loadstate.source.refresh is LoadState.Error

                //empty view
                if(loadstate.source.refresh is LoadState.NotLoading &&
                    loadstate.append.endOfPaginationReached && photoAdapter.itemCount < 1){
                    recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }

        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_gallery,menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
               if (query != null) {
                   binding.recyclerView.scrollToPosition(0)
                   viewModel.searchPhotos(query)
                   searchView.clearFocus()// it will hide the keyword when we a submit a search
               }
                return true

            }

            override fun onQueryTextChange(newText: String?): Boolean {
               return true
            }

        })
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}