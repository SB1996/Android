package com.example.selectimage

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.content.Intent
import android.provider.MediaStore
import android.content.DialogInterface
import android.net.Uri
import android.os.Environment
import androidx.appcompat.app.AlertDialog
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.util.Log
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.*
import android.R.attr.data




class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var selectImg: ImageView

    private val permissionListCode: Int = 10
    private val permissionList: Array<String> = arrayOf<String>(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestGroupPermission(permissionList, permissionListCode);

        selectImg = findViewById(R.id.slc_img)
        selectImg.setOnClickListener {
            selectImage()
        }

    }
    //Group permission request ..!
    private fun requestGroupPermission(_permissionsList: Array<String>, _permissionCode: Int){
        val permissionNeededList: Array<String> = _permissionsList
        var permissionDueList: Array<String> = arrayOf<String>()

        for(permission: String in permissionNeededList){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                permissionDueList = permissionDueList.plus(permission)
            }
        }

        if(permissionDueList.size > 0) {
            ActivityCompat.requestPermissions(this, permissionDueList, _permissionCode)
        }else{
            Toast.makeText(this, "You have already granted all permission!", Toast.LENGTH_SHORT).show();
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if(permissions.size > 1){
            var status: Boolean = false
            if (requestCode == permissionListCode) {
                if (grantResults.size > 0) {
                    for (_grantResults in grantResults){
                        if(_grantResults == PackageManager.PERMISSION_GRANTED){
                            status = true
                        }else{
                            status = false
                            break;
                        }
                    }
                    if(status == true) {
                        Toast.makeText(this, "All Permission GRANTED", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "All Permission Not GRANTED", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "All Permission Not GRANTED", Toast.LENGTH_SHORT).show()
                }
            }
        }



    }

    private fun selectImage() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Select an option")
        builder.setItems(options, DialogInterface.OnClickListener { dialog, item ->
            if (options[item] == "Take Photo") {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                val f = File(Environment.getExternalStorageDirectory(), "profile.jpg")
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f))
                startActivityForResult(intent, 1)
            } else if (options[item] == "Choose from Gallery") {
                val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 2)
            } else if (options[item] == "Cancel") {
                dialog.dismiss()
            }
        })
        builder.show()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                var f = File(Environment.getExternalStorageDirectory().toString())
                for (temp in f.listFiles()!!) {
                    if (temp.name == "profile.jpg") {
                        f = temp
                        break
                    }
                }
                try {
                    val bitmap: Bitmap
                    val bitmapOptions = BitmapFactory.Options()
                    bitmap = BitmapFactory.decodeFile(f.absolutePath, bitmapOptions)
                    selectImg.setImageBitmap(bitmap)
                    val path = (android.os.Environment
                        .getExternalStorageDirectory().toString()
                            + File.separator
                            + "Phoenix" + File.separator + "default")
                    f.delete()
                    var outFile: OutputStream? = null
                    val file = File(path, System.currentTimeMillis().toString() + ".jpg")
                    try {
                        outFile = FileOutputStream(file)
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile)
                        outFile.flush()
                        outFile.close()
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            } else if (requestCode == 2) {
                val selectedImage = data!!.data

                /*CropImage.activity(selectedImage)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);

                val result = CropImage.getActivityResult(data)
                val resultUri = result.uri*/

                val filePath = arrayOf(MediaStore.Images.Media.DATA)
                val c = contentResolver.query(selectedImage!!, filePath, null, null, null)
                c!!.moveToFirst()
                val columnIndex = c.getColumnIndex(filePath[0])
                val picturePath = c.getString(columnIndex)
                c.close()
                val thumbnail = BitmapFactory.decodeFile(picturePath)
                Log.i(TAG, picturePath )
                selectImg.setImageBitmap(thumbnail)
            }
        }
    }
}
