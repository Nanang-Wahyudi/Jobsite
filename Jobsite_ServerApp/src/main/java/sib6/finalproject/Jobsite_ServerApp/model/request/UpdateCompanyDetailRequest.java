package sib6.finalproject.Jobsite_ServerApp.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCompanyDetailRequest {

    private String name;

    @Email
    private String email;

    private String address;

    private MultipartFile picture;

    private MultipartFile banner;

    private String description;

}
