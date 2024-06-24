package sib6.finalproject.Jobsite_ClientApp.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FeedbackResponse {

    private String id;

    private Integer rating;

    private String comment;

    private String postDate;

    private ApplicantResponse applicantResponse;

}
