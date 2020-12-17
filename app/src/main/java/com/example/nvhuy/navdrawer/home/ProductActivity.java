// to control recyclerView scroll action

package com.example.nvhuy.navdrawer.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nvhuy.navdrawer.Adapter.RecyclerAdapter_category;
import com.example.nvhuy.navdrawer.Adapter.RecyclerViewAdapter;
import com.example.nvhuy.navdrawer.R;
import com.example.nvhuy.navdrawer.models.Category;
import com.example.nvhuy.navdrawer.models.Fruit;
import com.example.nvhuy.navdrawer.models.MyDatabaseHelper;

import java.util.ArrayList;


public class ProductActivity extends AppCompatActivity {
    RecyclerView recyclerView_sameProduct;
    RecyclerViewAdapter adapter_sameProduct;
    ArrayList<Fruit> items;
    MyDatabaseHelper dbHelper;
    TextView product_name,product_cost, product_company, product_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        AnhXa();
        setData();


        dbHelper = new MyDatabaseHelper(this);
        dbHelper.createDefaultProductsIfNeed();

        //todo recyclerView sameProduct
        items = new ArrayList<>();
        items = dbHelper.getAllProduct();
        recyclerView_sameProduct = findViewById(R.id.rv_sameProduct);
        adapter_sameProduct = new RecyclerViewAdapter(items);
        recyclerView_sameProduct.setAdapter(adapter_sameProduct);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView_sameProduct);
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        recyclerView_sameProduct.setLayoutManager(gridLayoutManager);
    }

    private void setData() {
        product_name.setText(getIntent().getExtras().getString("NAME"));
        product_cost.setText(getIntent().getExtras().getString("COST"));
        product_company.setText(getIntent().getExtras().getString("COMPANY"));
        product_description.setText(getIntent().getExtras().getString("DESCRIPTION"));

    }

    private void AnhXa() {
        product_name = findViewById(R.id.product_name);
        product_cost = findViewById(R.id.product_cost);
        product_company = findViewById(R.id.product_company);
        product_description = findViewById(R.id.product_description);
    }

}
