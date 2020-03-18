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

@Entity(tableName = "product_stock")
public class ProductStock
{

	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	private int id;

	@ColumnInfo(name = "productName")
	@ForeignKey(entity = Products.class, parentColumns = "id", childColumns = "productName", onDelete = ForeignKey.CASCADE)
	private int ProductName;

	@ColumnInfo(name = "categoryName")
	@ForeignKey(entity = Products.class, parentColumns = "id", childColumns = "categoryName", onDelete = ForeignKey.CASCADE)
	private int CategoryName;

	@ColumnInfo(name = "stock")
	private double Stock;

	public ProductStock(int productName, int categoryName, double stock)
	{
		ProductName = productName;
		CategoryName = categoryName;
		Stock = stock;
	}

	public ProductStock()
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

	public double getStock()
	{
		return Stock;
	}

	public void setStock(double stock)
	{
		Stock = stock;
	}


}
