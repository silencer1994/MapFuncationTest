package com.mapfunction.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.leador.api.maps.MapController;
import com.leador.api.maps.MapView;
import com.mapfunction.R;

/**
 * Created by Administrator on 2017/4/26 0026.
 */

public class BaseMapActivity extends Activity{
    private MapView mapView;
    private MapController controller;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapview);
        mapView = (MapView)findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        controller = mapView.getMap();
    }

    /**
     * 方法重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
