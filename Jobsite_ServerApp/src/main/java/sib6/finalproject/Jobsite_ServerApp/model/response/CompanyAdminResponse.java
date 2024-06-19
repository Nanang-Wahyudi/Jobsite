package sib6.finalproject.Jobsite_ServerApp.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompanyAdminResponse {

    private String id;

    private String urlPicture;

    private String username;

    private String name;

    private String email;

    private String address;

}
