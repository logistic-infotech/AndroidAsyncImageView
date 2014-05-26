package com.logistic.async;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.Header;

import android.R.color;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;

import com.logistic.magazine.restclient.LIRestClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

public class AsyncImageView extends FrameLayout {

	private static final String TAG = AsyncImageView.class.getName();
	
	ProgressBar progressBar;

	Context c;
	InputStream input;
	public ImageView imageView;
	String fileName;
	String URL;
	
	public String strDefaultImage;
	
	public AsyncImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		c = context;

		progressBar = new ProgressBar(c, null, android.R.attr.progressBarStyleSmall);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT, Gravity.TOP | Gravity.LEFT);

		imageView = new ImageView(c);
		imageView.setScaleType(ScaleType.FIT_CENTER);
		//imageView.setBackgroundColor(color.darker_gray);
		addView(imageView, params);
		//
		
		params = new FrameLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		addView(progressBar, params);
		progressBar.setVisibility(View.INVISIBLE);
		
	}
	
	public void setDefaultImage() {
		int resID = getResources().getIdentifier(strDefaultImage, "drawable",  c.getPackageName());
		imageView.setImageResource(resID);
	}
	
	public AsyncImageView(Context context) {
		this(context, null);		
	}

	public void downloadImage(String url) {
		URL = url;
		Bitmap myBitmap = null;
		//System.out.println("helo this is url -> " + URL);
		fileName = url.substring(url.lastIndexOf('/') + 1, url.length());
		File cacheDir = getContext().getExternalCacheDir();
		File f = new File(cacheDir, fileName);
		if (f.exists()) {
			System.out.println("getting image from folder " + f);
			myBitmap = BitmapFactory.decodeFile(f.getAbsolutePath());			
			imageView.setImageBitmap(myBitmap);
			// return myBitmap;

		} else {
			System.out.println("getting image from url" + f);
			
			progressBar.setVisibility(View.VISIBLE);
			
			LIRestClient.downloadFile(url, null, new FileAsyncHttpResponseHandler(f) {
				@Override
				public void onSuccess(int statusCode, File file) {
					Log.i(TAG, "onClick onSuccess :" + statusCode);
					
					Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());			
					imageView.setImageBitmap(myBitmap);
					
					progressBar.setVisibility(View.GONE);
				}

				@Override
				public void onProgress(int bytesWritten, int totalSize) {
					int totProgress = (bytesWritten * 100) / totalSize;
					if (totProgress > 0) {
						// Log.i(TAG, "totProgress: " + totProgress);
						
					}
				}
				 
				@Override
				public void onFailure(int statusCode, Header[] headers, Throwable e, File response) {
					response.delete();
					Log.i(TAG, "onClick onFailure :" + statusCode+" "+e.getLocalizedMessage());
					e.printStackTrace();
					progressBar.setVisibility(View.GONE);
					
					int resID = getResources().getIdentifier(strDefaultImage, "drawable",  c.getPackageName());
					imageView.setImageResource(resID);
				}
			});
		}
	}
}