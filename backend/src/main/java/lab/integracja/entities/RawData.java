package lab.integracja.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "raw_data")
@NoArgsConstructor
@AllArgsConstructor
public class RawData {
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
