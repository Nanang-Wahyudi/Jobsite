package sib6.finalproject.Jobsite_ClientApp.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApplicantResponse {

    private String id;

    private String urlPicture;

    private String nameUser;

    private String companyName;

    private String titleJob;

    private String status;

    private String applicantDate;

}
