package com.example.crypto.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.cmbc.firefly.security.crypto_adapter.CryptoUtil;
import com.example.crypto.view.Header;
import com.example.crypto.R;

import butterknife.BindView;

public class DigestActivity extends CryptoBaseActivity {
    // 摘要算法
    @BindView(R.id.digest_param_et)
    EditText digestParamEt;
    @BindView(R.id.md5_tv)
    TextView md5Tv;
    @BindView(R.id.sha1_tv)
    TextView sha1Tv;
    @BindView(R.id.sha256_tv)
    TextView sha256Tv;
    @BindView(R.id.sm3_tv)
    TextView sm3Tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showDigest(digestParamEt.getText().toString());
        setListener();
    }

    @Override
    protected Header onCreateHeader(Header.Builder builder) {
        return builder.setTitle("摘要算法")
                      .setBackButton(v -> DigestActivity.this.finish())
                      .build();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.digest_activity;
    }

    private void showDigest(String s) {
        try {
            md5Tv.setText(CryptoUtil.getInstance().encryptMD5ToHexString(s));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            sha1Tv.setText(CryptoUtil.getInstance().encryptSHA1ToHexString(s));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            sha256Tv.setText(CryptoUtil.getInstance().encryptSHA256ToHexString(s));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            sm3Tv.setText(CryptoUtil.getInstance().encryptSM3ToHexString(s));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initViews() {
        super.initViews();
    }

    @Override
    protected void initData() {

    }

    private void setListener() {
        digestParamEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                showDigest(s.toString());
            }
        });
    }
}
