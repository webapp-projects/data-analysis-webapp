package lab.integracja.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "alcohol_consumption")
@AllArgsConstructor
@NoArgsConstructor
public class AlcoholConsumption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Country country;

    @Enumerated(EnumType.STRING)
    private Subject subject;

    @ManyToOne
    private Measure measure;

    @Column(name = "year_date")
    private Integer time;

    @Column(name = "rate")
    private Float value;
}
