package com.yovin.notificationservice.notification;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;

@Component
public class DynamoDBTableInitializer {

    private static final String TABLE_NAME = "notifications";

    private final DynamoDbEnhancedClient enhancedClient;

    public DynamoDBTableInitializer(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
    }

    @PostConstruct
    public void init() {
        DynamoDbTable<Notification> table =
                enhancedClient.table(
                        TABLE_NAME,
                        TableSchema.fromBean(Notification.class)
                );

        try {
            table.describeTable();
            System.out.println("Tabla 'notifications' ya existe");
        } catch (ResourceNotFoundException e) {
            System.out.println("Tabla no existe, creando...");

            table.createTable();

            System.out.println("Tabla 'notifications' creada");
        }
    }
}
