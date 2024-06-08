package sib6.finalproject.Jobsite_ServerApp.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompanyDetailResponse {

    private String id;

    private String name;

    private String email;

    private String urlPicture;

    private String urlBanner;

    private String description;

    private String address;

    private List<FeedbackResponse> feedbackResponses;

    private List<JobResponse> jobResponses;

}
