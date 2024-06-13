package entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="templates")
@Data
public class TemplateEntity {
    @Id
    @SequenceGenerator(name = "templates_id_seq", allocationSize = 1, sequenceName = "templates_id_seq")
    @GeneratedValue(generator = "templates_id_seq", strategy= GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String template_name;

    @Column
    private String observation_period;

    @Column
    private byte inspections_frequency;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name="templates_doctor_id_fkey"), name="doctor_id", nullable=false)
    private DoctorEntity doctor;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "templates_diseases",
            joinColumns = @JoinColumn(name = "template_id"),
            inverseJoinColumns = @JoinColumn(name = "disease_id")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<DiseaseEntity> diseases;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemplateEntity that = (TemplateEntity) o;
        return inspections_frequency == that.inspections_frequency &&
                Objects.equals(id, that.id) &&
                Objects.equals(template_name, that.template_name) &&
                Objects.equals(observation_period, that.observation_period) &&
                Objects.equals(doctor.getId(), that.doctor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, template_name, observation_period, inspections_frequency, doctor.getId());
    }
}
