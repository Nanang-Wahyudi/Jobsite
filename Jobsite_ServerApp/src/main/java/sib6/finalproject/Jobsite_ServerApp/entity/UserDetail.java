package sib6.finalproject.Jobsite_ServerApp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "user_detail")
@Entity
public class UserDetail {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;

    private String picture;

    @Column(unique = true)
    private String email;

    @Lob
    private String description;

    private String address;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "user_detail_education",
            joinColumns = @JoinColumn(name = "user_detail_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "education_id", referencedColumnName = "id")
    )
    private List<Education> educations;

    @ManyToMany
    @JoinTable(
            name = "user_detail_skill",
            joinColumns = @JoinColumn(name = "user_detail_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id")
    )
    private List<Skill> skills;

    @OneToMany(mappedBy = "userDetail", cascade = CascadeType.ALL)
    List<Applicant> applicants;

    @OneToMany(mappedBy = "userDetail", cascade = CascadeType.ALL)
    List<Feedback> feedbacks;

}
