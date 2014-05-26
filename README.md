AndroidAsyncImageView
=====================

Android AsyncImageView Single Class
------------------------------------

To use in the project we have to just declare in xml anywhare just like below and then just need to access object and call downloadImage function.


	<com.logistic.async.AsyncImageView
        	android:id="@+id/imageView1"
        	android:layout_width="match_parent"
        	android:layout_height="match_parent"
        	android:layout_below="@+id/etUrl"
	        android:layout_marginTop="5dp"
        	android:background="@android:color/darker_gray" />




	async = (AsyncImageView) findViewById(R.id.imageView1);
	async.strDefaultImage = "no_image";
	async.downloadImage("image url");
