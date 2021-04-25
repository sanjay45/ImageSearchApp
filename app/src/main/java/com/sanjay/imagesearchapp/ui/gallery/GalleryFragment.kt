package com.sanjay.imagesearchapp.ui.gallery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sanjay.imagesearchapp.R
import com.sanjay.imagesearchapp.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

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
            adapter = photoAdapter.withLoadStateHeaderAndFooter(
                header = UnsplashPhotoLoadStateAdapter { photoAdapter.retry() },
                footer = UnsplashPhotoLoadStateAdapter { photoAdapter.retry() }
            )
        }

        viewModel.photos.observe(viewLifecycleOwner) {
            photoAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}