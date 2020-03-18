/*
 * *
 *  * Created by Saravana on 3/11/20 10:51 AM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 3/11/20 10:51 AM
 *  *
 */

package com.bes.illusion.RoomDB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DatabaseDAO
{

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	void insertCustomer(Customer... customer);

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	long[] insertProduct(Products... products);

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	long[] insertSubCategory(SubCategory... subCategories);

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	void insertProductPrice(ProductPrice productPrices);

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	void insertProductStock(ProductStock productStock);

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	void insertProductCategory(ProductCategory... productCategory);

	@Query("SELECT * from product_table ORDER BY isTop DESC,productName ASC")
	List<Products> getAllProduct();

	@Query("SELECT categoryName from sub_category_table WHERE id = :categoryId")
	String getAllSubCategory(int categoryId);

	@Query("SELECT id from sub_category_table WHERE categoryName = :categoryName")
	int getCategoryId(String categoryName);

	@Query("SELECT * from customer ORDER BY id ASC")
	List<Customer> getAllCustomer();

	@Query("SELECT customer_name from customer WHERE mobile_number = :mobileNumber")
	String getCustomerName(String mobileNumber);

	@Query("UPDATE customer SET customer_name = :customerName WHERE mobile_number = :mobileNumber")
	void updateCustomerName(String customerName,String mobileNumber);

	@Query("SELECT categoryId from product_category WHERE productId = :productId")
	int[] getSelectedCategory(int productId);

	@Query("SELECT stock from product_stock WHERE productName = :productId AND categoryName=:categoryID")
	double getStock(int productId,int categoryID);

	@Query("SELECT price from product_price WHERE productName = :productId AND categoryName=:categoryID")
	double getPrice(int productId,int categoryID);

	@Query("SELECT * from product_stock")
	List<ProductStock> getStock();

	@Query("UPDATE product_stock SET stock = :stock WHERE productName = :productName AND categoryName=:categoryName")
	void updateStock(double stock,int productName,int categoryName);
}
