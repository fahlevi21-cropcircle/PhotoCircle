package com.cropcircle.photocircle.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.cropcircle.photocircle.MainActivity
import com.cropcircle.photocircle.R
import com.cropcircle.photocircle.databinding.DetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.details_fragment) {
    private val viewModel by viewModels<DetailsViewModel>()
    private val args by navArgs<DetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = DetailsFragmentBinding.bind(view)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.mainToolbar.setupWithNavController(navController,appBarConfiguration)
        (activity as MainActivity).title = null

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getDetails(args.photoId).also {
                viewModel.setData(it)
                viewModel.details.observe(
                    viewLifecycleOwner,
                    { data ->
                        Glide.with(this@DetailsFragment)
                            .load(data.urls.regular)
                            .fitCenter()
                            .into(binding.photoDetailImage)
                        binding.layoutContent.photoDetailUsername.text = data.user.name
                        if (data.description != null && !data.description.isEmpty()){
                            binding.layoutContent.photoDetailAttribution.text = data.description
                        }else{
                            binding.layoutContent.photoDetailAttribution.text = data.altDescription
                        }
                    },
                )
            }
        }
    }
}