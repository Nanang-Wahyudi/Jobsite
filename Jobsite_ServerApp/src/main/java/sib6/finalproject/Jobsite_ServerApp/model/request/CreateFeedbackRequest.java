package sib6.finalproject.Jobsite_ServerApp.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateFeedbackRequest {

    @NotNull(message = "Rating cannot be null")
    @Min(value = 1, message = "Rating must be greater than 0")
    private Integer rating;

    @NotBlank(message = "Comment is Mandatory")
    private String comment;

}
