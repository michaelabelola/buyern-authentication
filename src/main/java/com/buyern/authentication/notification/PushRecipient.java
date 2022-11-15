package com.buyern.authentication.notification;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PushRecipient extends BaseRecipient {
    private String deviceId;
    @Builder
    public PushRecipient(RecipientType type, String uid, String name, String deviceId) {
        this.type = type;
        this.uid = uid;
        this.name = name;
        this.deviceId = deviceId;
    }
}
