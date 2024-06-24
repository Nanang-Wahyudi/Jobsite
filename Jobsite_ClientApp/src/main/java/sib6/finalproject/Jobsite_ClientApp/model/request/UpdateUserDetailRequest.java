package sib6.finalproject.Jobsite_ClientApp.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserDetailRequest {

    private String name;

    private String email;

    private String address;

    private MultipartFile picture;

    private String description;

    private String instansiName;

    private String major;

    private String avgScore;

    private String skillName;

}
