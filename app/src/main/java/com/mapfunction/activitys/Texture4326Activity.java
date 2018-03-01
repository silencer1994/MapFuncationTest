package com.mapfunction.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.leador.api.maps.MapController;
import com.leador.api.maps.TextureMapView;
import com.mapfunction.R;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

public class Texture4326Activity extends Activity{
    private TextureMapView mapView;
    private MapController controller;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textureview);
        mapView = (TextureMapView)findViewById(R.id.texture_view);
//        mapView.setCoodSystem(MapOptions.COOD_SYSTEM_4326);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        controller = mapView.getMap();
//        controller.setGridUrlListener(new MapController.GridUrlListener() {
//            @Override
//            public String getGridUrl(int i, int i1, int i2) {
//                return "http://172.192.100.19:8888/tile?x="+i+"&y="+i1+"&z="+i2+"&p=1&mid=basemap_day&f=png&scale=1&cache=true";
//            }
//        });
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
