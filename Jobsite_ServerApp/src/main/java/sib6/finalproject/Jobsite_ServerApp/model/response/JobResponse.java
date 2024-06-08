package sib6.finalproject.Jobsite_ServerApp.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JobResponse {

    private String id;

    private String title;

    private String type;

    private String salary;

    private String postDate;

    private Boolean is_active;

}
