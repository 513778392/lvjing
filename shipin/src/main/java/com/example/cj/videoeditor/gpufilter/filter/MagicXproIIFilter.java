package com.example.cj.videoeditor.gpufilter.filter;

import android.opengl.GLES20;

import com.example.cj.videoeditor.MyApplication;
import com.example.cj.videoeditor.gpufilter.basefilter.GPUImageFilter;
import com.example.cj.videoeditor.gpufilter.utils.OpenGlUtils;

import videoeditor.cj.example.com.shipin.R;


public class MagicXproIIFilter extends GPUImageFilter {
	private int[] inputTextureHandles = {-1,-1};
	private int[] inputTextureUniformLocations = {-1,-1};
	private int mGLStrengthLocation;

	public MagicXproIIFilter(){
		super(NO_FILTER_VERTEX_SHADER, OpenGlUtils.readShaderFromRawResource(R.raw.xproii_filter_shader));
	}

	public void onDestroy() {
		super.onDestroy();
		GLES20.glDeleteTextures(inputTextureHandles.length, inputTextureHandles, 0);
		for(int i = 0; i < inputTextureHandles.length; i++)
			inputTextureHandles[i] = -1;
	}

	protected void onDrawArraysAfter(){
		for(int i = 0; i < inputTextureHandles.length
				&& inputTextureHandles[i] != OpenGlUtils.NO_TEXTURE; i++){
			GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + (i+3));
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
			GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		}
	}

	protected void onDrawArraysPre(){
		for(int i = 0; i < inputTextureHandles.length
				&& inputTextureHandles[i] != OpenGlUtils.NO_TEXTURE; i++){
			GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + (i+3) );
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, inputTextureHandles[i]);
			GLES20.glUniform1i(inputTextureUniformLocations[i], (i + 3));
		}
	}

	public void onInit(){
		super.onInit();
		for(int i = 0; i < inputTextureUniformLocations.length; i++)
			inputTextureUniformLocations[i] = GLES20.glGetUniformLocation(getProgram(), "inputImageTexture"+(2+i));
			mGLStrengthLocation = GLES20.glGetUniformLocation(mGLProgId,
				"strength");
	}

	public void onInitialized(){
		super.onInitialized();
		setFloat(mGLStrengthLocation, 1.0f);
		runOnDraw(new Runnable(){
			public void run(){
				inputTextureHandles[0] = OpenGlUtils.loadTexture(MyApplication.getContext(), "filter/xpromap.png");
				inputTextureHandles[1] = OpenGlUtils.loadTexture(MyApplication.getContext(), "filter/vignettemap_new.png");
			}
		});
	}

}
