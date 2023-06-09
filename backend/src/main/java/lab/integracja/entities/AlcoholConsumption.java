package lab.integracja.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class AlcoholConsumption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;
    private String indicator;
    private String subject;
    private String measure;
    private String frequency;

    @Column(name = "year_date")
    private Integer time;

    @Column(name = "rate")
    private Float value;
    private String flagCodes;
}
