package sib6.finalproject.Jobsite_ClientApp.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ForgotPasswordRequest {

    private String username;

    private String newPassword;

    private String repeatNewPassword;

}
