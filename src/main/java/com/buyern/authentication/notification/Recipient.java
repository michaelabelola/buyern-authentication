package com.buyern.authentication.notification;

import lombok.Data;

@Data
public class Recipient {
    private RecipientType type;
    private String uid;
    private String name;
    private String phone;
    private String email;
    private String deviceId;

    public PhoneRecipient phoneRecipient() {
        return new PhoneRecipient(type, uid, name, phone);
    }

    public EmailRecipient emailRecipient() {
        return new EmailRecipient(type, uid, name, email);
    }

    public PushRecipient pushRecipient() {
        return new PushRecipient(type, uid, name, deviceId);
    }
}
