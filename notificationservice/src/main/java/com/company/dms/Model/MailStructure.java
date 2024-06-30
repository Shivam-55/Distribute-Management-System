package com.company.dms.Model;

import lombok.Getter;
import lombok.Setter;

/**
 * Model class representing the structure of an email.
 */
@Getter
@Setter
public class MailStructure {
    private String subject;
    private String message;
}
