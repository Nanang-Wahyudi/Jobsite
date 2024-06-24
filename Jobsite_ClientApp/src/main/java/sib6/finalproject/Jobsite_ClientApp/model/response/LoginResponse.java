package sib6.finalproject.Jobsite_ClientApp.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponse {

    private String username;

    private String email;

    private List<String> authorities;

}
