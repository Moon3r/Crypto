package com.example.crypto.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.cmbc.firefly.security.crypto_adapter.CryptoUtil;
import com.example.crypto.view.Header;
import com.example.crypto.R;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageActivity extends CryptoBaseActivity {
    // 消息加密算法
    @BindView(R.id.message_type_et)
    EditText messageTypeEt;
    @BindView(R.id.message_key_et)
    EditText messageKeyEt;
    @BindView(R.id.message_data_et)
    EditText messageDataEt;
    @BindView(R.id.message_encrypted_tv)
    TextView messageEncryptedTv;
    @BindView(R.id.message_decrypted_tv)
    TextView messageDncryptedTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Header onCreateHeader(Header.Builder builder) {
        return builder.setTitle("消息加密算法")
                      .setBackButton(v -> MessageActivity.this.finish())
                      .build();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.message_activity;
    }

    @Override
    protected void initViews() {
        super.initViews();
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.message_encrypt_btn)
    void messageEncrypt() {
        String messageData = messageDataEt.getText().toString();
        String messageKey = messageKeyEt.getText().toString();
        String encryptedData = null;
        try {
            encryptedData = CryptoUtil.getInstance().messageEncrypt2Base64(messageData, messageKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        messageEncryptedTv.setText(encryptedData);
    }

    @OnClick(R.id.message_decrypt_btn)
    void messageDecrypt() {
        String messageKey = messageKeyEt.getText().toString();
        String encryptedData = messageEncryptedTv.getText().toString();
//        String encryptedData = "SfE6LdCk5DlZeI3yfsciek55reuH6vYCEpqKncH/I9gSsjjaS2c9WvKoUiIYpev2elGbctVnYU+YMhda2d1Gug==";
        String decryptedData = null;
        try {
            decryptedData = CryptoUtil.getInstance().messageDecryptBase64(encryptedData, messageKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        messageDncryptedTv.setText(decryptedData);
    }
}
