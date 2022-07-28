package com.gsafety.iot.test.entity;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

/**
 * @Date 2022/4/2 17:02
 * @Author wangqiang
 * @Description TODO
 **/
@Measurement(name = "p0001")
public class InfluxDeviceLog {

    /**
     * 设备外部映射编号
     */
    @Column(name = "device_mapping_code", tag = true)
    private String deviceMappingCode;

    /**
     * 设备编号
     */
    @Column(name = "device_code", tag = true)
    private String deviceCode;

    /**
     * 数据类型
     */
    @Column(name = "data_type", tag = true)
    private String dataType;

    /**
     * 上报ip
     */
    @Column(name = "report_ip")
    private String reportIp;

    /**
     * 采样时间
     */
    @Column(name = "sampling_time")
    private long samplingTime;


    /**
     * 上报时音
     */
    @Column(name = "up_time")
    private long upTime;

    /**
     * 产品编号
     */
    @Column(name = "product_code")
    private String productCode;

    /**
     * 报文长度
     */
    @Column(name = "msgLengh")
    private long msgLengh;

    /**
     * 报文
     */
    @Column(name = "msg")
    private String msg;

    public String getDeviceMappingCode() {
        return deviceMappingCode;
    }

    public void setDeviceMappingCode(String deviceMappingCode) {
        this.deviceMappingCode = deviceMappingCode;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getReportIp() {
        return reportIp;
    }

    public void setReportIp(String reportIp) {
        this.reportIp = reportIp;
    }

    public long getSamplingTime() {
        return samplingTime;
    }

    public void setSamplingTime(long samplingTime) {
        this.samplingTime = samplingTime;
    }

    public long getUpTime() {
        return upTime;
    }

    public void setUpTime(long upTime) {
        this.upTime = upTime;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public long getMsgLengh() {
        return msgLengh;
    }

    public void setMsgLengh(long msgLengh) {
        this.msgLengh = msgLengh;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
