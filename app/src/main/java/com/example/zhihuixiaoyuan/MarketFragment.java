package com.example.zhihuixiaoyuan;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class MarketFragment extends Fragment {
    private Button btnPublish;
    private TextInputEditText etSearch;
    private RecyclerView rvProducts;
    private ProductAdapter productAdapter;
    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market, container, false);
        
        dbHelper = new DatabaseHelper(getContext());
        initViews(view);
        setupProductList();
        setupSearch();
        
        return view;
    }

    private void initViews(View view) {
        btnPublish = view.findViewById(R.id.btnPublish);
        etSearch = view.findViewById(R.id.etSearch);
        rvProducts = view.findViewById(R.id.rvProducts);

        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PublishProductActivity.class);
                startActivityForResult(intent, 100);
            }
        });
    }

    private void setupProductList() {
        List<Product> productList = dbHelper.getAllProducts();
        
        productAdapter = new ProductAdapter(productList, new ProductAdapter.OnProductClickListener() {
            @Override
            public void onProductClick(Product product) {
                Intent intent = new Intent(getContext(), ProductDetailActivity.class);
                intent.putExtra("product_id", product.getId());
                startActivity(intent);
            }
        });
        
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rvProducts.setLayoutManager(layoutManager);
        rvProducts.setAdapter(productAdapter);
    }

    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString().trim();
                List<Product> productList;
                if (keyword.isEmpty()) {
                    productList = dbHelper.getAllProducts();
                } else {
                    productList = dbHelper.searchProducts(keyword);
                }
                productAdapter.updateProducts(productList);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == getActivity().RESULT_OK) {
            // 刷新商品列表
            List<Product> productList = dbHelper.getAllProducts();
            productAdapter.updateProducts(productList);
        }
    }
}

