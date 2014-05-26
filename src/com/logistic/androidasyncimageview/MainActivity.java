package com.logistic.androidasyncimageview;

import com.logistic.async.AsyncImageView;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	EditText etUrl;
	Button btnGo;
	AsyncImageView async;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		async = (AsyncImageView) findViewById(R.id.imageView1);
		async.strDefaultImage = "no_image";
		//async.setDefaultImage();
		
		etUrl = (EditText) findViewById(R.id.etUrl);
		etUrl.setText("http://www.logisticinfotech.com/blog/imagesAndroidDemos/androidasyncimageview.jpg");
		btnGo = (Button) findViewById(R.id.button1);
		btnGo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (etUrl.getText().toString().length() > 0) {
					async.downloadImage(etUrl.getText().toString().trim());
				}

			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
