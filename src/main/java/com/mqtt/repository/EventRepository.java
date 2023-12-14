package com.mqtt.repository;


//import com.mqtt.domain.EventType;
import com.mqtt.domain.EventType;
//import com.mqtt.domain.LoRaData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EventRepository extends ElasticsearchRepository<EventType,String> {
}
