package com.bes.illusion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.bes.illusion.RoomDB.DatabaseDAO;
import com.bes.illusion.RoomDB.RoomDB;
import com.bes.illusion.RoomDB.SubCategory;

public class SplashScreenActivity extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		getSupportActionBar().hide();
		RoomDB db = RoomDB.getDatabase(SplashScreenActivity.this);
		final DatabaseDAO databaseDAO = db.databaseDAO();
		db.databaseWriteExecutor.execute(new Runnable(){
			@Override
			public void run()
			{
//				for(String name:databaseDAO.getAllSubCategory(new int[]{2,3}))
//				{
//										Log.d("s2s", "mohan " + name);
//				}
			}
		});
		Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				startActivity(new Intent(SplashScreenActivity.this,LoginActivity.class));
				finish();
			}
		}, 2000);
	}
}
