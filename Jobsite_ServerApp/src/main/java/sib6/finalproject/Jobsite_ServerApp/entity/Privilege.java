package sib6.finalproject.Jobsite_ServerApp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import sib6.finalproject.Jobsite_ServerApp.model.enums.PrivilegeEnum;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "privilege")
@Entity
public class Privilege {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private PrivilegeEnum name;


    @ManyToMany(mappedBy = "privileges")
    private List<Role> roles;

}
