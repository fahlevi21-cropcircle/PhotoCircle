package com.cropcircle.photocircle.ui.detail

import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Drawable
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
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.cropcircle.photocircle.MainActivity
import com.cropcircle.photocircle.R
import com.cropcircle.photocircle.databinding.DetailsFragmentBinding
import com.cropcircle.photocircle.model.PhotoItem
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialFadeThrough
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.details_fragment) {
    private val viewModel by viewModels<DetailsViewModel>()
    private lateinit var binding: DetailsFragmentBinding
    private lateinit var photo: PhotoItem
    private lateinit var requestPermissionWriteExternalStorage: ActivityResultLauncher<String>
    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialFadeThrough().apply {
            duration = 300L
        }

        requestPermissionWriteExternalStorage =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    downloadImage()
                } else {
                    Toast.makeText(context, "Permission not granted", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //postponeEnterTransition()

        binding = DetailsFragmentBinding.bind(view)
        (activity as MainActivity).setSupportActionBar(binding.toolbar)
        (activity as MainActivity).title = null
        (activity as MainActivity).setupNavComponents(true)
        (activity as MainActivity).hideBottomNav()

        /*binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }*/

        /* binding.button.setOnClickListener {
             openPermissionWindow()
         }*/


        /*Glide.with(this@DetailsFragment)
            .load(args.photoItem.urls.thumb)
            .fitCenter()
            .into(binding.image)*/

        getPhotoDetail()
        observePhoto()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).showBottomNav()
    }

    private fun openPermissionWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            downloadImage()
        } else {
            when (PackageManager.PERMISSION_GRANTED) {
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

    private fun getPhotoDetail() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getDetails(args.photoId).also {
                if(it != null){
                    viewModel.setData(it)
                }else{
                    Toast.makeText(context, "Network Error/Limit Reached!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observePhoto() {
        viewModel.details.observe(
            viewLifecycleOwner,
            { data ->
                if (data != null) {
                    photo = data
                    Glide.with(this@DetailsFragment)
                        .load(data.urls.regular)
                        .fitCenter()
                        .thumbnail(0.3f)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                //startPostponedEnterTransition()
                                binding.loading.isVisible = false
                                binding.cardDetails.isVisible = true
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                //startPostponedEnterTransition()
                                binding.loading.isVisible = false
                                binding.cardDetails.isVisible = true
                                return false
                            }
                        })
                        .error(R.drawable.ic_baseline_settings_power)
                        .into(binding.image)
                    /* binding.layoutContent.photoDetailUsername.text = data.user.name
                     binding.layoutContent.photoDetailAttribution.text = data.altDescription*/
                }
            },
        )
    }

    private fun downloadImage() {
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

        val downloadManager =
            (activity as MainActivity).getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadID = downloadManager.enqueue(downloadRequest)

        Toast.makeText(context, "Downloading..", Toast.LENGTH_SHORT).show()

        Log.d("DOWNLOAD MANAGER", "downloadImage: $downloadID")
    }
}