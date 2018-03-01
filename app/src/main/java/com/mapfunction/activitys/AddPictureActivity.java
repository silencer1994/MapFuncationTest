package com.mapfunction.activitys;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.leador.api.maps.CameraUpdateFactory;
import com.leador.api.maps.MapController;
import com.leador.api.maps.MapView;
import com.leador.api.maps.MapsInitializer;
import com.leador.api.maps.model.BitmapDescriptorFactory;
import com.leador.api.maps.model.LatLng;
import com.leador.api.maps.model.Poi;
import com.leador.api.maps.model.PolygonOptions;
import com.leador.api.maps.model.Polyline;
import com.leador.api.maps.model.PolylineOptions;
import com.mapfunction.R;
import com.mapfunction.copycode.Constants;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/4/27 0027.
 */

public class AddPictureActivity extends Activity {
    private MapView mapView;
    private MapController controller;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        MapsInitializer.setCoodSystem(MapsInitializer.COOD_SYSTEM_4326);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapview);
        mapView = (MapView)findViewById(R.id.mapView);
//        mapView.setCoodSystem(MapOptions.COOD_SYSTEM_4326);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        controller = mapView.getMap();
        controller.setGridUrlListener(new MapController.GridUrlListener() {
            @Override
            public String getGridUrl(int i, int i1, int i2) {
                return "http://172.192.100.19:8888/tile?x="+i+"&y="+i1+"&z="+i2+"&p=1&mid=basemap_day&f=png&scale=1&cache=true";
            }
        });
        //添加一条线段
        addPolyLine();
        //添加一个矩形
        addRego();
    }

    private void addRego() {
        controller.addPolygon(new PolygonOptions()
                .addAll(createRectangle(Constants.BEIJING, 1, 1))
                .fillColor(Color.LTGRAY).strokeColor(Color.RED).strokeWidth(1));
    }
    /**
     * 生成一个长方形的四个坐标点
     */
    private List<LatLng> createRectangle(LatLng center, double halfWidth,
                                         double halfHeight) {
        return Arrays.asList(new LatLng(center.latitude - halfHeight,
                        center.longitude - halfWidth), new LatLng(center.latitude
                        - halfHeight, center.longitude + halfWidth), new LatLng(
                        center.latitude + halfHeight, center.longitude + halfWidth),
                new LatLng(center.latitude + halfHeight, center.longitude
                        - halfWidth));
    }

    /**
     * 添加测试线条,并添加测试监听
     */
    private void addPolyLine() {
//        controller.setOnPolylineClickListener(new MapController.OnPolylineClickListener() {
//            @Override
//            public void onPolylineClick(Polyline polyline) {
//                Toast.makeText(getApplicationContext(),"您点击了此线条",Toast.LENGTH_SHORT).show();
//            }
//        });
        Polyline polyline_add = controller.addPolyline((new PolylineOptions())
                .add(new LatLng(29.719141, 106.403469), new LatLng(30.550709, 98.16148))
                .width(64)
                .setCustomTexture(BitmapDescriptorFactory.fromAsset("leador_route.png")));
        controller.setOnPolylineClickListener(new MapController.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {
                Toast.makeText(getApplicationContext(),"您点击了此线条",Toast.LENGTH_SHORT).show();
            }
        });        controller.setOnMapClickListener(new MapController.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Toast.makeText(getApplicationContext(),"您点击了地图",Toast.LENGTH_SHORT).show();
            }
        });
        controller.setOnPOIClickListener(new MapController.OnPOIClickListener() {
            @Override
            public void onPOIClick(Poi poi) {
                Toast.makeText(getApplicationContext(),"您点击了"+poi.getName(),Toast.LENGTH_SHORT).show();
            }
        });
        controller.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.300299, 106.347656), 4));

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
