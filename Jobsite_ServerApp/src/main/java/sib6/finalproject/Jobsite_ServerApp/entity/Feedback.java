package sib6.finalproject.Jobsite_ServerApp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "feedback")
@Entity
public class Feedback {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private Integer rating;

    @Lob
    private String comment;

    private String postDate;


    @ManyToOne
    @JoinColumn(name = "user_detail_id")
    private UserDetail userDetail;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;


    public void setPostDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.postDate = dateFormat.format(date);
    }

}
