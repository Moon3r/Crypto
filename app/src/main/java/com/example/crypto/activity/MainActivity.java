package com.example.crypto.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.crypto.view.Header;
import com.example.crypto.bean.MenuBean;
import com.example.crypto.bean.MenuName;
import com.example.crypto.bean.MenuTag;
import com.example.crypto.view.OnItemClickListener;
import com.example.crypto.R;
import com.example.crypto.view.SecondLevelMenuAdapter;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;


public class MainActivity extends BaseActivity implements OnItemClickListener {

    @BindView(R.id.rv)
    RecyclerView mRv;

    private SecondLevelMenuAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected Header onCreateHeader(Header.Builder builder) {
        return builder.setTitle("").build();
    }

    @Override
    protected void initData() {

        mAdapter = new SecondLevelMenuAdapter(this);
        mLinearLayoutManager = new LinearLayoutManager(this);

        MenuBean digestBean = new MenuBean(MenuName.digest, MenuTag.digest);
        MenuBean symmetryAlgBean = new MenuBean(MenuName.symmetryAlg, MenuTag.symmetryAlg);
        MenuBean asymmetricAlgBean = new MenuBean(MenuName.asymmetricAlg, MenuTag.asymmetricAlg);
        MenuBean messageAlgBean = new MenuBean(MenuName.message, MenuTag.message);

        List<MenuBean> menuBeanList = new LinkedList<>();
        menuBeanList.add(digestBean);
        menuBeanList.add(symmetryAlgBean);
        menuBeanList.add(asymmetricAlgBean);
        menuBeanList.add(messageAlgBean);
        mAdapter.setData(menuBeanList);
        mAdapter.setOnItemClickListener(this);
        mRv.setLayoutManager(mLinearLayoutManager);
        mRv.setAdapter(mAdapter);
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(int position) {
        MenuBean menuBean = mAdapter.getData().get(position);
        String menuKey = menuBean.getKey();
        Intent intent;
        switch (menuKey) {
            case MenuTag.digest:
                intent = new Intent(this, DigestActivity.class);
                startActivity(intent);
                break;
            case MenuTag.symmetryAlg:
                intent = new Intent(this, SymmetryActivity.class);
                startActivity(intent);
                break;
            case MenuTag.asymmetricAlg:
                intent = new Intent(this, AsymmetricActivity.class);
                startActivity(intent);
                break;
            case MenuTag.message:
                intent = new Intent(this, MessageActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}
