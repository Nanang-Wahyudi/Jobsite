package sib6.finalproject.Jobsite_ClientApp.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EducationResponse {

    private String id;

    private String instansiName;

    private String major;

    private String avgScore;

}
