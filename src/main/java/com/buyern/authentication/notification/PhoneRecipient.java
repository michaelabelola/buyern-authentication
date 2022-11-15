package com.buyern.authentication.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PhoneRecipient extends BaseRecipient {
    private String phone;
    @Builder
    public PhoneRecipient(RecipientType type, String uid, String name, String phone) {
        this.type = type;
        this.uid = uid;
        this.name = name;
        this.phone = phone;
    }
}
