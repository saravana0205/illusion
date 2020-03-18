package com.bes.illusion;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bes.illusion.RoomDB.Customer;
import com.bes.illusion.RoomDB.DatabaseDAO;
import com.bes.illusion.RoomDB.RoomDB;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity
{
	RoomDB db;
	DatabaseDAO databaseDAO;
	TextInputEditText phoneNumber;
	static TextInputEditText customerName;
	String name;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		db = RoomDB.getDatabase(this);
		databaseDAO = db.databaseDAO();
		MaterialButton button = findViewById(R.id.button);
		customerName = findViewById(R.id.customerName);
		phoneNumber = findViewById(R.id.phoneNumber);
		phoneNumber.addTextChangedListener(new TextWatcher()
		{

			public void afterTextChanged(Editable s){}

			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
			}

			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				if(s.length() == 10)
				{
					db.databaseWriteExecutor.execute(new Runnable()
					{
						@Override
						public void run()
						{
							name = databaseDAO.getCustomerName(phoneNumber.getText().toString());
							Handler refresh = new Handler(Looper.getMainLooper());
							refresh.post(new Runnable()
							{
								public void run()
								{
									if(name != null)
									{
										customerName.setText(name);
										customerName.setSelection(name.length());
									}
								}
							});
						}
					});
				}
			}
		});
		phoneNumber.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
			{
				if(phoneNumber.getText().length() == 10)
				{

					db.databaseWriteExecutor.execute(new Runnable()
					{
						@Override
						public void run()
						{
							name = databaseDAO.getCustomerName(phoneNumber.getText().toString());
							Handler refresh = new Handler(Looper.getMainLooper());
							refresh.post(new Runnable()
							{
								public void run()
								{
									if(name != null)
									{
										customerName.setText(name);
										customerName.setSelection(name.length());
									}
								}
							});
						}
					});
				}
				else
				{
					phoneNumber.setError("Minimum 10 digits required!!");
				}

				return false;
			}
		});

		customerName.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
			{
				if(customerName.getText().toString().trim().isEmpty())
				{
					customerName.setError("Customer Name Required!!");
				}
				else
				{
					if(name == null)
					{
						db.databaseWriteExecutor.execute(new Runnable()
						{
							@Override
							public void run()
							{
								databaseDAO.insertCustomer(new Customer(customerName.getText().toString(), phoneNumber.getText().toString()));
							}
						});
					}
					else if(!customerName.getText().toString().equals(name))
					{
						db.databaseWriteExecutor.execute(new Runnable()
						{
							@Override
							public void run()
							{
								databaseDAO.updateCustomerName(customerName.getText().toString(), phoneNumber.getText().toString());
							}
						});
					}
					customerName.setText("");
					phoneNumber.setText("");
					startActivity(new Intent(LoginActivity.this, FormActivity.class));
				}

				return false;
			}
		});

		button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(phoneNumber.getText().length() == 10 && !customerName.getText().toString().trim().isEmpty())
				{
					if(name == null)
					{
						db.databaseWriteExecutor.execute(new Runnable()
						{
							@Override
							public void run()
							{
								databaseDAO.insertCustomer(new Customer(customerName.getText().toString(), phoneNumber.getText().toString()));
							}
						});
					}
					else if(!customerName.getText().toString().equals(name))
					{
						db.databaseWriteExecutor.execute(new Runnable()
						{
							@Override
							public void run()
							{
								databaseDAO.updateCustomerName(customerName.getText().toString(), phoneNumber.getText().toString());
							}
						});
					}
					customerName.setText("");
					phoneNumber.setText("");
					startActivity(new Intent(LoginActivity.this, FormActivity.class));
				}
				else
				{
					Snackbar.make(v, "Please Fill all Mandatory Fields", Snackbar.LENGTH_LONG).show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.add_login_customer_menu, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		MenuItem item = menu.findItem(R.id.action_login_customer);
		item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
		{
			@Override
			public boolean onMenuItemClick(MenuItem item)
			{
				customerName.setText("");
				phoneNumber.setText("");
				startActivity(new Intent(LoginActivity.this, CustomerActivity.class));
				return false;
			}
		});
		return super.onPrepareOptionsMenu(menu);
	}
}
