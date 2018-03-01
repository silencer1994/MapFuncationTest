package com.mapfunction;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.leador.api.maps.MapsInitializer;
import com.mapfunction.activitys.AddPictureActivity;
import com.mapfunction.activitys.ControllerPositionActivity;
import com.mapfunction.activitys.DrawLineActivity;
import com.mapfunction.activitys.Fragement4326Activity;
import com.mapfunction.activitys.MormalHeatMapActivity;
import com.mapfunction.activitys.Option4326Activity;
import com.mapfunction.activitys.PolygonActivity;
import com.mapfunction.activitys.Texture4326Activity;
import com.mapfunction.activitys.XML4326Activity;
import com.mapfunction.route.RouteActivity;
import com.mapfunction.view.FeatureView;

public class MainActivity extends ListActivity {
    private static class DemoDetails {
        private final String titleId;
        private final String descriptionId;
        private final Class<? extends android.app.Activity> activityClass;

        public DemoDetails(String titleId, String descriptionId,
                           Class<? extends android.app.Activity> activityClass) {
            super();
            this.titleId = titleId;
            this.descriptionId = descriptionId;
            this.activityClass = activityClass;
        }
    }

    private static class CustomArrayAdapter extends ArrayAdapter<DemoDetails> {
        public CustomArrayAdapter(Context context, DemoDetails[] demos) {
            super(context, R.layout.feature, R.id.title, demos);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FeatureView featureView;
            if (convertView instanceof FeatureView) {
                featureView = (FeatureView) convertView;
            } else {
                featureView = new FeatureView(getContext());
            }
            DemoDetails demo = getItem(position);
            featureView.setTitleId(demo.titleId);
            featureView.setDescriptionId(demo.descriptionId);
            return featureView;
        }
    }

    private static final DemoDetails[] demos = {
            new DemoDetails("普通的热力图", "默认参数", MormalHeatMapActivity.class),
//            new DemoDetails("少点大范围热力图", "完整参数", LittleHeatMapActivity.class),
//            new DemoDetails("多点小范围热力图权重点", "完整参数", LotHeatMapActivity.class),
//            new DemoDetails("MapOptions设置比例尺、指北针、logo在屏幕上的位置坐标和图片接口", "MapOptions", PositionActivity.class),
            new DemoDetails("Controller设置比例尺,指北针,logo在屏幕上的位置坐标和图片接口", "Controller设置比例尺,指北针,logo", ControllerPositionActivity.class),
            new DemoDetails("PolyLine点击无响应事件", "点击Polyline后应该有Toast提示", PolygonActivity.class),
            new DemoDetails("4326坐标系XML", "xml启动", XML4326Activity.class),
            new DemoDetails("4326坐标系Fragment", "MapOptions启动", Fragement4326Activity.class),
            new DemoDetails("4326坐标系无XML", "无XML启动", Option4326Activity.class),
            new DemoDetails("线路绘制效率", "大幅度提升绘制效率", DrawLineActivity.class),
            new DemoDetails("切片地图添加各种图形类东西","polyLine,poliGone等等",AddPictureActivity.class),
            new DemoDetails("4326坐标系公交路线绘制","4326坐标系公交路线绘制", RouteActivity.class),
            new DemoDetails("Texture测试","Texture测试", Texture4326Activity.class)
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("地图Demo" + MapsInitializer.getVersion());
        ListAdapter adapter = new CustomArrayAdapter(
                this.getApplicationContext(), demos);
        setListAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        DemoDetails demo = (DemoDetails) getListAdapter().getItem(position);
        startActivity(new Intent(this.getApplicationContext(),
                demo.activityClass));
    }
}
