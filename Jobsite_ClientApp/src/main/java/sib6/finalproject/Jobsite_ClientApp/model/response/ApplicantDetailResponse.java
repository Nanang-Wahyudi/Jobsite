package sib6.finalproject.Jobsite_ClientApp.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApplicantDetailResponse {

    private String id;

    private JobResponse jobResponse;

    private UserResponse userResponse;

    private String urlDownloadCv;

    private String applicantDate;

    private String status;

}
