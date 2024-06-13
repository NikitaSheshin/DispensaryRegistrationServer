package entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="templates_patients")
@Data
public class TemplatePatientEntity implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "template_id")
    private TemplateEntity templateId;

    @Id
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patientId;

    @Column(name = "observation_start")
    private LocalDate observationStart;

    @Column(name = "observation_end")
    private LocalDate observationEnd;

    @Column(name = "next_observation_date")
    private LocalDate nextObservationDate;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctorId;
}
