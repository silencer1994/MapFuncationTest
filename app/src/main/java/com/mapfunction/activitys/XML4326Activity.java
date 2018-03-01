package com.mapfunction.activitys;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Window;

import com.leador.api.maps.CameraUpdateFactory;
import com.leador.api.maps.MapController;
import com.leador.api.maps.MapView;
import com.leador.api.maps.MapsInitializer;
import com.leador.api.maps.model.BitmapDescriptorFactory;
import com.leador.api.maps.model.CameraPosition;
import com.leador.api.maps.model.LatLng;
import com.leador.api.maps.model.MarkerOptions;
import com.mapfunction.R;

/**
 * Created by Administrator on 2017/4/27 0027.
 */

public class XML4326Activity extends Activity {
    private MapView mapView;
    private MapController controller;
//    private Marker mStartMarker;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        MapsInitializer.setCoodSystem(MapsInitializer.COOD_SYSTEM_4326);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mapview);
        mapView = (MapView)findViewById(R.id.mapView);
//        mapView.setCoodSystem(MapOptions.COOD_SYSTEM_4326);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        controller = mapView.getMap();
        controller.setGridUrlListener(new MapController.GridUrlListener() {
            @Override
            public String getGridUrl(int i, int i1, int i2) {
//                Log.e("ASDF", "getGridUrl: "+i+"  "+i1+"  "+i2);
                return "http://172.192.100.19:8888/tile?x="+i+"&y="+i1+"&z="+i2+"&p=1&mid=basemap_day&f=png&scale=1&cache=true";
            }
        });
        controller.setOnCameraChangeListener(new MapController.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                Log.e("ASDF", "onCameraChange: "+ cameraPosition.zoom);
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                Log.e("ASDF", "onCameraChange: "+ cameraPosition.zoom);
            }
        });
        controller.setOnMapClickListener(new MapController.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
//                mStartMarker.setPosition(latLng);
                controller.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.startpoint))).position(latLng).setAddByAnimation(true));
//                controller.addPolyline((new PolylineOptions())
//                        .add(new LatLng(36.285643, 83.222875), new LatLng(43.902099, 125.242301))
//                        .geodesic(true).setDottedLine(true).color(Color.RED));
            }
        });
        controller.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.300299, 106.347656), 4));
        controller.setMapTextZIndex(2);

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
