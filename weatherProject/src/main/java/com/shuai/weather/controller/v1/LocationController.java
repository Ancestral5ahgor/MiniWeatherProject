package com.shuai.weather.controller.v1;


import com.shuai.weather.api.LocationControllerApi;
import com.shuai.weather.dtos.LocationInfo;
import com.shuai.weather.service.findLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/location")
public class LocationController implements LocationControllerApi {

    @Autowired
    private findLocationService findLocationService;

    @GetMapping("/hellolocation")
    @Override
    public LocationInfo findLocation(@RequestParam("latitude") String latitude, @RequestParam("longitude")String longitude) {
        LocationInfo location = findLocationService.findLocation(latitude, longitude);
        return location;
    }
}
