package com.example.crypto.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.crypto.ConvertUtil;
import com.example.crypto.R;

public final class Header {

    HeaderController mController;

    protected Header(HeaderController hc) {
        mController = hc;
    }

    public RelativeLayout getView() {
        return mController.mContainer;
    }

    public void hideHeader() {
        mController.mContainer.setVisibility(View.GONE);
    }

    public void showHeader() {
        mController.mContainer.setVisibility(View.VISIBLE);
    }

    public TextView getLeftButton() {
        return mController.mLeftButton;
    }

    public TextView getRightTextView(){
        return mController.mRightTextView;
    }

    public ImageView getRightButton() {
        return mController.mRightButton;
    }

    public View getBackButton() {
        return mController.mBackView;
    }

    public TextView getTitleView() {
        return mController.mTitleView;
    }

    public static class Builder {
        HeaderController pHc;

        public Context mContext;

        public Builder(final Activity context, RelativeLayout container) {
            mContext = context;
            pHc = new HeaderController();
            pHc.mContainer = container;
            pHc.mContainer.setBackgroundColor(Color.WHITE);
            setBackButton(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.finish();
                }
            });
        }

        //设置头布局background
        public Builder setHeaderColor(int resId) {
            pHc.mContainer.setBackgroundResource(resId);
            return this;
        }

        public Builder showBackBtn(boolean isShow) {
            if (pHc.mBackView != null) {
                if(isShow){
                    pHc.mBackView.setVisibility(View.VISIBLE);
                }else{
                    pHc.mBackView.setVisibility(View.GONE);
                }
            }
            return this;
        }

        public Builder setBackButton(@Nullable View.OnClickListener listener) {
            ImageView backView = new ImageView(mContext);
            backView.setImageResource(R.mipmap.secondary_menu_navi_arrow);
            if (listener != null) {
                backView.setOnClickListener(listener);
            }
            int padding = ConvertUtil.dip2px(mContext, 6);
            backView.setPadding(padding, padding, padding, padding);
            pHc.mBackView = backView;
            return this;
        }

        public Builder setLeftButton(int text, View.OnClickListener clickListener) {
            String content = "";
            if (text > 0) {
                content = mContext.getResources().getString(text);
            }
            TextView btn = new TextView(mContext);
            btn.setBackgroundColor(0x00000000);
//            btn.setTextColor(mContext.getResources().getColor(R.color.guide_white));
            btn.setText(content);
            btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            btn.setGravity(Gravity.CENTER);
            btn.setOnClickListener(clickListener);
            pHc.mLeftButton = btn;
            return this;
        }

        public Builder setRightButton(int resid, View.OnClickListener clickListener) {
            ImageView btn = new ImageView(mContext);
            btn.setImageResource(resid);
            btn.setOnClickListener(clickListener);
            int padding = ConvertUtil.dip2px(mContext, 10);
            btn.setPadding(padding, padding, padding, padding);
            pHc.mRightButton = btn;
            return this;
        }

        public Builder setRightTextView(String str, View.OnClickListener clickListener) {
            TextView tv = new TextView(mContext);
            tv.setText(str);
            tv.setOnClickListener(clickListener);
            int padding = ConvertUtil.dip2px(mContext, 10);
            tv.setPadding(padding, padding, padding, padding);
//            tv.setTextColor(mContext.getResources().getColor(R.color.default_blue));
            tv.setGravity(Gravity.CENTER);
            tv.setSingleLine(true);
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setMaxEms(12);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            pHc.mRightTextView = tv;
            return this;
        }

        public Builder setTitle(int resId) {
            String text = mContext.getResources().getString(resId);
            return setTitle(text);
        }

        public Builder setTitle(String title) {
            TextView titleView = new TextView(mContext);
            titleView.setText(title);
//            titleView.setTextColor(ContextCompat.getColor(mContext,R.color.default_text_color));
            titleView.setGravity(Gravity.CENTER);
            titleView.setSingleLine(true);
            titleView.setEllipsize(TextUtils.TruncateAt.END);
            titleView.setMaxEms(12);
            titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            pHc.mTitleView = titleView;
            return this;
        }


        public Header build() {
            Header header = new Header(pHc);
            pHc.apply();
            return header;
        }
    }

    private static class HeaderController {

        private RelativeLayout mContainer;

        private TextView mTitleView;

        private ImageView mBackView;

        private TextView mLeftButton;

        private ImageView mRightButton;

        private TextView mRightTextView;

        HeaderController() {
        }

        void apply() {
            mContainer.removeAllViews();
            RelativeLayout.LayoutParams lp = null;
            if (mBackView != null) {
                lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                lp.addRule(RelativeLayout.CENTER_VERTICAL);
                mContainer.addView(mBackView, lp);
            }

            if (mTitleView != null) {
                lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                mTitleView.setGravity(Gravity.CENTER);
                mContainer.addView(mTitleView, lp);
            }

            if (mLeftButton != null) {
                lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                lp.addRule(RelativeLayout.CENTER_VERTICAL);
                lp.leftMargin = 15;
                mContainer.addView(mLeftButton, lp);
            }

            if (mRightButton != null) {
                lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                lp.addRule(RelativeLayout.CENTER_VERTICAL);
                mContainer.addView(mRightButton, lp);
            }
            if (mRightTextView != null) {
                lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                lp.addRule(RelativeLayout.CENTER_VERTICAL);
                mContainer.addView(mRightTextView, lp);
            }
        }
    }


}
