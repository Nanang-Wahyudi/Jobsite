package sib6.finalproject.Jobsite_ServerApp.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateJobRequest {

    @NotBlank(message = "Title is Mandatory")
    private String title;

    @NotBlank(message = "Type is Mandatory")
    private String type;

    @NotBlank(message = "Salary is Mandatory")
    private String salary;

    @NotBlank(message = "Description is Mandatory")
    private String description;

    @NotBlank(message = "Qualification is Mandatory")
    private String qualification;

}
