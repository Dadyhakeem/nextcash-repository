package com.dev.hakeem.nextcash.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdatePasswordDTO {



    @NotBlank(message = "Current password cannot be blank")
    @Size(min = 6,max = 100,message = "as senha nao pode ultrapassar 6 digitos")
    private String currentPassword;

    @NotBlank(message = "New password cannot be blank")
    @Size(min = 6,max = 100,message = "as senha nao pode ultrapassar 6 digitos")
    private String newPassword;

    @NotBlank(message = "Confirm password cannot be blank")
    @Size(min = 6,max = 100,message = "as senha nao pode ultrapassar 6 digitos")
    private String confirmPassword;

}
