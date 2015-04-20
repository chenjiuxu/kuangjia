package com.example.administrator.chen.Fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.administrator.chen.R;
import com.baidu.mapapi.model.LatLngBounds.Builder;

import java.util.ArrayList;
import java.util.List;

public class map extends Fragment {


    private LocationClient mLocationClient;
    private MapView mapview;
    private BaiduMap baiduMap;
    private FragmentActivity activity;
    private ArrayList<Marker> list;//记录对象
    private List<PoiInfo> listpoi;//
    private double Latitude=0;//定位纬度
    private double Longitude=0;//定位精度

    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity = getActivity();//获得当前加载的activity
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        initializeView(view);
        initializeLocation();

        baidumapListener();
        return view;
    }

    /**
     * 初始化页面控件并设置页面点击事件
     */
    private void initializeView(View view) {
        mapview = (MapView) view.findViewById(R.id.bmapView);//获得地图控件
        mapview.showZoomControls(false);
        View bt1 = view.findViewById(R.id.fragment_map_bt1);
        View bt2= view.findViewById(R.id.fragment_map_bt2);
        View bt3 = view.findViewById(R.id.fragment_map_bt3);
        View bt4 = view.findViewById(R.id.fragment_map_bt4);
        baiduMap = mapview.getMap();//获得百度地图

        setonclick(bt1, bt2, bt3,bt4, mapview);

    }


    /**
     * 初始化定位设置
     */
    private void initializeLocation() {
        mLocationClient = new LocationClient(getActivity().getApplication());//初始化定位
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);

        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);//设置定位图标
        baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker));


        location();
    }

    /**
     * 设置百度地图搜索点击事件
     */
    private void baidumapListener() {
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            Button button = new Button(activity);
            @Override
            public boolean onMarkerClick(Marker marker) {
                button.setBackgroundResource(R.drawable.popup);
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) == marker) {
                        PoiInfo poiInfo = listpoi.get(i);
                        button.setText(poiInfo.name);
                        LatLng ll = marker.getPosition();
                        LatLng llNew = new LatLng(ll.latitude , ll.longitude );
                        marker.setPosition(llNew);
                        baiduMap.hideInfoWindow();
                        break;
                    }
                }
                LatLng ll = marker.getPosition();
                InfoWindow mInfoWindow = new InfoWindow(button, ll, -47);
                baiduMap.showInfoWindow(mInfoWindow);
                return true;
            }
        });

    }

    /**
     * 搜索周边
     */
    private void search(double latitude, double longitude,String s) {
        PoiSearch mPoiSearch = PoiSearch.newInstance();
        OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {//发起搜索获得的结果对象
            public void onGetPoiResult(PoiResult result) {
                listpoi=null;
                listpoi = result.getAllPoi();
                if (result != null && listpoi.size() != 0) {
                    if(list!=null&&list.size()>0){
                        list=null;
                    }
                    list = new ArrayList<Marker>();
                    baiduMap.clear();
                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
                    LatLngBounds.Builder builder = new Builder();
                    for (int i = 0; i < result.getAllPoi().size(); i++) {
                        PoiInfo poiInfo = listpoi.get(i);
                        LatLng location = poiInfo.location;
                        if (location != null) {
                            OverlayOptions oo = new MarkerOptions().icon(icon).position(location);
                            Marker marker = (Marker) (baiduMap.addOverlay(oo));
                            list.add(marker);
                            builder.include(location);
                        }

                    }
                }
            }

            public void onGetPoiDetailResult(PoiDetailResult result) {

            }
        };


        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);//开始收索
        mPoiSearch.searchNearby(new PoiNearbySearchOption()
                .keyword(s)
                .location(new LatLng(latitude, longitude))//收索原点
                .radius(3000)//搜索半径
        );


    }


    /**
     * 开始定位
     */
    private void location() {
        mLocationClient.registerLocationListener(new myListener());
        mLocationClient.requestLocation();
        mLocationClient.start();
    }

    /**
     * 设置地图样式
     */
    private void setonclick(View bt1, View bt2, View bt3,View bt4,MapView mapview) {

        bt1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                baiduMap.clear();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Toast.makeText(activity,Longitude+"/"+Latitude,Toast.LENGTH_SHORT).show();
                if(Latitude==0&&Longitude==0){
                }else {
                    search(Latitude,Longitude,"医院");
                }
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity,Longitude+"/"+Latitude,Toast.LENGTH_SHORT).show();
                if(Latitude==0&&Longitude==0){
                }else {
                    search(Latitude,Longitude,"游乐场");
                }
            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity,Longitude+"/"+Latitude,Toast.LENGTH_SHORT).show();
                if(Latitude==0&&Longitude==0){
                }else {
                    search(Latitude, Longitude, "酒店");
                }
            }
        });


    }

    /**
     * 定位返回对象
     */
    private class myListener implements BDLocationListener {


        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            // map view 销毁后不在处理新接收的位置
            if (bdLocation == null || baiduMap == null) return;

            Latitude=bdLocation.getLatitude();
            Longitude=bdLocation.getLongitude();
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())//定位精度
                    .direction(100)
                    .latitude(Latitude)//纬度
                    .longitude(Longitude).build();//精度
            baiduMap.setMyLocationData(locData);

                LatLng ll = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                baiduMap.animateMapStatus(u);


        }
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        baiduMap.setMyLocationEnabled(true);
    }

    @Override
    public void onPause() {
        mapview.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        mapview.onResume();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        // 退出时销毁定位
        mLocationClient.stop();
        // 关闭定位图层
        baiduMap.setMyLocationEnabled(false);
        mapview.onDestroy();
        mapview = null;
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        super.onStop();
        baiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
