package com.motivi.santa.demo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kr.motivi.santa.NoAdCode
import kr.motivi.santa.ads.OnAdListener
import kr.motivi.santa.ads.view.NativeAsset
import kr.motivi.santa.ads.view.NativeViewBinder
import kr.motivi.santa.ads.view.SantaAdView

class DemoActivityKt : AppCompatActivity() {

    private lateinit var santaAdView: SantaAdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity)
        santaAdView = findViewById(R.id.adview)

        // layout xml에 적용하지 않았다면 동적으로 unit_id를 적용한다.
        santaAdView.setAdUnitId(getString(R.string.unit_id));

        // layout xml에 적용하지 않았다면 동적으로 AdFormat를 적용한다.
//        santaAdView.setAdFormats(AdFormat.HTML);

        santaAdView.setOnAdListener(object : OnAdListener {
            override fun onLoad() {
                // 광고 로딩
                santaAdView.setVisibility(View.VISIBLE)
            }

            override fun onNoAd(noAdCode: NoAdCode) {
                // 광고 없음
                santaAdView.setVisibility(View.GONE)
            }

            override fun onShow() {
                // 광고 노출됨
            }

            override fun onClick() {
                // 광고 클릭됨
            }
        })

        // SantaAdView 광고 호출
        findViewById<View>(R.id.load_btn).setOnClickListener { view: View? -> santaAdView.loadAd() }
        /**
         * 응답된 Native 광고 Data가 아래 설정된 id에 의해서 노출된다.
         */
        val nativeViewBinder = NativeViewBinder.Builder()
            .mainImageId(R.id.native_main_image)
            .callToActionButtonId(R.id.native_cta)
            .titleTextViewId(R.id.native_title)
            .textTextViewId(R.id.native_text)
            .iconImageId(R.id.native_icon_image)
            .adInfoImageId(R.id.native_privacy_information_icon_image)
            .build()
        santaAdView.setNativeViewBinder(nativeViewBinder)

        // 클라이언트 레이아웃 구성상 필수로 응답 되어야 할 항목 설정
        santaAdView.setRequiredAsset(
            NativeAsset.TITLE, NativeAsset.CTATEXT, NativeAsset.ICON,
            NativeAsset.MAINIMAGE, NativeAsset.DESC
        )
    }

    public override fun onDestroy() {
        if (santaAdView != null) {
            santaAdView!!.onDestroy()
        }
        super.onDestroy()
    }
}