package de.melkodary.shserver.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

    public enum SEX {
        MALE,
        FEMAlE,
        OTHER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Patient.SEX sex;

    @ManyToMany(mappedBy = "patients")
    private Set<Hospital> hospitals;
}
