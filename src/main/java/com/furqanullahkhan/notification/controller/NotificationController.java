package com.furqanullahkhan.notification.controller;


import com.furqanullahkhan.notification.model.Notification;
//import org.example.Model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private KafkaTemplate<String, Notification> kafkaTemplate;

    private static final String TOPIC = "notification-topic";

    @MessageMapping("/sendNotification")
    public void sendNotification(@Payload Notification notification) {
        kafkaTemplate.send(TOPIC, notification);
    }

    @KafkaListener(topics = TOPIC, groupId = "notification-group")
    public void listen(Notification notification) {
        simpMessagingTemplate.convertAndSend("/notifications", notification);
    }
}


