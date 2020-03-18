package com.bes.illusion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bes.illusion.R;
import com.bes.illusion.RoomDB.Customer;
import com.bes.illusion.RoomDB.DatabaseDAO;
import com.bes.illusion.RoomDB.Products;
import com.bes.illusion.RoomDB.RoomDB;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity
{
	DatabaseDAO databaseDAO;
	RoomDB db;
	RecyclerView customerRecyclerView;
	SwipeRefreshLayout swipeRefresh;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customer);
		db = RoomDB.getDatabase(this);
		databaseDAO = db.databaseDAO();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		customerRecyclerView = findViewById(R.id.customerRecyclerView);
		swipeRefresh = findViewById(R.id.swipeRefresh);
		swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				db.databaseWriteExecutor.execute(new Runnable()
				{
					@Override
					public void run()
					{
						CustomAdapter customAdapter = new CustomAdapter(databaseDAO.getAllCustomer());
						customerRecyclerView.setAdapter(customAdapter);
						new Handler(Looper.getMainLooper()) {
							@Override
							public void handleMessage(Message message) {
								swipeRefresh.setRefreshing(false);
							}
						}.postDelayed(new Runnable(){
							@Override
							public void run()
							{
								swipeRefresh.setRefreshing(false);
							}
						}, 4000);
					}
				});
			}
		});
		swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
		customerRecyclerView.setLayoutManager(linearLayoutManager);
		db.databaseWriteExecutor.execute(new Runnable()
		{
			@Override
			public void run()
			{
				CustomAdapter customAdapter = new CustomAdapter(databaseDAO.getAllCustomer());
				customerRecyclerView.setAdapter(customAdapter);
			}
		});


	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item)
	{
		if(item.getItemId() == android.R.id.home) finish();
		return false;
	}

	class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>
	{
		List<Customer> customerList;
		public CustomAdapter(List<Customer> customerList)
		{
			this.customerList = customerList;
		}

		@Override
		public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
		{
			View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_list, parent, false);
			MyViewHolder vh = new MyViewHolder(v);
			return vh;
		}

		@Override
		public void onBindViewHolder(MyViewHolder holder, final int position)
		{
			holder.customerName.setText(customerList.get(position).getCustomerName());
			holder.mobileNumber.setText(customerList.get(position).getMobileNumber());
		}

		@Override
		public int getItemCount()
		{
			return customerList.size();
		}

		public class MyViewHolder extends RecyclerView.ViewHolder
		{
			TextView customerName, mobileNumber;

			public MyViewHolder(View itemView)
			{
				super(itemView);
				customerName = itemView.findViewById(R.id.customerName);
				mobileNumber = itemView.findViewById(R.id.mobileNumber);
			}
		}
	}
}
