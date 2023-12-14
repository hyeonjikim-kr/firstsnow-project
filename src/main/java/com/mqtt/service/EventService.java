package com.mqtt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mqtt.domain.EventType;
import com.mqtt.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final ObjectMapper objectMapper;
    private final EventRepository eventRepository;

    //    EventType.Object object = eventType.getObject();
    EventType eventType = new EventType();

    public EventType savePayload(String jsonData) {

        //System.currentTimeMillis();
        try {
            eventType =  objectMapper.readValue(jsonData, EventType.class);
            batteryStatus();
            angleState();
            shutDown();
            System.out.println(eventType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return eventRepository.save(eventType);
    }

    public boolean isObjectNull() {
        EventType.Object object = eventType.getObject();
        if (object == null) {
            eventType.setObjectNull(1);
            return true;
        }
        else {
            eventType.setObjectNull(0);
            return false;
        }
    }

    public void batteryStatus(){
        EventType.Object object = eventType.getObject();
        if (isObjectNull() || object == null) {
            return;
        }

        Integer battery = object.getBattery();

        if (battery == null) {
            eventType.setBatteryError("null");
        } else if (battery == 0) {
            eventType.setBatteryError("전원 꺼짐");
        } else if (battery < 20) {
            eventType.setBatteryError("충전 필요");
        } else {
            eventType.setBatteryError("이상 없음");
        }
    }

    public void angleState(){
        EventType.Object object = eventType.getObject();
        if (isObjectNull() || object == null) {
            return;
        }

        final double MAX_SAFE_ANGLE_X = 39.0; // X축 최대 안전 각도
        final double MAX_SAFE_ANGLE_Y = 39.0; // Y축 최대 안전 각도

        int angleX = object.getAngleX();
        int angleY = object.getAngleY();

        if(angleX <= -MAX_SAFE_ANGLE_X || angleX >= MAX_SAFE_ANGLE_X) {
            eventType.setRoll("옆으로 넘어짐");
        }

        if (angleY <= -MAX_SAFE_ANGLE_Y || angleY >= MAX_SAFE_ANGLE_Y){
            eventType.setPitch("앞뒤로 넘어짐");
        }
    }

    public void shutDown(){
        EventType.Object object = eventType.getObject();
        if (isObjectNull() || object == null) {
            return;
        }

        double latitude = object.getLatitude();
        double longitude = object.getLongitude();


        if (latitude == 0 && longitude == 0){
            eventType.setDriveState("기기가 실내에 있음");
        } else{
            eventType.setDriveState("정상 운행중");
        }
    }

}

