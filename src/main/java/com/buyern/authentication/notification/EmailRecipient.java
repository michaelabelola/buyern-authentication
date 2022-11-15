package com.buyern.authentication.notification;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmailRecipient extends BaseRecipient {
    private String email;

    @Builder
    public EmailRecipient(RecipientType type, String uid, String name, String email) {
        this.type = type;
        this.uid = uid;
        this.name = name;
        this.email = email;
    }
}
