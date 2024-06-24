package sib6.finalproject.Jobsite_ClientApp.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdatePasswordRequest {

    private String password;

    private String newPassword;

    private String repeatNewPassword;

}
