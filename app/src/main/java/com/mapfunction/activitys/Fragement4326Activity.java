package com.mapfunction.activitys;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.leador.api.maps.MapController;
import com.leador.api.maps.MapsInitializer;
import com.leador.api.maps.SupportMapFragment;
import com.mapfunction.R;

/**
 * Created by Administrator on 2017/4/27 0027.
 */

public class Fragement4326Activity extends FragmentActivity {
    private MapController controller;
    private SupportMapFragment mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MapsInitializer.setCoodSystem(MapsInitializer.COOD_SYSTEM_4326);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fragment_basemap);
        mapView = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        setUpMapIfNeeded();
        controller.setGridUrlListener(new MapController.GridUrlListener() {
            @Override
            public String getGridUrl(int i, int i1, int i2) {
                return "http://172.192.100.19:8888/tile?x="+i+"&y="+i1+"&z="+i2+"&p=1&mid=basemap_day&f=png&scale=1&cache=true";
            }
        });
//        controller.setOnMapClickListener(new MapController.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
////                mStartMarker.setPosition(latLng);
//                controller.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.startpoint))).position(latLng));
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        setUpMapIfNeeded();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    private void setUpMapIfNeeded() {
        if (controller == null) {
            controller = mapView.getMap();
        }
    }

}
