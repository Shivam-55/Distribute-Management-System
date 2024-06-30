package com.company.dms.dto.userdto;
import com.company.dms.utils.ExcludeFromCodeCoverage;

import lombok.*;
@ExcludeFromCodeCoverage
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginDto {
    private String password;
    private String email;
}
