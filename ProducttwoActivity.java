package com.entrolabs.firebaseauth.storeapptwo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.entrolabs.firebaseauth.R;
import com.entrolabs.firebaseauth.storeapp.ApiService2;
import com.entrolabs.firebaseauth.storeapp.Product;
import com.entrolabs.firebaseauth.storeapp.RetrofitClient2;
import com.entrolabs.firebaseauth.streamingapp.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProducttwoActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapterTwo adapter;
    private List<Product> productList = new ArrayList<>();
    private ProgressBar progressBar;
    private EditText searchInput;
    Toolbar toolbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producttwo);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        //   progressBar = findViewById(R.id.progressBar);
        searchInput = findViewById(R.id.searchInput);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new ProductAdapterTwo(this, productList);
        recyclerView.setAdapter(adapter);

        fetchProducts();

        adapter.setOnItemClickListener(product -> {
            Intent intent = new Intent(ProducttwoActivity.this, ProdcuttwodetailsActivity.class);
          //  intent.putExtra("PRODUCT_ID", product.getId());
            intent.putExtra("PRODUCT", product);
            startActivity(intent);
        });

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void fetchProducts() {
        //  progressBar.setVisibility(View.VISIBLE);
        ApiService2 apiService = RetrofitClient2.getInstance().create(ApiService2.class);
        apiService.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                //  progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    productList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ProducttwoActivity.this, "Failed to load products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                //  progressBar.setVisibility(View.GONE);
                Toast.makeText(ProducttwoActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterProducts(String query) {
        List<Product> filteredList = new ArrayList<>();
        for (Product product : productList) {
            if (product.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(product);
            }
        }
        adapter = new ProductAdapterTwo(this, filteredList);
        recyclerView.setAdapter(adapter);
    }

}