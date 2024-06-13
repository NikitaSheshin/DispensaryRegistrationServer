package entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="templates_diseases")
@Data
public class TemplatesDiseasesEntity implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "template_id")
    private TemplateEntity templateId;

    @Id
    @ManyToOne
    @JoinColumn(name = "disease_id")
    private DiseaseEntity diseaseId;
}
