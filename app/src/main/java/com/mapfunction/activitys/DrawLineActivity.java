package com.mapfunction.activitys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.leador.api.maps.MapController;
import com.leador.api.maps.MapView;
import com.leador.api.maps.overlay.BusRouteOverlay;
import com.leador.api.maps.overlay.DrivingRouteOverlay;
import com.leador.api.maps.overlay.WalkRouteOverlay;
import com.leador.api.services.core.LatLonPoint;
import com.leador.api.services.route.BusPath;
import com.leador.api.services.route.BusRouteResult;
import com.leador.api.services.route.DrivePath;
import com.leador.api.services.route.DriveRouteResult;
import com.leador.api.services.route.RouteSearch;
import com.leador.api.services.route.WalkPath;
import com.leador.api.services.route.WalkRouteResult;
import com.mapfunction.R;
import com.mapfunction.copycode.ToastUtil;

/**
 * Created by Administrator on 2017/4/27 0027.
 */

public class DrawLineActivity extends Activity implements RouteSearch.OnRouteSearchListener {
    private MapView mapView;
    private MapController controller;

    //copycode
    private ProgressDialog progDialog = null;// 搜索时进度条
    private RouteSearch routeSearch;
    private BusRouteResult busRouteResult;// 公交模式查询结果
    private DriveRouteResult driveRouteResult;// 驾车模式查询结果
    private WalkRouteResult walkRouteResult;// 步行模式查询结果

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawline);
        mapView = (MapView)findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        controller = mapView.getMap();
        routeSearch = new RouteSearch(DrawLineActivity.this);
        routeSearch.setRouteSearchListener(this);
    }

    /**
     * 两个按钮的点击事件监听
     * @param view
     */
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_start_drawline:

                break;
            case R.id.btn_stop_drawline:
                break;
            default:
                break;
        }
    }

    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(LatLonPoint startPoint, LatLonPoint endPoint) {
        showProgressDialog();
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                startPoint, endPoint);
            RouteSearch.BusRouteQuery query = new RouteSearch.BusRouteQuery(fromAndTo);//参数表示路径规划的起点和终点
            routeSearch.calculateBusRouteAsyn(query);// 异步路径规划公交模式查询
    }
    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(true);
        progDialog.setMessage("正在搜索");
        progDialog.show();
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

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }
    @Override
    public void onBusRouteSearched(BusRouteResult result, int rCode) {
        dissmissProgressDialog();
        if (rCode == 0) {
            if (result != null && result.getPaths() != null
                    && result.getPaths().size() > 0) {
                busRouteResult = result;
                BusPath busPath = busRouteResult.getPaths().get(0);
                controller.clear();// 清理地图上的所有覆盖物
                LatLonPoint s = busRouteResult.getStartPosInfo().getLocation();
                LatLonPoint end= busRouteResult.getTargetPosInfo().getLocation();
                BusRouteOverlay routeOverlay = new BusRouteOverlay(this, controller,
                        busPath, busRouteResult.getStartPos(),
                        busRouteResult.getTargetPos());
                routeOverlay.removeFromMap();
                routeOverlay.addToMap();
                routeOverlay.zoomToSpan();
            } else {
                ToastUtil.show(DrawLineActivity.this, R.string.error_no_result);
            }
        } else if (rCode == 27) {
            ToastUtil.show(DrawLineActivity.this, R.string.error_network);
        } else if (rCode == 102) {
            ToastUtil.show(DrawLineActivity.this, R.string.error_key);
        } else if(rCode == 105){
            ToastUtil.show(DrawLineActivity.this, " 查询结果为空。");
        } else {
            ToastUtil.show(DrawLineActivity.this, R.string.error_other);
        }
    }

    /**
     * 驾车结果回调
     */
    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int rCode) {
        dissmissProgressDialog();
        if (rCode == 0) {
            if (result != null && result.getPaths() != null
                    && result.getPaths().size() > 0) {
                driveRouteResult = result;
                DrivePath drivePath = driveRouteResult.getPaths().get(0);
                controller.clear();// 清理地图上的所有覆盖物
                DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
                        this, controller, drivePath, driveRouteResult.getStartPos(),
                        driveRouteResult.getTargetPos());
                drivingRouteOverlay.removeFromMap();
                drivingRouteOverlay.addToMap();
                drivingRouteOverlay.zoomToSpan();
            } else {
                ToastUtil.show(DrawLineActivity.this, R.string.error_no_result);
            }
        } else if (rCode == 27) {
            ToastUtil.show(DrawLineActivity.this, R.string.error_network);
        } else if (rCode == 102) {
            ToastUtil.show(DrawLineActivity.this, R.string.error_key);
        } else if(rCode == 105){
            ToastUtil.show(DrawLineActivity.this, " 查询结果为空。");
        } else {
            ToastUtil.show(DrawLineActivity.this, R.string.error_other);
        }
    }

    /**
     * 步行路线结果回调
     */
    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int rCode) {
        dissmissProgressDialog();
        if (rCode == 0) {
            if (result != null && result.getPaths() != null
                    && result.getPaths().size() > 0) {
                walkRouteResult = result;
                WalkPath walkPath = walkRouteResult.getPaths().get(0);

                controller.clear();// 清理地图上的所有覆盖物
                WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(this,
                        controller, walkPath, walkRouteResult.getStartPos(), walkRouteResult.getTargetPos());
                walkRouteOverlay.removeFromMap();
                walkRouteOverlay.addToMap();
                walkRouteOverlay.zoomToSpan();
            }
        }else if (rCode == 27) {
            ToastUtil.show(DrawLineActivity.this, R.string.error_network);
        } else if (rCode == 102) {
            ToastUtil.show(DrawLineActivity.this, R.string.error_key);
        } else if(rCode == 105){
            ToastUtil.show(DrawLineActivity.this, " 查询结果为空。");
        } else if(rCode == 10000) {
            ToastUtil.show(DrawLineActivity.this, " 引擎异常。");
        } else {
            ToastUtil.show(DrawLineActivity.this, R.string.error_other);
        }
    }

}
