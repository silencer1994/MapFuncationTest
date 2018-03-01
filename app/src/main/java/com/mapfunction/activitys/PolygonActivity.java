package com.mapfunction.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.widget.Toast;

import com.leador.api.maps.CameraUpdateFactory;
import com.leador.api.maps.MapController;
import com.leador.api.maps.MapView;
import com.leador.api.maps.MapsInitializer;
import com.leador.api.maps.model.BitmapDescriptorFactory;
import com.leador.api.maps.model.LatLng;
import com.leador.api.maps.model.Poi;
import com.leador.api.maps.model.Polyline;
import com.leador.api.maps.model.PolylineOptions;
import com.mapfunction.R;

/**
 * Created by Administrator on 2017/4/26 0026.
 */

public class PolygonActivity extends Activity {
    private MapView mapView;
    private MapController controller;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        MapsInitializer.setCoodSystem(MapsInitializer.COOD_SYSTEM_900913);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mapview);
        mapView = (MapView)findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        controller = mapView.getMap();
        addPolyLine();
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
        controller.getUiSettings().setMyLocationButtonEnabled(true);

        controller.setOnPolylineClickListener(new MapController.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {
                Toast.makeText(getApplicationContext(),"您点击了此线条",Toast.LENGTH_SHORT).show();
            }
        });
        controller.setOnMapClickListener(new MapController.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Toast.makeText(getApplicationContext(),"您点击了地图",Toast.LENGTH_SHORT).show();
            }
        });
        controller.setOnPOIClickListener(new MapController.OnPOIClickListener() {
            @Override
            public void onPOIClick(Poi poi) {
                Toast.makeText(getApplicationContext(),"您点击了"+poi.getName(),Toast.LENGTH_SHORT).show();
//                int[] ints = new int[2];
//                ints[5] = 10;
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
