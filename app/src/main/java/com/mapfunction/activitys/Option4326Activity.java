package com.mapfunction.activitys;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.leador.api.maps.MapController;
import com.leador.api.maps.MapOptions;
import com.leador.api.maps.MapsInitializer;
import com.leador.api.maps.SupportMapFragment;
import com.leador.api.maps.model.BitmapDescriptorFactory;
import com.leador.api.maps.model.CameraPosition;
import com.leador.api.maps.model.LatLng;
import com.leador.api.maps.model.MarkerOptions;
import com.mapfunction.R;
import com.mapfunction.copycode.Constants;


/**
 * Created by Administrator on 2017/4/27 0027.
 */

public class Option4326Activity extends FragmentActivity {

    private static final String MAP_FRAGMENT_TAG = "map";
    static final CameraPosition beijing = new CameraPosition.Builder()
            .target(Constants.BEIJING).zoom(14).bearing(0).build();//.tilt(30)
    private MapController controller;
    private SupportMapFragment controllerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        MapsInitializer.setCoodSystem(MapsInitializer.COOD_SYSTEM_4326);
        MapsInitializer.setCoodSystem(MapsInitializer.COOD_SYSTEM_900913);
        super.onCreate(savedInstanceState);
        try {
            MapsInitializer.initialize(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        init();
        controller.setGridUrlListener(new MapController.GridUrlListener() {
            @Override
            public String getGridUrl(int i, int i1, int i2) {
//                return "http://172.192.100.19:8888/tile?x="+i+"&y="+i1+"&z="+i2+"&p=1&mid=basemap_day&f=png&scale=1&cache=true";
                return "http://172.192.100.14:25003/v3/tile?&x="+i+"&y="+i1+"&z="+i2+"&s=tmc&mid=basemap_day";
            }
        });
        controller.setOnMapClickListener(new MapController.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                controller.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.startpoint))).position(latLng));
            }
        });
    }

    /**
     * 初始化controller对象
     */
    private void init() {
        MapOptions aOptions = new MapOptions();
//        aOptions.zoomGesturesEnabled(false);// 禁止通过手势缩放地图
//        aOptions.scrollGesturesEnabled(false);// 禁止通过手势移动地图
//        aOptions.tiltGesturesEnabled(false);// 禁止通过手势倾斜地图
        aOptions.camera(beijing);
        if (controllerFragment == null) {
            controllerFragment = SupportMapFragment.newInstance(aOptions);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            fragmentTransaction.add(android.R.id.content, controllerFragment,
                    MAP_FRAGMENT_TAG);
            fragmentTransaction.commit();
        }
        if (controller == null) {
            controller = controllerFragment.getMap();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        initMap();
    }

    /**
     * 初始化controller对象
     */
    private void initMap() {
//        if (controller == null) {
//            controller = controllerFragment.getMap();
//        }
    }

}
