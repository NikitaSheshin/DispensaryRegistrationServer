package entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "diseases")
@Data
public class DiseaseEntity {
    @Id
    @SequenceGenerator(name = "diseases_id_seq", allocationSize = 1, sequenceName = "diseases_id_seq")
    @GeneratedValue(generator = "diseases_id_seq", strategy= GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String icd_id;

    @Column
    private String disease_name;

    @ManyToMany(mappedBy = "diseases", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<TemplateEntity> templates;
}
