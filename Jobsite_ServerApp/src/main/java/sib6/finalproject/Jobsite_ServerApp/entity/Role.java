package sib6.finalproject.Jobsite_ServerApp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import sib6.finalproject.Jobsite_ServerApp.model.enums.RoleEnum;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "role")
@Entity
public class Role {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private RoleEnum name;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_privilege",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id")
    )
    List<Privilege> privileges;

}
