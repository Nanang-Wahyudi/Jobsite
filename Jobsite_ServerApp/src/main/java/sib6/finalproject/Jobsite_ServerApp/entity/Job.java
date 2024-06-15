package sib6.finalproject.Jobsite_ServerApp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import sib6.finalproject.Jobsite_ServerApp.model.enums.JobTypeEnum;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "job")
@Entity
public class Job {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String title;

    @Enumerated(EnumType.STRING)
    private JobTypeEnum type;

    @Lob
    private String description;

    @Lob
    private String qualification;

    private String salary;

    private String postDate;

    private Boolean isActive;


    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<Applicant> applicants;


    public void setPostDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.postDate = dateFormat.format(date);
    }

}
