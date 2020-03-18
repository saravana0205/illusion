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
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_category")
public class ProductCategory
{

	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	private int id;

	@ColumnInfo(name = "productId")
	@ForeignKey(entity = Products.class, parentColumns = "id", childColumns = "productId", onDelete = ForeignKey.CASCADE)
	private int ProductName;

	@ColumnInfo(name = "categoryId")
	@ForeignKey(entity = Products.class, parentColumns = "id", childColumns = "categoryId", onDelete = ForeignKey.CASCADE)
	private int CategoryName;

	public ProductCategory(int productName, int categoryName)
	{
		ProductName = productName;
		CategoryName = categoryName;
	}

	public ProductCategory()
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

	public int getProductName()
	{
		return ProductName;
	}

	public void setProductName(int productName)
	{
		ProductName = productName;
	}

	public int getCategoryName()
	{
		return CategoryName;
	}

	public void setCategoryName(int categoryName)
	{
		CategoryName = categoryName;
	}


}
