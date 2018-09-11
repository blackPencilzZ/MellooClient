package com.auvx.melloo.util;

import com.auvx.melloo.context.Melloo;
import com.auvx.melloo.domain.UserLocation;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;

public class LocationManager {

    private static final LocationClient mLocationClient = new LocationClient(Melloo.getContext());

    public static UserLocation requestSyncLocation() {
        final BDLocation location = mLocationClient.getLastKnownLocation();
        UserLocation userLocation = new UserLocation();
        userLocation.setLongitude(location.getLongitude());
        userLocation.setLatitude(location.getLatitude());
        location.getCountryCode();
        location.getCityCode();
        return userLocation;
    }
}
