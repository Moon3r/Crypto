package com.example.crypto.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cmbc.firefly.security.crypto.GmsslCryptoStrategy;
import com.cmbc.firefly.security.crypto_adapter.CryptoUtil;
import com.cmbc.firefly.security.crypto_adapter.ICryptoStrategy;

public abstract class CryptoBaseActivity extends BaseActivity {
    protected ICryptoStrategy GmsslStrategy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCrypto();
    }

    private void initCrypto() {
        GmsslStrategy = new GmsslCryptoStrategy();
        CryptoUtil.getInstance().setCryptoStrategy(GmsslStrategy);
    }
}
