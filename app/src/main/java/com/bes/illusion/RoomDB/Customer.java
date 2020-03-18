/*
 * *
 *  * Created by Saravana on 3/11/20 10:50 AM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 3/11/20 10:18 AM
 *  *
 */

package com.bes.illusion.RoomDB;/*
 * *
 *  * Created by Saravana on 3/10/20 4:04 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 3/10/20 4:04 PM
 *  *
 */

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "customer")
public class Customer
{
	@ColumnInfo(name = "id")
	@PrimaryKey(autoGenerate = true)
	private int id;
	@ColumnInfo(name = "customer_name")
	private String CustomerName;
	@ColumnInfo(name = "mobile_number")
	private String mobileNumber;

	public Customer(String CustomerName, String mobileNumber)
	{
		this.CustomerName = CustomerName;
		this.mobileNumber = mobileNumber;
	}

	@Ignore
	public Customer()
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

	public String getCustomerName()
	{
		return CustomerName;
	}

	public void setCustomerName(String customerName)
	{
		CustomerName = customerName;
	}

	public String getMobileNumber()
	{
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber)
	{
		this.mobileNumber = mobileNumber;
	}

}
