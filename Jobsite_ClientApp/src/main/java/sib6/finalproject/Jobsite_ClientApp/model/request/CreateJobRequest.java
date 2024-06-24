package sib6.finalproject.Jobsite_ClientApp.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateJobRequest {

    private String title;

    private String type;

    private String salary;

    private String description;

    private String qualification;

}
