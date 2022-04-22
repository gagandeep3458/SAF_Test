package com.gagan.saftest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.documentfile.provider.DocumentFile
import com.gagan.saftest.databinding.ActivityMainBinding
import java.io.BufferedInputStream
import java.io.FileInputStream


private const val TAG = "MainActivity!!"
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Contract to request permission to access document folder
    private val documentFolderUriContract = registerForActivityResult(ActivityResultContracts.OpenDocumentTree()) {

        // Persist Uri Permission for future use
        contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    // Uri for Document Folder
    private val documentFolderUri: Uri = Uri.parse("content://com.android.externalstorage.documents/tree/primary%3ADocuments")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val dc = DocumentsContract.

        Log.d(TAG, "onCreate: ")




//        Log.d(TAG, "onCreate: ${contentResolver.persistedUriPermissions[0].uri}")
//
//
//        if (contentResolver.persistedUriPermissions.isEmpty()) {
//            // Ask for Read permission on Document Folder
//            documentFolderUriContract.launch(documentFolderUri)
//        } else {
//            if (contentResolver.persistedUriPermissions.filter { it.uri.equals(documentFolderUri)  && it.isReadPermission }.count() == 0) {
//                // Ask for Read permission on Document Folder
//                documentFolderUriContract.launch(documentFolderUri)
//            } else {
//                printListOfFilesInDocumentFolder()
//            }
//        }
    }

    private fun printListOfFilesInDocumentFolder() {
        val df = DocumentFile.fromTreeUri(this, documentFolderUri)
        df?.listFiles()?.forEach {
            Log.d(TAG, "File Name -> ${it.name}")
            Log.d(TAG, "File Name -> ${it.uri}")
            Log.d(TAG, "File Contents -> ${getPathFromUri(it.uri)}")
            Log.d(TAG, "|||||||||||||||||||||||")
        }
    }

    private fun getPathFromUri(uri: Uri): String {

        Log.d(TAG, "URI Type -> ${contentResolver}")

            contentResolver.openFileDescriptor(uri, "r").use { pfd ->
                FileInputStream(pfd?.fileDescriptor).use { fis ->
                    val bis = BufferedInputStream(fis)
                    val text = bis.reader().readLines()
                    return text.toString()
                }
            }
    }
}