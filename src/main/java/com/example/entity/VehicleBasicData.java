package com.example.entity;

import java.io.Serializable;

/**
 *
 * @Author: HYX
 * @Date: 2020/7/29 15:52
 */
public class VehicleBasicData implements Serializable {

    /**
     * 车牌号
     */
    private String licensePlate;
    /**
     * 车辆识别码
     */
    private String vin;
    /**
     * 车辆性质（私有车辆/公司车辆）
     */
    private String vehicleProperties;
    /**
     * 发动机编号
     */
    private String engineNumber;


    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getVehicleProperties() {
        return vehicleProperties;
    }

    public void setVehicleProperties(String vehicleProperties) {
        this.vehicleProperties = vehicleProperties;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }
}
