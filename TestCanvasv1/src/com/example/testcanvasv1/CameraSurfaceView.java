package com.example.testcanvasv1;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

	private static final String TAG = "CameraSurfaceView";
	private SurfaceHolder holder;
	private Camera camera;
	
	public CameraSurfaceView(Context context) {
		super(context);
		
		// Initiate the Surface Holder properly
		this.holder = this.getHolder();
		this.holder.addCallback(this);
	}
	
	public CameraSurfaceView(Context context, AttributeSet set) {
		super(context, set);

		// Initiate the Surface Holder properly
		this.holder = this.getHolder();
		this.holder.addCallback(this);
		this.holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			// Open the Camera in preview mode
			this.camera = Camera.open();
			//Configuration c = getResources().getConfiguration();
			 
			/*if(c.orientation == Configuration.ORIENTATION_PORTRAIT ) {
				this.camera.setDisplayOrientation(90);	
			}*/

			this.camera.setPreviewDisplay(this.holder);

		} catch (IOException ioe) {
			ioe.printStackTrace(System.out);
		}		
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

		Log.e("Test", "SurfaceChanged");

		// Now that the size is known, set up the camera parameters and begin
		// the preview.
		Camera.Parameters parameters = camera.getParameters();
		List<Camera.Size> previewSize = parameters.getSupportedPreviewSizes();
		List<Camera.Size> pictureSize = parameters.getSupportedPictureSizes();
		int preview_index = ImageMaxSize.maxSize(previewSize);
        int picture_index = ImageMaxSize.maxSize(pictureSize);
		parameters.setPictureSize(previewSize.get(preview_index).width, previewSize.get(preview_index).height);
        parameters.setPreviewSize(previewSize.get(picture_index).width,previewSize.get(picture_index).height);
        parameters.setJpegQuality(100);

		camera.setParameters(parameters);
		camera.startPreview();

	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// Surface will be destroyed when replaced with a new screen
		// Always make sure to release the Camera instance
		camera.setPreviewCallback(null);
		camera.stopPreview();
		camera.release();
		camera = null;
	}
	
	public Camera getCamera() {
		return this.camera;
	}
	
}
