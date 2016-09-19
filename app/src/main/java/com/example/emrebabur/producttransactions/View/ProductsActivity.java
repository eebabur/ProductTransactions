package com.example.emrebabur.producttransactions.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.emrebabur.producttransactions.Pojo.ProductList;
import com.example.emrebabur.producttransactions.Presenter.ProductLoaderService;
import com.example.emrebabur.producttransactions.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductsActivity extends AppCompatActivity {

    private ProductList mProducts = null;
    private ProductAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        ButterKnife.bind(this);
        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(mProducts != null)
                {
                    Intent intent = new Intent(getApplicationContext(), TransactionsActivity.class);
                    intent.putExtra("product", mProducts.get(i));
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        if(mProducts == null) {
            productList.setVisibility(View.GONE);
            loadingAnimation.setVisibility(View.VISIBLE);
            Intent intent = new Intent(this, ProductLoaderService.class);
            startService(intent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showProducts(final ProductList products) {
        mProducts = products;
        mAdapter = new ProductAdapter(getApplicationContext(), products);
        productList.setAdapter(mAdapter);
        loadingAnimation.setVisibility(View.GONE);
        productList.setVisibility(View.VISIBLE);
    }

    @Bind(R.id.LoadingAnimation)
    RelativeLayout loadingAnimation;

    @Bind(R.id.ProductList)
    ListView productList;
}
