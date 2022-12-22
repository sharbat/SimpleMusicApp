package com.example.musicplay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import com.example.musicplay.databinding.ActivityMainBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        var songsNameArrayList = ArrayList<String>()
        var songsDataArrayList = ArrayList<String>()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        runtimePermission()


    }

    override fun onResume() {
        super.onResume()
        findSongsAndDisplay()
        val fragmentManager = supportFragmentManager
        val fragmentPlayer = FragmentPlayer()

        fragmentManager.beginTransaction().add(R.id.fragment_container, fragmentPlayer).commit()
    }

    //implementation 'com.karumi:dexter:6.2.3'
    fun runtimePermission(){
       Dexter.withContext(this).withPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.RECORD_AUDIO)
           .withListener(object: MultiplePermissionsListener {
               override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                   onResume()
               }

               override fun onPermissionRationaleShouldBeShown(
                   p0: MutableList<PermissionRequest>?,
                   p1: PermissionToken?
               ) {
                   p1?.continuePermissionRequest()
               }

           }).check()
    }

    fun findSongsAndDisplay(){
        songsNameArrayList.clear()
        songsDataArrayList.clear()
        val cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null)
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    val indexName = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
                    val indexData = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
                    var songName = cursor.getString(indexName)

                    if(songName.contains(".mp3") || songName.contains(".wav") || songName.contains(".mp4")){
                        songName = songName.replace(".mp3", "")
                        songName = songName.replace(".mp4", "")
                        songName = songName.replace(".wav", "")
                        songsNameArrayList.add(songName)
                        songsDataArrayList.add(cursor.getString(indexData))
                    }
                }while(cursor.moveToNext())
            }
        }

        cursor?.close()

        binding.recyclerViewSongs.layoutManager = GridLayoutManager(applicationContext, 1)
        binding.recyclerViewSongs.adapter = songsListAdapter(applicationContext, songsNameArrayList, songsDataArrayList)
    }
}

