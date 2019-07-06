package com.example.crypto.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.crypto.view.Header;
import com.example.crypto.R;

import butterknife.ButterKnife;

public abstract class BaseActivity extends FragmentActivity {
    protected Header mHeader;
    protected View mContentView;
    private LinearLayout container;
    private RelativeLayout headerContainer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.base_title_layout);

        headerContainer = findViewById(R.id.header_container);

        getExtra();

        initHeader(headerContainer);

        addView();
    }


    protected abstract Header onCreateHeader(Header.Builder builder);

//    protected abstract View onCreateContentView();

    protected abstract int getContentLayoutId();

    protected void getExtra(){

    }

    protected  void initViews(){

    }

    protected abstract void initData();



    private void initHeader(RelativeLayout headerContainer) {
        Header.Builder builder = new Header.Builder(this, headerContainer);
        mHeader = onCreateHeader(builder);
        if (mHeader == null) {
            headerContainer.setVisibility(View.GONE);
            return;
        }
    }


    private void addView() {
        mContentView = View.inflate(this, getContentLayoutId(), null);
        ButterKnife.bind(this, mContentView);
        if (mContentView != null) {
            container = findViewById(R.id.main_layout_container);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
            lp.weight = 1;
            container.addView(mContentView, lp);
        }
        initViews();
        initData();
    }

}
