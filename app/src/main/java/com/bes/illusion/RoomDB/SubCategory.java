/*
 * *
 *  * Created by Saravana on 3/12/20 12:10 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 3/12/20 12:10 PM
 *  *
 */

package com.bes.illusion.RoomDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "sub_category_table")
public class SubCategory
{

	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	private int id;

	@ColumnInfo(name = "categoryName")
	private String CategoryName;

	@ColumnInfo(name = "isTop")
	private boolean Istop = false;

	public SubCategory(String CategoryName)
	{
		this.CategoryName = CategoryName;
	}

	public SubCategory(String CategoryName, boolean isTop)
	{
		this.CategoryName = CategoryName;
		Istop = isTop;
	}

	@Ignore
	public SubCategory()
	{
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getCategoryName()
	{
		return CategoryName;
	}

	public void setCategoryName(String CategoryName)
	{
		this.CategoryName = CategoryName;
	}

	public boolean isIstop()
	{
		return Istop;
	}

	public void setIstop(boolean istop)
	{
		Istop = istop;
	}

}
