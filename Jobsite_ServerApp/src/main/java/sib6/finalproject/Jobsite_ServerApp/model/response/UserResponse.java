package sib6.finalproject.Jobsite_ServerApp.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sib6.finalproject.Jobsite_ServerApp.entity.Skill;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {

    private String id;

    private String name;

    private String urlPicture;

    private String address;

    private List<Skill> skills;

}
