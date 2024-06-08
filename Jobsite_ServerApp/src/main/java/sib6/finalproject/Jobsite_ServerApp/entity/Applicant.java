package sib6.finalproject.Jobsite_ServerApp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import sib6.finalproject.Jobsite_ServerApp.model.enums.StatusApplicantEnum;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "applicant")
@Entity
public class Applicant {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Lob
    private byte[] cv;

    private String applicantDate;

    @Enumerated(EnumType.STRING)
    private StatusApplicantEnum status;


    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne
    @JoinColumn(name = "user_detail_id")
    private UserDetail userDetail;

    @OneToMany(mappedBy = "applicant")
    private List<Feedback> feedbacks;


    public void setApplicantDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.applicantDate = dateFormat.format(date);
    }

}
