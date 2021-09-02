package com.cropcircle.photocircle.ui.detail

import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.cropcircle.photocircle.MainActivity
import com.cropcircle.photocircle.R
import com.cropcircle.photocircle.databinding.DetailsFragmentBinding
import com.cropcircle.photocircle.model.PhotoItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.details_fragment) {
    private val viewModel by viewModels<DetailsViewModel>()
    private lateinit var binding: DetailsFragmentBinding
    private lateinit var photo : PhotoItem
    private lateinit var requestPermissionWriteExternalStorage : ActivityResultLauncher<String>
    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissionWriteExternalStorage = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            isGranted ->
            if (isGranted){
                downloadImage()
            }else{
                Toast.makeText(context, "Permission not granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DetailsFragmentBinding.bind(view)
        (activity as MainActivity).setSupportActionBar(binding.mainToolbar)
        (activity as MainActivity).title = null

        val navController = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(binding.mainToolbar, navController)

        binding.mainToolbar.setNavigationOnClickListener { v->
            v.findNavController().navigateUp()
        }

        binding.button.setOnClickListener {
            openPermissionWindow()
        }

        getPhotoDetail()
        observePhoto()
    }


    private fun openPermissionWindow(){
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
           downloadImage()
       }else{
           when (PackageManager.PERMISSION_GRANTED){
               ContextCompat.checkSelfPermission(
                   requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE
               ) -> {
                   downloadImage()
               }
               else -> {
                   requestPermissionWriteExternalStorage.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
               }
           }
       }
    }

    private fun getPhotoDetail(){
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getDetails(args.photoId).also {
                viewModel.setData(it)
            }
        }
    }

    private fun observePhoto(){
        viewModel.details.observe(
            viewLifecycleOwner,
            { data ->
                photo = data
                Glide.with(this@DetailsFragment)
                    .load(data.urls.regular)
                    .fitCenter()
                    .into(binding.photoDetailImage)
                binding.layoutContent.photoDetailUsername.text = data.user.name
                if (data.description.isNotEmpty()){
                    binding.layoutContent.photoDetailAttribution.text = data.description
                }else{
                    binding.layoutContent.photoDetailAttribution.text = data.altDescription
                }
            },
        )
    }

    private fun downloadImage(){
        val downloadRequest = DownloadManager.Request(Uri.parse(photo.urls.full))
        downloadRequest.apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            setTitle(photo.id + ".jpg")
            setDescription("Downloading image...")
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setAllowedOverMetered(true)
            setAllowedOverRoaming(true)
            setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, photo.id + ".jpg")
        }

        val downloadManager = (activity as MainActivity).getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadID = downloadManager.enqueue(downloadRequest)

        Toast.makeText(context, "Downloading..",Toast.LENGTH_SHORT).show()

        Log.d("DOWNLOAD MANAGER", "downloadImage: $downloadID")
    }
}