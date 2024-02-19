package com.stepuro.customer.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "individual", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Individual {
    @Id
    @Column(name = "individual_id")
    @GeneratedValue(generator = "individual_id_sequence_generator")
    @SequenceGenerator(name = "individual_id_sequence_generator", sequenceName = "individual_id_sequence", allocationSize = 1)
    private Integer individualId;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "day_of_birth")
    private java.sql.Date dayOfBirth;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @OneToMany(mappedBy = "individual", cascade = CascadeType.REMOVE)
    private List<Card> cards;

}
