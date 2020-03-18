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

@Entity(tableName = "product_table")
public class Products
{

	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	private int id;

	@ColumnInfo(name = "productName")
	private String ProductName;

	@ColumnInfo(name = "isTop")
	private boolean Istop = false;

	public Products(String ProductName)
	{
		this.ProductName = ProductName;
	}

	public Products(String productName, boolean isTop)
	{
		ProductName = productName;
		Istop = isTop;
	}

	@Ignore
	public Products()
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

	public String getProductName()
	{
		return ProductName;
	}

	public void setProductName(String productName)
	{
		ProductName = productName;
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
