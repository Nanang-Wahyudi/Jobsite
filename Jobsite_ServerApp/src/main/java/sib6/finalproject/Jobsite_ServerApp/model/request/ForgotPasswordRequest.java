package sib6.finalproject.Jobsite_ServerApp.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ForgotPasswordRequest {

    @NotBlank(message = "Username is Mandatory")
    private String username;

    @NotBlank(message = "New Password is Mandatory")
    private String newPassword;

    @NotBlank(message = "Repeat New Password is Mandatory")
    private String repeatNewPassword;

}
