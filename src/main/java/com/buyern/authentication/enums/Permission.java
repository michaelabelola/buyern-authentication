package com.buyern.authentication.enums;

public enum Permission {
    ALL, READ, CREATE, WRITE, EDIT, DELETE,
    // permissions
    GRANT, REVOKE,
    //ROLE
    ASSIGN, UN_ASSIGN,
    // finance
    VIEW_TRANSACTION_HISTORY, CREATE_ACCOUNT, DELETE_ACCOUNT, ACCOUNT_PREFERENCES,
    //stakeholder
    ADD,
    REMOVE
}

