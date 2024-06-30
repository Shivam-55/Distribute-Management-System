package com.company.dms.customenum;

import com.company.dms.utils.ExcludeFromCodeCoverage;
/**
 * Enumeration representing approval status.
 */
@ExcludeFromCodeCoverage
public enum IsApproved {
    /**
     * Indicates that something is not approved.
     */
    NOTAPPROVED,

    /**
     * Indicates that something is approved.
     */
    APPROVED,

    /**
     * Indicates that something was approved once.
     */
    ONCEAPPROVED
}
