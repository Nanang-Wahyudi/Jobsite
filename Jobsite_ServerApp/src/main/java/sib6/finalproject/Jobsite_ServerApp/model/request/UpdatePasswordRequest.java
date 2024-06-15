package sib6.finalproject.Jobsite_ServerApp.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdatePasswordRequest {

    @NotBlank(message = "Password is Mandatory")
    private String password;

    @NotBlank(message = "New Password is Mandatory")
    private String newPassword;

    @NotBlank(message = "Repeat New Password is Mandatory")
    private String repeatNewPassword;

}
