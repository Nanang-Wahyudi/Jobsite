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
public class UserDetailResponse {

    private String id;

    private String name;

    private String urlPicture;

    private String email;

    private String description;

    private String address;

    private List<SkillResponse> skillResponses;

    private List<EducationResponse> educationResponses;

}
