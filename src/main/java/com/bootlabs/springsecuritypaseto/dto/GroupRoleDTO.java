package com.bootlabs.springsecuritypaseto.dto;

import com.bootlabs.springsecuritypaseto.enums.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * This class used for convert ApplicationGroupRole data
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GroupRoleDTO {

    private long id;

    @NotNull
    @NotBlank(message = "Code role is mandatory")
    @Size(min = 1, max = 50, message = "The code role '${validatedValue}' must be between {min} and {max} characters long")
    private RoleEnum code;

    private String description;

}
