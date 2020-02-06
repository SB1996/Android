package com.santanu.camerax

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.util.Rational
import android.view.TextureView
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.Preview.OnPreviewOutputUpdateListener
import androidx.lifecycle.LifecycleOwner
import androidx.camera.core.CameraX
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureConfig
import androidx.camera.core.Preview
import androidx.camera.core.PreviewConfig
import android.os.Environment
import android.util.Size
import android.view.Surface
import android.view.View

import java.io.File


class MainActivity : AppCompatActivity() {
    private val TAG: String = MainActivity::class.java.simpleName

    private lateinit var cameraTextureView: TextureView
    private lateinit var captureIBtn: ImageButton
    private lateinit var imgView: ImageView

    /** Permission List **/
    private val permissionList: Array<String> = arrayOf<String>(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private val permissionCode: Int = 10 /** Permission Code **/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cameraTextureView = findViewById(R.id.camera_view)
        captureIBtn = findViewById(R.id.ib_capture)
        imgView = findViewById(R.id.iv_img)

        Permission().requestPermissions(this, permissionList, permissionCode)

        startCamera()


    }

    private fun startCamera() {
        CameraX.unbindAll()
        val aspectRatio: Rational = Rational(cameraTextureView.width, cameraTextureView.height)
        val screen: Size = Size(cameraTextureView.width, cameraTextureView.height)
        val previewConfig: PreviewConfig = PreviewConfig.Builder()
            .setTargetAspectRatio(aspectRatio)
            .setTargetResolution(screen) //.setLensFacing(CameraX.LensFacing.FRONT)
            .build()
        val preview: Preview = Preview(previewConfig)

        preview.onPreviewOutputUpdateListener =
            OnPreviewOutputUpdateListener { output ->
                val parent = cameraTextureView.parent as ViewGroup
                parent.removeView(cameraTextureView)
                parent.addView(cameraTextureView, 0)
                cameraTextureView.surfaceTexture = output.surfaceTexture
                updateTransform()
            }

        val imageCaptureConfig = ImageCaptureConfig.Builder()
            .setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
            .setTargetRotation(windowManager.defaultDisplay.rotation).build()

        val imgCap: ImageCapture = ImageCapture(imageCaptureConfig)

        captureIBtn.setOnClickListener {
            val file = File(Environment.getExternalStorageDirectory().toString() + "/" + System.currentTimeMillis() + ".png")
            imgCap.takePicture(file, object : ImageCapture.OnImageSavedListener{
                override fun onImageSaved(file: File) {
                    val path = file.absolutePath
                    Log.d(TAG, "MainActivity{} : onImageSaved() >>" +
                        " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Path[Image] : $path"
                    )

                    val bitmap: Bitmap = BitmapFactory.decodeFile(path)
                    imgView.setImageBitmap(bitmap)
                }

                override fun onError(useCaseError: ImageCapture.UseCaseError, message: String, cause: Throwable?) {
                    Log.d(TAG, "MainActivity{} : onError() >>" +
                        " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Failed : $message"
                    )
                    cause?.printStackTrace()
                }
            })
        }

        //bind to lifecycle:
        CameraX.bindToLifecycle(this as LifecycleOwner, preview, imgCap)
    }
    
    private fun updateTransform(){

        val mx: Matrix = Matrix()

        val width: Int = cameraTextureView.measuredWidth
        val height: Int = cameraTextureView.measuredHeight

        val cX: Float = width / 2f
        val cY: Float = height / 2f

        var rotationDgr: Int
        val rotation: Int = cameraTextureView.rotation.toInt()

        rotationDgr = when(rotation){
            Surface.ROTATION_0 -> {
                0
            }
            Surface.ROTATION_90 -> {
                90
            }
            Surface.ROTATION_180 -> {
                180
            }
            Surface.ROTATION_270 -> {
                270
            }

            else -> {return}
        }

        mx.postRotate(rotationDgr.toFloat(), cX, cY)
        cameraTextureView.setTransform(mx)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d(TAG, "MainActivity{} : onRequestPermissionsResult() >>" +
            " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Call"
        )

        if (requestCode == permissionCode) {
            Log.d(TAG, "MainActivity{} : onRequestPermissionsResult() >>" +
                " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Permission Granted"
            )
        }
    }
}
