package entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name="patients")
@Data
public class PatientEntity {
    @Id
    @SequenceGenerator(name = "patients_id_seq", allocationSize = 1, sequenceName = "patients_id_seq")
    @GeneratedValue(generator = "patients_id_seq", strategy= GenerationType.SEQUENCE)
    private Long id;

    @Column(name="firstname")
    private String firstName;
    @Column(name="lastname")
    private String lastName;
    @Column
    private String patronymic;

    @Column
    private LocalDate birthday;

    @Column
    private String address;

    @Column(name="phonenumber")
    private String phoneNumber;

    @Column(name="isobserved")
    private boolean isObserved;

    @Column(name="passport_number", length=6)
    private String passportNumber;

    @Column(name="passport_series", length=4)
    private String passportSeries;

    @Column(name="snils_number", length=14)
    private String snilsNumber;

    @Column(name="oms_policy", length = 16)
    private String omsPolicy;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "patients_diseases",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "disease_id")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<DiseaseEntity> diseases;

    @Column(name = "gender")
    private boolean gender;
}
