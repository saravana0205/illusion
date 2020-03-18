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
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_price")
public class ProductPrice
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

	@ColumnInfo(name = "price")
	private double Price;

	public ProductPrice(int productName, int categoryName, double price)
	{
		ProductName = productName;
		CategoryName = categoryName;
		Price = price;
	}

	public ProductPrice()
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

	public double getPrice()
	{
		return Price;
	}

	public void setPrice(double price)
	{
		Price = price;
	}


}
