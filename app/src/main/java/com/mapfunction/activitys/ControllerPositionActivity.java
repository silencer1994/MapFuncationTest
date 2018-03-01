package com.mapfunction.activitys;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.leador.api.maps.MapController;
import com.leador.api.maps.MapOptions;
import com.leador.api.maps.MapView;
import com.leador.api.maps.model.BitmapDescriptorFactory;
import com.leador.api.maps.model.LatLng;
import com.leador.api.maps.model.MarkerOptions;
import com.leador.api.maps.model.Poi;
import com.mapfunction.R;


/**
 * Created by Administrator on 2017/3/29/029.
 */
public class ControllerPositionActivity extends Activity {
    private MapView mapView;
    private MapController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mapview);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        controller = mapView.getMap();
//        controller.setGridUrlListener(new MapController.a() {
//            @Override
//            public String a(int i, int i1, int i2) {
//                return "http://172.192.100.19:8888/tile?x=" + i + "&y=" + i1 + "&z=" + i2 + "&p=0&mid=basemap_day&f=png&scale=1&cache=true";
//            }
//        });
        controller.getUiSettings().setCompassEnabled(true);
        controller.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        controller.setMyLocationType(MapController.LOCATION_TYPE_LOCATE);
        controller.getUiSettings().setScaleControlsEnabled(true);
        controller.setOnPOIClickListener(new MapController.OnPOIClickListener() {
            @Override
            public void onPOIClick(Poi poi) {
                Toast.makeText(getApplicationContext(), "您点击了" + poi.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        controller.setOnMapClickListener(new MapController.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
//                Toast.makeText(getApplicationContext(), "您点击了地图", Toast.LENGTH_SHORT).show();
                controller.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.startpoint))).position(latLng).setAddByAnimation(true));

            }
        });

        //设置指南针图片和位置
        controller.getUiSettings().setMyLocationButtonEnabled(true);
        Bitmap[] bitmaps = new Bitmap[1];
        bitmaps[0] = BitmapFactory.decodeResource(getResources(), R.drawable.compass3);
        controller.setViewBitmap(MapOptions.POSITION_COMPASS, bitmaps);

        Bitmap[] bitmapslocation = new Bitmap[2];
        bitmapslocation[1] = BitmapFactory.decodeResource(getResources(), R.drawable.compass3);
        bitmapslocation[0] = BitmapFactory.decodeResource(getResources(), R.drawable.scale);
        controller.setViewBitmap(2, bitmapslocation);
//
//        //设置比例尺图片和位置
//        Bitmap[] bitmaps2 = new Bitmap[1];
//        bitmaps2[0] = BitmapFactory.decodeResource(getResources(), R.drawable.scale);
////        controller.setViewBitmap(MapOptions.POSITION_SCALE,bitmaps2);

//        //设置logo图片和位置
//        Bitmap[] bitmaps3 = new Bitmap[1];
//        bitmaps3[0] = BitmapFactory.decodeResource(getResources(), R.drawable.compass3);
//        controller.setViewBitmap(MapOptions.POSITION_LOGO, bitmaps3);
        controller.setViewPosition(MapOptions.POSITION_SCALE, 300, 300);
        controller.setViewPosition(MapOptions.POSITION_COMPASS, 500, 300);
        controller.setViewPosition(MapOptions.POSITION_LOGO, 500, 500);


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
