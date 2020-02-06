package com.santanu.barcodedata.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.android.material.button.MaterialButton
import com.santanu.barcodedata.R
import com.santanu.barcodedata.viewmodel.CodeScanViewModel


class CodeScanFragment : Fragment() {
    private val TAG: String = CodeScanFragment::class.java.simpleName

    /** viewModel instance **/
    private lateinit var viewModel: CodeScanViewModel

    private lateinit var barcodeDetector: BarcodeDetector
    private lateinit var cameraSource: CameraSource

    /** widget view instance **/
    private lateinit var surfaceView: SurfaceView
    private lateinit var takeImgBtn: MaterialButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.code_scan_fragment, container, false)

        surfaceView = view.findViewById(R.id.surface_view)
        takeImgBtn = view.findViewById(R.id.m_btn_take_image)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CodeScanViewModel::class.java)

    }

    companion object {
        fun newInstance() = CodeScanFragment()
    }
}
