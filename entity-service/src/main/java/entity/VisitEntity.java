package entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "visits")
@Data
public class VisitEntity implements Serializable {
    @Id
    @SequenceGenerator(name = "diseases_id_seq", allocationSize = 1, sequenceName = "diseases_id_seq")
    @GeneratedValue(generator = "diseases_id_seq", strategy= GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patientId;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor_id;

    @Column(name = "visit_date")
    private LocalDate visitDate;

    @Column(length = 400)
    private String complaints;

    @Column(length = 400, name = "life_anamnesis")
    private String lifeAnamnesis;

    @Column(length = 400, name = "disease_anamnesis")
    private String diseaseAnamnesis;

    @Column(length = 400)
    private String recomendations;
}
