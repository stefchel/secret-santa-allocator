package de.stsc.persistance.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
public class Participant extends PanacheEntity {

    private String name;

    @OneToOne
    private Participant match;

}
