package com.bes.illusion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.bes.illusion.RoomDB.DatabaseDAO;
import com.bes.illusion.RoomDB.ProductStock;
import com.bes.illusion.RoomDB.Products;
import com.bes.illusion.RoomDB.RoomDB;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class FormActivity extends AppCompatActivity
{

	private String[] product_Type;
	private String[] tier_Type = {"--Select Product--"};
	TextInputEditText qty;
	TextView noQuantity;
	AppCompatSpinner tierType, productType;
	DatabaseDAO databaseDAO;
	RoomDB db;
	int productID;
	List<Products> productsList;
	double availableStock, ProductPrice, tranportPrice = 0, packagePrice = 0;
	boolean transportChecked=true, packageChecked=true;
	RadioGroup transportGroup, packageGroup;
	MaterialRadioButton tOwn, tCompany, pOwn, pCompany;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		productType = findViewById(R.id.productType);
		qty = findViewById(R.id.qty);
		transportGroup = findViewById(R.id.transportGroup);
		packageGroup = findViewById(R.id.packageGroup);
		tOwn = transportGroup.findViewById(R.id.tOwn);
		tCompany = transportGroup.findViewById(R.id.tCompany);
		pOwn = packageGroup.findViewById(R.id.pOwn);
		pCompany = packageGroup.findViewById(R.id.pCompany);
		noQuantity = findViewById(R.id.noQuantity);
		tierType = findViewById(R.id.tierType);
		transportGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
		{
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				if(checkedId == R.id.tOwn)
				{
					tranportPrice = 0;
					transportChecked = false;
				}
				else if(checkedId == R.id.tCompany)
				{
					tranportPrice = -20;
					transportChecked = false;
				}
			}
		});
		packageGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
		{
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				if(checkedId == R.id.pOwn)
				{
					packagePrice = 0;
					packageChecked = false;
				}
				else if(checkedId == R.id.pCompany)
				{
					packagePrice = -20;
					packageChecked = false;
				}
			}
		});
		db = RoomDB.getDatabase(this);
		databaseDAO = db.databaseDAO();
		db.databaseWriteExecutor.execute(new Runnable()
		{
			@Override
			public void run()
			{
				productsList = databaseDAO.getAllProduct();
				product_Type = new String[productsList.size()];
				int i = 0;
				for(Products p : productsList)
				{
					product_Type[i++] = p.getProductName();
				}
				ArrayAdapter<String> product_type = new ArrayAdapter<>(FormActivity.this, android.R.layout.simple_spinner_dropdown_item, product_Type);
				product_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				productType.setAdapter(product_type);
			}
		});

		productType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, final int position, long id)
			{
				db.databaseWriteExecutor.execute(new Runnable()
				{
					@Override
					public void run()
					{
						if(position != 0)
						{
							productID = productsList.get(position).getId();
							int[] categoryId = databaseDAO.getSelectedCategory(productID);
							tier_Type = new String[categoryId.length + 1];
							int k = 1;
							tier_Type[0] = "--Select One--";
							for(int i : categoryId)
							{
								tier_Type[k++] = databaseDAO.getAllSubCategory(i);
							}
							runOnUiThread(new Runnable()
							{
								@Override
								public void run()
								{
									ArrayAdapter<String> tier_type = new ArrayAdapter<>(FormActivity.this, android.R.layout.simple_spinner_dropdown_item, tier_Type);
									tier_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
									tierType.setAdapter(tier_type);
								}
							});
						}
						else
						{
							runOnUiThread(new Runnable()
							{
								@Override
								public void run()
								{
									tier_Type = new String[] {"--Select Product--"};
									ArrayAdapter<String> tier_type = new ArrayAdapter<>(FormActivity.this, android.R.layout.simple_spinner_dropdown_item, tier_Type);
									tier_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
									tierType.setAdapter(tier_type);
								}
							});
						}
					}

				});
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{

			}
		});

		tierType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, final int position, long id)
			{
				db.databaseWriteExecutor.execute(new Runnable()
				{
					@Override
					public void run()
					{
						if(position != 0)
						{
							int CategoryId = databaseDAO.getCategoryId(tierType.getSelectedItem().toString());
							availableStock = databaseDAO.getStock(productID, CategoryId);
							ProductPrice = databaseDAO.getPrice(productID, CategoryId);
							runOnUiThread(new Runnable()
							{
								@Override
								public void run()
								{
									noQuantity.setVisibility(View.VISIBLE);
									noQuantity.setText(String.format("%s - Quantities Available", availableStock));
								}
							});
						}
						else
						{
							runOnUiThread(new Runnable()
							{
								@Override
								public void run()
								{
									noQuantity.setVisibility(View.INVISIBLE);
								}
							});
						}
					}

				});
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{

			}
		});

		MaterialButton result = findViewById(R.id.result);
		result.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(qty.getText().toString() == null || qty.getText().toString().isEmpty())
				{
					Snackbar.make(v, "Enter Quantity", Snackbar.LENGTH_LONG).show();
				}
				else if(Double.parseDouble(qty.getText().toString()) > availableStock)
				{
					Snackbar.make(v, "Please Enter Less Quantity", Snackbar.LENGTH_LONG).show();
				}
				else if(transportChecked)
				{
					Snackbar.make(v, "Please Select Transport Type", Snackbar.LENGTH_LONG).show();
				}
				else if(packageChecked)
				{
					Snackbar.make(v, "Please Select Package Type", Snackbar.LENGTH_LONG).show();
				}
				else if(Double.parseDouble(qty.getText().toString()) > availableStock)
				{
					Snackbar.make(v, "Please Enter Less Quantity", Snackbar.LENGTH_LONG).show();
				}
				else
				{
					db.databaseWriteExecutor.execute(new Runnable()
					{
						@Override
						public void run()
						{
							int CategoryId = databaseDAO.getCategoryId(tierType.getSelectedItem().toString());
							databaseDAO.updateStock(availableStock - Double.parseDouble(qty.getText().toString()), productID, CategoryId);
							ProductPrice = databaseDAO.getPrice(productID, CategoryId);
							runOnUiThread(new Runnable()
							{
								@Override
								public void run()
								{
									noQuantity.setVisibility(View.VISIBLE);
									noQuantity.setText(String.format("%s - Quantities Available", availableStock));
								}
							});

						}

					});
					ViewGroup viewGroup = v.findViewById(android.R.id.content);
					View dialogView = LayoutInflater.from(FormActivity.this).inflate(R.layout.dialog_result, viewGroup, false);
					MaterialButton close = dialogView.findViewById(R.id.close);
					TextView result = dialogView.findViewById(R.id.result);
					result.setText("Product price - ₹" + ProductPrice + "\nTransport Price - ₹" + tranportPrice + "\nPackage Price - ₹"+packagePrice +"\nOverall Total - ₹" + (ProductPrice + packagePrice + tranportPrice));
					AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this);
					builder.setView(dialogView);
					final AlertDialog alertDialog = builder.create();
					close.setOnClickListener(new View.OnClickListener()
					{
						@Override
						public void onClick(View v)
						{
							Intent intent = getIntent();
							finish();
							startActivity(intent);
						}
					});
					alertDialog.setCanceledOnTouchOutside(false);
					alertDialog.show();
				}
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item)
	{
		if(item.getItemId() == android.R.id.home) finish();
		return false;
	}
}
