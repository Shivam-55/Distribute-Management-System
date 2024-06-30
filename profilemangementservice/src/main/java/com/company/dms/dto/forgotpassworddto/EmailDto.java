package com.company.dms.dto.forgotpassworddto;

import com.company.dms.utils.ExcludeFromCodeCoverage;

import lombok.*;
/**
 * Data transfer object (DTO) representing an email address.
 */
@ExcludeFromCodeCoverage
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmailDto {
    /**
     * The email address.
     */
    private String email;
}
