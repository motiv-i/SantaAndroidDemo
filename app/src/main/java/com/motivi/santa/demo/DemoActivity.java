package com.motivi.santa.demo;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import kr.motivi.santa.MediationType;
import kr.motivi.santa.NoAdCode;
import kr.motivi.santa.ads.OnAdListener;
import kr.motivi.santa.ads.view.NativeAsset;
import kr.motivi.santa.ads.view.NativeViewBinder;
import kr.motivi.santa.ads.view.SantaAdView;

public class DemoActivity extends AppCompatActivity {

    SantaAdView santaAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity);

        santaAdView = findViewById(R.id.adview);

        // layout xml에 적용하지 않았다면 동적으로 unit_id를 적용한다.
//        santaAdView.setAdUnitId(getString(R.string.unit_id));

        // layout xml에 적용하지 않았다면 동적으로 AdFormat를 적용한다.
//        santaAdView.setAdFormats(AdFormat.HTML);

        santaAdView.setOnAdListener(new OnAdListener() {
            @Override
            public void onLoad() {
                // 광고 로딩
                santaAdView.setVisibility(View.VISIBLE);
            }
            @Override
            public void onNoAd(NoAdCode noAdCode) {
                // 광고 없음
                santaAdView.setVisibility(View.GONE);
            }
            @Override
            public void onShow() {
                // 광고 노출됨
            }
            @Override
            public void onClick() {
                // 광고 클릭됨
            }

            @Override
            public void onMediation(MediationType mediationType) {
                // 설정된 타 SDK 미디에이션 순서 호출. 클라이언트 설정 없을 시 호출 되지 않음
            }
        });

        // SantaAdView 광고 호출
        findViewById(R.id.load_btn).setOnClickListener(view -> santaAdView.loadAd());

        /**
         * 응답된 Native 광고 Data가 아래 설정된 id에 의해서 노출된다.
         */
        NativeViewBinder nativeViewBinder = new NativeViewBinder.Builder()
                .mainImageId(R.id.native_main_image)
                .callToActionButtonId(R.id.native_cta)
                .titleTextViewId(R.id.native_title)
                .textTextViewId(R.id.native_text)
                .iconImageId(R.id.native_icon_image)
                .adInfoImageId(R.id.native_privacy_information_icon_image)
                .build();
        santaAdView.setNativeViewBinder(nativeViewBinder);

        // 클라이언트 레이아웃 구성상 필수로 응답 되어야 할 항목 설정
        santaAdView.setRequiredAsset(
                NativeAsset.TITLE, NativeAsset.CTATEXT, NativeAsset.ICON,
                NativeAsset.MAINIMAGE, NativeAsset.DESC);
    }

    @Override
    public void onDestroy() {
        if(santaAdView != null){
            santaAdView.onDestroy();
        }
        super.onDestroy();
    }
}