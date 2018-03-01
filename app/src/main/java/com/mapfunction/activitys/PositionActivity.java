//package com.mapfunction.activitys;
//
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentTransaction;
//
//import com.leador.api.maps.MapController;
//import com.leador.api.maps.MapOptions;
//import com.leador.api.maps.SupportMapFragment;
//import com.leador.api.maps.model.CameraPosition;
//import com.leador.api.maps.model.LatLng;
//import com.mapfunction.R;
//
//import static com.leador.api.maps.MapOptions.POSITION_COMPASS;
//import static com.leador.api.maps.MapOptions.POSITION_SCALE;
//
///**
// * 通过Java代码添加一个SupportMapFragment对象
// */
//public class PositionActivity extends FragmentActivity {
//    public static final LatLng WUHAN = new LatLng(30.593454, 114.295733);// 武汉市经纬度
//
//    private static final String MAP_FRAGMENT_TAG = "map";
//    static final CameraPosition wuhan = new CameraPosition.Builder()
//            .target(WUHAN).zoom(18).bearing(0).tilt(30).build();
//    private MapController lMap;
//    private SupportMapFragment lMapFragment;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        init();
//    }
//
//    /**
//     * 初始化lMap对象
//     */
//    private void init() {
//        MapOptions aOptions = new MapOptions();
//        aOptions.compassEnabled(true).scaleControlsEnabled(true);
//        aOptions.zoomGesturesEnabled(true);// 通过手势缩放地图
//        aOptions.scrollGesturesEnabled(true);// 通过手势移动地图
//        aOptions.tiltGesturesEnabled(true);// 通过手势倾斜地图
//        aOptions.rotateGesturesEnabled(true);
//        Bitmap[] bitmaps = new Bitmap[1];
//        bitmaps[0] = BitmapFactory.decodeResource(getResources(),R.drawable.compass3);
//        aOptions.customViewBitmap(POSITION_COMPASS,bitmaps);
//        //TODO
//        int[] ints = {200,200};
//        aOptions.customViewPositionInWindow(POSITION_COMPASS,ints);
//        //TODO
//        Bitmap[] bitmaps2 = new Bitmap[1];
//        bitmaps2[0] = BitmapFactory.decodeResource(getResources(), R.drawable.scale);
//        aOptions.customViewBitmap(POSITION_SCALE,bitmaps2);
//        int[] ints2 = {300,300};
//        aOptions.customViewPositionInWindow(POSITION_SCALE,ints2);
//
//        Bitmap[] bitmaps3 = new Bitmap[1];
//        bitmaps3[0] = BitmapFactory.decodeResource(getResources(), R.drawable.scale);
//        aOptions.customViewBitmap(POSITION_SCALE,bitmaps3);
//        int[] ints3 = {400, 400};
//        aOptions.customViewPositionInWindow(POSITION_SCALE,ints3);
//        aOptions.camera(wuhan);
//
//        if (lMapFragment == null) {
//            lMapFragment = SupportMapFragment.newInstance(aOptions);
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
//                    .beginTransaction();
//            fragmentTransaction.add(android.R.id.content, lMapFragment,
//                    MAP_FRAGMENT_TAG);
//            fragmentTransaction.commit();
//        }
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        initMap();
//    }
//
//    /**
//     * 初始化lMap对象
//     */
//    private void initMap() {
//        if (lMap == null) {
//            lMap = lMapFragment.getMap();
//        }
//    }
//}
