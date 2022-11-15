package com.buyern.authentication.notification;

import lombok.Data;

@Data
class BaseRecipient {
    RecipientType type;
    String uid;
    String name;
}
