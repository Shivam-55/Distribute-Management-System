package com.example.inventoryservice.customenum;

/**
 * Enum representing approval status.
 */
public enum IsApproved {
    /** Not approved status. */
    NOTAPPROVED,
    /** Approved status. */
    APPROVED,
    /** Once approved status. */
    ONCEAPPROVED
}
//userDtoResponse.isApproved().equals(IsApproved.APPROVED)