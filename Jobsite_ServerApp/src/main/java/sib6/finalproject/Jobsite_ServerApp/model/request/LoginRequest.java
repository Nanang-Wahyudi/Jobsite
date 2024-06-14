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
public class LoginRequest {

    @NotBlank(message = "Username is Mandatory")
    private String username;

    @NotBlank(message = "Password is Mandatory")
    private String password;

}
