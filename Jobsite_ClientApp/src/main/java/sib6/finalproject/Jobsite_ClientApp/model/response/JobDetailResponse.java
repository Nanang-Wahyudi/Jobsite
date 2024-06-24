package sib6.finalproject.Jobsite_ClientApp.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JobDetailResponse {

    private String id;

    private String title;

    private String type;

    private String description;

    private String qualification;

    private String salary;

    private String postDate;

    private Boolean isActive;

    private CompanyResponse companyResponse;

}
