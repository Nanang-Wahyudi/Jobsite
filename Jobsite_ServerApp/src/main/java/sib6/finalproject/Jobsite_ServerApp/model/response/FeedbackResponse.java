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
public class FeedbackResponse {

    private String id;

    private Integer rating;

    private String comment;

    private String postDate;

    private List<ApplicantResponse> applicantResponses;

}
