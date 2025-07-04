package com.example.notessaver

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.notessaver.databinding.FragmentProfileBinding
import java.io.File
import java.io.FileOutputStream

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                convertUriToBitmapAndStore(it)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load image from internal storage if exists
        val prefs = requireContext().getSharedPreferences("profile_prefs", android.content.Context.MODE_PRIVATE)
        val savedImagePath = prefs.getString("image_path", null)
        if (savedImagePath != null) {
            val file = File(savedImagePath)
            if (file.exists()) {
                val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                binding.profileImage.setImageBitmap(bitmap)
            }
        }

        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.editIcon.setOnClickListener {
            pickImageFromGallery()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun pickImageFromGallery() {
        pickImageLauncher.launch("image/*")
    }

    private fun convertUriToBitmapAndStore(uri: Uri) {
        Glide.with(requireContext())
            .asBitmap()
            .load(uri)
            .override(200, 200)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    binding.profileImage.setImageBitmap(resource)

                    // Save image to internal storage
                    val path = saveBitmapToInternalStorage(resource)

                    saveImagePathToPrefs(path)
                }

                override fun onLoadCleared(placeholder: android.graphics.drawable.Drawable?) {
                }
            })
    }

    private fun saveBitmapToInternalStorage(bitmap: Bitmap): String {
        val filename = "profile_image.png"
        val file = File(requireContext().filesDir, filename)
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        return file.absolutePath
    }

    private fun saveImagePathToPrefs(path: String) {
        val prefs = requireContext().getSharedPreferences("profile_prefs", android.content.Context.MODE_PRIVATE)
        prefs.edit().putString("image_path", path).apply()
    }
}
