/*
 * *
 *  * Created by Saravana on 3/11/20 11:06 AM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 3/11/20 11:06 AM
 *  *
 */

package com.bes.illusion.RoomDB;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Customer.class, Products.class,SubCategory.class,ProductPrice.class,ProductStock.class,ProductCategory.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase
{
	public abstract DatabaseDAO databaseDAO();

	private static volatile RoomDB INSTANCE;
	private final int NUMBER_OF_THREADS = 4;
	public final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

	private synchronized static RoomDB getInstance(Context context)
	{
		if(INSTANCE == null)
		{
			INSTANCE = getDatabase(context);
		}
		return INSTANCE;
	}

	@NonNull
	public static RoomDB getDatabase(final Context context)
	{
		return Room.databaseBuilder(context, RoomDB.class, "sample_database").addCallback(new Callback(){
			@Override
			public void onCreate(@NonNull SupportSQLiteDatabase db)
			{
				super.onCreate(db);
				Executors.newSingleThreadScheduledExecutor().execute(new Runnable(){
					@Override
					public void run()
					{
						long[] productId = getInstance(context).databaseDAO().insertProduct(new Products("--Select Any--",true), new Products("Carrot"),new Products("Tomato"));
						long[] categoryId = getInstance(context).databaseDAO().insertSubCategory(new SubCategory("--Select Any--",true),new SubCategory("Red"),new SubCategory("Yellow"),new SubCategory("Bangalore"),new SubCategory("Country")) ;

						getInstance(context).databaseDAO().insertProductCategory(new ProductCategory(2,2),new ProductCategory(2,3));
						getInstance(context).databaseDAO().insertProductCategory(new ProductCategory(3,4),new ProductCategory(3,5));
						for(long i:productId)
						{
							for(long j:categoryId )
							{

								if(i != 1 && j!= 1)
								{
									getInstance(context).databaseDAO().insertProductPrice(new ProductPrice((int)i,(int)j,100.0));
									getInstance(context).databaseDAO().insertProductStock(new ProductStock((int)i,(int)j,10.0));
								}
							}
						}
					}
				});
			}
		}).build();
	}
}

