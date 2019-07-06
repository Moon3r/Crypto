package com.example.crypto.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.cmbc.firefly.security.crypto_adapter.CryptoUtil;
import com.example.crypto.view.Header;
import com.example.crypto.R;

import butterknife.BindView;
import butterknife.OnClick;

public class SymmetryActivity extends CryptoBaseActivity {

    // AES对称加密算法
    @BindView(R.id.aes_type_et)
    EditText aesTypeEt;
    @BindView(R.id.aes_iv_et)
    EditText aesIvEt;
    @BindView(R.id.aes_key_et)
    EditText aesKeyEt;
    @BindView(R.id.aes_data_et)
    EditText aesDataEt;
    @BindView(R.id.aes_encrypted_tv)
    TextView aesEncryptedTv;
    @BindView(R.id.aes_decrypted_tv)
    TextView aesDncryptedTv;

    // SM4对称加密算法
    @BindView(R.id.sm4_type_et)
    EditText sm4TypeEt;
    @BindView(R.id.sm4_iv_et)
    EditText sm4IvEt;
    @BindView(R.id.sm4_key_et)
    EditText sm4KeyEt;
    @BindView(R.id.sm4_data_et)
    EditText sm4DataEt;
    @BindView(R.id.sm4_encrypted_tv)
    TextView sm4EncryptedTv;
    @BindView(R.id.sm4_decrypted_tv)
    TextView sm4DncryptedTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Header onCreateHeader(Header.Builder builder) {
        return builder.setTitle("对称加密算法")
                      .setBackButton(v -> finish())
                      .build();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.symmetry_activity;
    }

    @Override
    protected void initViews() {
        super.initViews();
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.aes_encrypt_btn)
    void aesEncrypt() {
        CryptoUtil.getInstance().setCryptoStrategy(GmsslStrategy);
        String aesData = aesDataEt.getText().toString();
        String aesKey = aesKeyEt.getText().toString();
        String aesIv = aesIvEt.getText().toString();
        String encryptedData = null;
        try {
            encryptedData = CryptoUtil.getInstance().encryptAES256_CBC2Base64(aesData, aesKey, aesIv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        aesEncryptedTv.setText(encryptedData);
    }

    @OnClick(R.id.aes_decrypt_btn)
    void aesDecrypt() {
        String aesKey = aesKeyEt.getText().toString();
        String aesIv = aesIvEt.getText().toString();
        String encryptedData = aesEncryptedTv.getText().toString();
//        String encryptedData = "YIZWSiE/mp02Nc9Vxnbufs3436kgdRE2E/sc7ui8wcp7pz37LRJXStCUhxwBDj1gRjK7GpgOpAZ9K+hhm4p3Le3O1c8r96OtHJWoTKPf+EtQyo5XUMZVJwslj9XlYqQz";
        String decryptedData = null;
        try {
            decryptedData = CryptoUtil.getInstance().decryptBase64AES256_CBC(encryptedData, aesKey, aesIv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        aesDncryptedTv.setText(decryptedData);
    }

    @OnClick(R.id.sm4_encrypt_btn)
    void sm4Encrypt() {
        CryptoUtil.getInstance().setCryptoStrategy(GmsslStrategy);
        String sm4Data = sm4DataEt.getText().toString();
        String sm4Key = sm4KeyEt.getText().toString();
        String sm4Iv = sm4IvEt.getText().toString();
        String encryptedData = null;
        try {
            encryptedData = CryptoUtil.getInstance().encryptSM4_CBC2Base64(sm4Data, sm4Key, sm4Iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sm4EncryptedTv.setText(encryptedData);
    }

    @OnClick(R.id.sm4_decrypt_btn)
    void sm4Decrypt() {
        String sm4Key = sm4KeyEt.getText().toString();
        String sm4Iv = sm4IvEt.getText().toString();
        String encryptedData = sm4EncryptedTv.getText().toString();
//        String encryptedData = "c+hGnIrSRPt5Yqvz2eWVY2bPCTHe2jGeb13y8JwJb3mHA0V/kpVxFdaEBv3He43PYL3jU+Z2wH9rAVuScRIJpBOq9fM+BSbZjWVxDYSAgA3PgrQqivMU0efGPvsm4VuU";
        String decryptedData = null;
        try {
            decryptedData = CryptoUtil.getInstance().decryptBase64SM4_CBC(encryptedData, sm4Key, sm4Iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sm4DncryptedTv.setText(decryptedData);
    }
}
