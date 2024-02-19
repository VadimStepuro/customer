package com.stepuro.customer.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Table(name = "legal_entity", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Setter
@Getter
public class LegalEntity {
    @Id
    @Column(name = "legal_entity_id")
    @GeneratedValue(generator = "legal_entity_id_sequence_generator")
    @SequenceGenerator(name = "legal_entity_id_sequence_generator", sequenceName = "legal_entity_id_sequence", allocationSize = 1)
    private Integer legalEntityId;

    @Column(name = "name")
    private String name;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "inn")
    private String inn;

}
