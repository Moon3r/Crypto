package com.example.crypto.activity;

import android.os.Bundle;
import android.util.Base64;
import android.widget.EditText;
import android.widget.TextView;

import com.cmbc.firefly.security.crypto_adapter.CryptoUtil;
import com.cmbc.firefly.security.crypto_adapter.RSASignHashType;
import com.example.crypto.view.Header;
import com.example.crypto.R;

import butterknife.BindView;
import butterknife.OnClick;

public class AsymmetricActivity extends CryptoBaseActivity {
    // RSA非对称加密算法
    @BindView(R.id.rsa_type_et)
    EditText rsaTypeEt;
    @BindView(R.id.rsa_public_key_et)
    EditText rsaPublicKeyEt;
    @BindView(R.id.rsa_private_key_et)
    EditText rsaPrivateKeyEt;
    @BindView(R.id.rsa_data_et)
    EditText rsaDataEt;
    @BindView(R.id.rsa_encrypted_tv)
    TextView rsaEncryptedTv;
    @BindView(R.id.rsa_decrypted_tv)
    TextView rsaDecryptedTv;
    @BindView(R.id.rsa_sign_result_tv)
    TextView rsaSignResultTv;
    @BindView(R.id.rsa_verify_result_tv)
    TextView rsaVerifyResultTv;

    // SM2非对称加密算法
    @BindView(R.id.sm2_public_key_et)
    EditText sm2PublicKeyEt;
    @BindView(R.id.sm2_private_key_et)
    EditText sm2PrivateKeyEt;
    @BindView(R.id.sm2_data_et)
    EditText sm2DataEt;
    @BindView(R.id.sm2_encrypted_tv)
    TextView sm2EncryptedTv;
    @BindView(R.id.sm2_decrypted_tv)
    TextView sm2DecryptedTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Header onCreateHeader(Header.Builder builder) {
        return builder.setTitle("非对称加密算法")
                .setBackButton(v -> finish())
                .build();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.asymmetric_activity;
    }

    @Override
    protected void initViews() {
        super.initViews();
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.sm2_encrypt_btn)
    void sm2Encrypt() {
        CryptoUtil.getInstance().setCryptoStrategy(GmsslStrategy);
        String sm2Data = sm2DataEt.getText().toString();
        String sm2PublicKey = sm2PublicKeyEt.getText().toString();
        String encryptedData = null;
        try {
            encryptedData = CryptoUtil.getInstance().encryptSM2ToBase64(sm2Data, sm2PublicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sm2EncryptedTv.setText(encryptedData);
    }

    @OnClick(R.id.sm2_decrypt_btn)
    void sm2Decrypt() {
        CryptoUtil.getInstance().setCryptoStrategy(GmsslStrategy);
        //        sm2EncryptedTv.setText("vcL2CpENIn0dSbuqJDkmp9wdsdsnbOhujLm7rTGot5JJx0uCyDKAcNmbejVK/cUKV6/T9Ir7m+tDTCOEh3+MzmDg4F8taHZ7GhtI2Db0l8LR7lldqcpiPbs8KcKMnhD99+//ehAvNnKanNdDkpvNyMvRVkpOBQikuSmsmcJ3D8Q=");
        String sm2PrivateKey = sm2PrivateKeyEt.getText().toString();
        String encryptedData = sm2EncryptedTv.getText().toString();
        String decryptedData = null;
        try {
            decryptedData = CryptoUtil.getInstance().decryptBase64SM2(encryptedData, sm2PrivateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sm2DecryptedTv.setText(decryptedData);
    }

    @OnClick(R.id.rsa_encrypt_btn)
    void rsaEncrypt() {
        String rsaData = rsaDataEt.getText().toString();
        String rsaPublicKey = rsaPublicKeyEt.getText().toString();
        byte[] encryptedData = null;
        byte[] encryptedData3 = null;
        byte[] publicKey = Base64.decode(rsaPublicKey, Base64.NO_WRAP);
        try {
            encryptedData = CryptoUtil.getInstance().encryptRSA(rsaData.getBytes(), publicKey);
            // 多次加密
            byte[] encryptedData2 = CryptoUtil.getInstance().encryptRSA(encryptedData, publicKey);
            encryptedData3 = CryptoUtil.getInstance().encryptRSA(encryptedData2, publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        rsaEncryptedTv.setText(Base64.encodeToString(encryptedData3, Base64.NO_WRAP));
    }

    @OnClick(R.id.rsa_decrypt_btn)
    void rsaDecrypt() {
        CryptoUtil.getInstance().setCryptoStrategy(GmsslStrategy);
        //        rsaEncryptedTv.setText("vcL2CpENIn0dSbuqJDkmp9wdsdsnbOhujLm7rTGot5JJx0uCyDKAcNmbejVK/cUKV6/T9Ir7m+tDTCOEh3+MzmDg4F8taHZ7GhtI2Db0l8LR7lldqcpiPbs8KcKMnhD99+//ehAvNnKanNdDkpvNyMvRVkpOBQikuSmsmcJ3D8Q=");
        String rsaPrivateKey = rsaPrivateKeyEt.getText().toString();
        String encryptedData = rsaEncryptedTv.getText().toString();
        byte[] decryptedData = null;
        byte[] decryptedData3 = null;
        byte[] privateKey = Base64.decode(rsaPrivateKey, Base64.NO_WRAP);
        try {
            decryptedData = CryptoUtil.getInstance().decryptRSA(Base64.decode(encryptedData, Base64.NO_WRAP), privateKey);
            byte[] decryptedData2 = CryptoUtil.getInstance().decryptRSA(decryptedData, privateKey);
            decryptedData3 = CryptoUtil.getInstance().decryptRSA(decryptedData2, privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 多次解密
        rsaDecryptedTv.setText(new String(decryptedData3));
    }

    @OnClick(R.id.rsa_sign_btn)
    void rsaSign() {
        CryptoUtil.getInstance().setCryptoStrategy(GmsslStrategy);
        String rsaData = rsaDataEt.getText().toString();
        String rsaPrivateKey = rsaPrivateKeyEt.getText().toString();
        String rsaSignResultData = null;
        try {
            rsaSignResultData = CryptoUtil.getInstance().rsaSign(rsaData, rsaPrivateKey, RSASignHashType.SHA256);
        } catch (Exception e) {
            e.printStackTrace();
        }
        rsaSignResultTv.setText(rsaSignResultData);
    }

    @OnClick(R.id.rsa_verify_sign_btn)
    void rsaVerifySign() {
        String rsaData = rsaDataEt.getText().toString();
        String rsaPublicKey = rsaPublicKeyEt.getText().toString();
        String rsaSignResultData = rsaSignResultTv.getText().toString();
        boolean rsaVerifyByPublicKey = false;
        try {
            rsaVerifyByPublicKey = CryptoUtil.getInstance().rsaVerifyByPublicKey(rsaData, rsaPublicKey,
                    rsaSignResultData, RSASignHashType.SHA256);
        } catch (Exception e) {
            e.printStackTrace();
        }
        rsaVerifyResultTv.setText(rsaVerifyByPublicKey + "");
    }
}
