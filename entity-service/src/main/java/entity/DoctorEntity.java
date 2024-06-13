package entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="doctors")
@Data
public class DoctorEntity implements Serializable {
    @Id
    @SequenceGenerator(name = "doctors_id_seq", allocationSize = 1, sequenceName = "doctors_id_seq")
    @GeneratedValue(generator = "doctors_id_seq", strategy= GenerationType.SEQUENCE)
    private Long id;

    @Column(length=100, nullable=false)
    private String firstname;

    @Column(length=100)
    private String patronymic;

    @Column(length=100, nullable=false)
    private String lastname;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name="doctors_specialty_id_fkey"), name="specialty_id", nullable=false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private DoctorsSpecialtiesEntity specialty;

    @Column(name="doctorlogin", length=100, nullable=false)
    private String login;

    @Column(name="doctorpassword", length=100, nullable=false)
    private String password;

    @OneToMany(mappedBy = "doctor")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<TemplateEntity> templates;
}
