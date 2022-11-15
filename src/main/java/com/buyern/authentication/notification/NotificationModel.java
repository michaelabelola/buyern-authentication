package com.buyern.authentication.notification;

import lombok.*;
import org.springframework.lang.Nullable;

@Data
//@Builder
public class NotificationModel<T> {
    private NotificationType type;
    private T recipient;
    private String title;
    private String icon;
    @Nullable
    private String content;
}
