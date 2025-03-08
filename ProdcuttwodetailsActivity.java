package com.entrolabs.firebaseauth.storeapptwo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.entrolabs.firebaseauth.R;
import com.entrolabs.firebaseauth.storeapp.Product;

public class ProdcuttwodetailsActivity extends AppCompatActivity {
    TextView productTitle, productPrice, productDescription;
    ImageView productImage;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prodcuttwodetails);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        productTitle = findViewById(R.id.productTitle);
        productPrice = findViewById(R.id.productPrice);
        productDescription = findViewById(R.id.productDescription);
        productImage = findViewById(R.id.productImage);

        Product product = (Product) getIntent().getSerializableExtra("PRODUCT");

        productTitle.setText(product.getTitle());
        productPrice.setText("$" + product.getPrice());
        productDescription.setText(product.getDescription());
        Glide.with(this).load(product.getImage()).into(productImage);

    }
}