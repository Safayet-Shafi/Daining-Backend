package Daining.UserInformation.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USERINFORMATION", schema = "DAINING")
public class UserInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userinformation_seq_gen")
    @SequenceGenerator(name = "userinformation_seq_gen", sequenceName = "DAINING.USERINFORMATION_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FIRST_NAME", length = 100)
    private String firstName;

    @Column(name = "LAST_NAME", length = 100)
    private String lastName;

    @Column(name = "EMAIL", length = 50)
    private String email;

    @Column(name = "USERNAME", length = 50)
    private String username;

    @Column(name = "PASSWORD", length = 20)
    private String password;


    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "ACTIVE_FLAG", length = 1)
    private String activeFlag;
}
