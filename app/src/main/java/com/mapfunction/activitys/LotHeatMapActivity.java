package com.mapfunction.activitys;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.leador.api.maps.CameraUpdateFactory;
import com.leador.api.maps.LocationSource;
import com.leador.api.maps.MapController;
import com.leador.api.maps.MapOptions;
import com.leador.api.maps.MapView;
import com.leador.api.maps.UiSettings;
import com.leador.api.maps.model.Gradient;
import com.leador.api.maps.model.HeatmapTileProvider;
import com.leador.api.maps.model.LatLng;
import com.leador.api.maps.model.TileOverlayOptions;
import com.leador.api.maps.model.WeightedLatLng;
import com.leador.map.api.location.LocationManagerProxy;
import com.leador.map.api.location.MapLocation;
import com.leador.map.api.location.MapLocationListener;
import com.mapfunction.R;

import java.util.Arrays;

/**
 * Created by Administrator on 2017/3/28/028.
 */
public class LotHeatMapActivity extends Activity implements
        RadioGroup.OnCheckedChangeListener, View.OnClickListener, LocationSource, MapLocationListener {
    private MapController map;
    private MapView mapView;
    private UiSettings mUiSettings;
    private RadioGroup zoomRadioGroup;
    private boolean isFirstFocus = false;
    private LocationSource.OnLocationChangedListener mListener;

    private LocationManagerProxy locationManager = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_settings);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        init();
        initDataAndHeatMap();
    }
    private  final int[] ALT_HEATMAP_GRADIENT_COLORS = {
//            Color.argb(0, 0, 255, 255),
//            Color.argb(255 / 3 * 2, 0, 255, 0),
//            Color.rgb(125, 191, 0),
//            Color.rgb(185, 71, 0),
//            Color.rgb(255, 0, 0)
            Color.GREEN,
            Color.BLACK,
    };

    public  final float[] ALT_HEATMAP_GRADIENT_START_POINTS = { 0.0f,1.0f};
//            ,1.0f};
//            0.10f, 0.20f, 0.60f, 1.0f };

    public  final Gradient ALT_HEATMAP_GRADIENT = new Gradient(
            ALT_HEATMAP_GRADIENT_COLORS, ALT_HEATMAP_GRADIENT_START_POINTS);

    private void initDataAndHeatMap() {
        // 第一步： 生成热力点坐标列表
        WeightedLatLng[] latlngs = new WeightedLatLng[6000];
        double x = 39.904980;
        double y = 116.40955;

        for (int i = 0; i < 6000; i++) {
            double x_ = 0;
            double y_ = 0;
            x_ = Math.random() * 0.2 - 0.125;
            y_ = Math.random() * 0.2 - 0.125;
            double weight = Math.random() * 10;
            latlngs[i] = new WeightedLatLng(new LatLng(x + x_, y + y_),weight);
        }

        // 第二步： 构建热力图 TileProvider
        HeatmapTileProvider.Builder builder = new HeatmapTileProvider.Builder();
        builder.weightedData(Arrays.asList(latlngs)).transparency(1) // 设置热力图绘制的数据
                .gradient(ALT_HEATMAP_GRADIENT); // 设置热力图渐变，有默认值 DEFAULT_GRADIENT，可不设置该接口
        // Gradient 的设置可见参考手册
        // 构造热力图对象
        HeatmapTileProvider heatmapTileProvider = builder.build();

        // 第三步： 构建热力图参数对象
        TileOverlayOptions tileOverlayOptions = new TileOverlayOptions();
        tileOverlayOptions.tileProvider(heatmapTileProvider); // 设置瓦片图层的提供者

        // 第四步： 添加热力图
        map.addTileOverlay(tileOverlayOptions);
    }

    private void init() {
        if (map == null) {
            map = mapView.getMap();
            mUiSettings = map.getUiSettings();
        }
        Button buttonScale = (Button) findViewById(R.id.buttonScale);
        buttonScale.setOnClickListener(this);
        CheckBox scaleToggle = (CheckBox) findViewById(R.id.scale_toggle);
        scaleToggle.setOnClickListener(this);
        CheckBox zoomToggle = (CheckBox) findViewById(R.id.zoom_toggle);
        zoomToggle.setOnClickListener(this);
        zoomRadioGroup = (RadioGroup) findViewById(R.id.zoom_position);
        zoomRadioGroup.setOnCheckedChangeListener(this);
        CheckBox compassToggle = (CheckBox) findViewById(R.id.compass_toggle);
        compassToggle.setOnClickListener(this);
        CheckBox mylocationToggle = (CheckBox) findViewById(R.id.mylocation_toggle);
        mylocationToggle.setOnClickListener(this);
        CheckBox scrollToggle = (CheckBox) findViewById(R.id.scroll_toggle);
        scrollToggle.setOnClickListener(this);
        CheckBox zoom_gesturesToggle = (CheckBox) findViewById(R.id.zoom_gestures_toggle);
        zoom_gesturesToggle.setOnClickListener(this);
        CheckBox tiltToggle = (CheckBox) findViewById(R.id.tilt_toggle);
        tiltToggle.setOnClickListener(this);
        CheckBox rotateToggle = (CheckBox) findViewById(R.id.rotate_toggle);
        rotateToggle.setOnClickListener(this);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.logo_position);
        radioGroup.setOnCheckedChangeListener(this);
        compassToggle.setChecked(mUiSettings.isCompassEnabled());
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
        deactivate();
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
        if (locationManager != null) {
            locationManager.removeUpdates(this);
            locationManager.destroy();
        }
        locationManager = null;
        mapView.onDestroy();
    }

    /**
     * 设置logo位置，左下，底部居中，右下
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (map != null) {
            if (checkedId == R.id.bottom_left) {
                mUiSettings
                        .setLogoPosition(MapOptions.LOGO_POSITION_BOTTOM_LEFT);// 设置地图logo显示在左下方
            } else if (checkedId == R.id.bottom_center) {
                mUiSettings
                        .setLogoPosition(MapOptions.LOGO_POSITION_BOTTOM_CENTER);// 设置地图logo显示在底部居中
            } else if (checkedId == R.id.bottom_right) {
                mUiSettings
                        .setLogoPosition(MapOptions.LOGO_POSITION_BOTTOM_RIGHT);// 设置地图logo显示在右下方
            } else if (checkedId == R.id.zoom_bottom_right) {
                mUiSettings
                        .setZoomPosition(MapOptions.ZOOM_POSITION_RIGHT_BUTTOM);//设置缩放按钮显示在右下方
            } else if (checkedId == R.id.zoom_center_right) {
                mUiSettings
                        .setZoomPosition(MapOptions.ZOOM_POSITION_RIGHT_CENTER);//设置缩放按钮显示在右侧居中
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /**
             * 一像素代表多少米
             */
            case R.id.buttonScale:
                float scale = map.getScalePerPixel();
//                ToastUtil.show(UiSettingsActivity.this, "每像素代表" + scale + "米");
                Toast.makeText(this,"每像素代表" + scale + "米",Toast.LENGTH_SHORT).show();
                break;
            /**
             * 设置地图比例尺显示
             */
            case R.id.scale_toggle:
                mUiSettings.setScaleControlsEnabled(((CheckBox) view).isChecked());

                break;
            /**
             * 设置地图缩放按钮显示
             */
            case R.id.zoom_toggle:
                mUiSettings.setZoomControlsEnabled(((CheckBox) view).isChecked());
                zoomRadioGroup.setVisibility(((CheckBox) view).isChecked()?View.VISIBLE:View.GONE);
                break;
            /**
             * 设置地图指南针显示
             */
            case R.id.compass_toggle:
                mUiSettings.setCompassEnabled(((CheckBox) view).isChecked());
                break;
            /**
             * 设置地图定位按钮显示
             */
            case R.id.mylocation_toggle:
                map.setLocationSource(this);// 设置定位监听
                mUiSettings.setMyLocationButtonEnabled(((CheckBox) view)
                        .isChecked()); // 是否显示默认的定位按钮
                map.setMyLocationEnabled(((CheckBox) view).isChecked());// 是否可触发定位并显示定位层
                break;
            /**
             * 设置地图手势滑动
             */
            case R.id.scroll_toggle:
                mUiSettings.setScrollGesturesEnabled(((CheckBox) view).isChecked());
                break;
            /**
             * 设置地图手势缩放
             */
            case R.id.zoom_gestures_toggle:
                mUiSettings.setZoomGesturesEnabled(((CheckBox) view).isChecked());
                break;
            /**
             * 设置地图可以倾斜
             */
            case R.id.tilt_toggle:
                mUiSettings.setTiltGesturesEnabled(((CheckBox) view).isChecked());
                break;
            /**
             * 设置地图可以旋转
             */
            case R.id.rotate_toggle:
                mUiSettings.setRotateGesturesEnabled(((CheckBox) view).isChecked());
                break;
            default:
                break;
        }
    }



    public boolean enableMyLocation(String bestProvider) {
        boolean result = true;
        locationManager.requestLocationUpdates(bestProvider, 2000, 10, this);
        return result;
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(LocationSource.OnLocationChangedListener listener) {
        mListener = listener;
        if(locationManager==null){
            locationManager = LocationManagerProxy.getInstance(this);
            enableMyLocation(LocationManagerProxy.LocNetwork);
        }

    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (locationManager != null) {
            locationManager.removeUpdates(this);
            locationManager.destroy();
        }
        locationManager = null;
    }
    @Override
    public void onLocationChanged(MapLocation location) {
        if(location!=null&&location.getErrorInfo()!=null){
            return ;
        }
        if (mListener != null &&location!=null){
            mListener.onLocationChanged(location);
            float r=location.getAccuracy();
            if(!isFirstFocus){
                map.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
                if(r>200){
                    map.moveCamera(CameraUpdateFactory.zoomTo(17));
                }else{
                    map.moveCamera(CameraUpdateFactory.zoomTo(18));
                }
                isFirstFocus = true;
            }

        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
