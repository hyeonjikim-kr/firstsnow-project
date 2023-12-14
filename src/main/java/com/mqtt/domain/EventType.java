package com.mqtt.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;
import java.util.List;

@Document(indexName = "loradata")
@Data
@RequiredArgsConstructor
public class EventType {
    @Id
    private String id;
    private String applicationID;
    private String applicationName;
    private String deviceName;
    private String devEUI;
    private List<RxInfo> rxInfo;
    private TxInfo txInfo;
    private boolean adr;
    private int fCnt;
    private int fPort;
    private String data;
    private Object object;
    private String batteryError;
    private int objectNull;
    private String roll;
    private String pitch;
    private String driveState;

    @Data
    public static class RxInfo{
        private String gatewayID;
        private String uplinkID;
        private String name;
        private int rssi;
        private int loRaSNR;
        /*private Location location;
        @Data
        static class Location {
            private double latitude;
            private double longitude;
            private double altitude;
        }*/
    }
    @Data
    static class TxInfo{
        private long frequency;
        private int dr;
    }

    @Data
    public static class Object{
        @JsonProperty("abnormal_detection")
        private String abnormalDetection;
        @JsonProperty("drive_status")
        private String driveStatus;
        @JsonProperty("sensor_error")
        private String sensorError;
        private int angleX;
        private int angleY;
        private Integer battery;
        private String falldown;
        private double latitude;
        private double longitude;
        private int status;
    }

    @CreatedDate
    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    private Instant createdDate;
    @LastModifiedDate
    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    private Instant lastModifiedDate;

}
