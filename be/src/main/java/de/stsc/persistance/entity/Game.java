package de.stsc.persistance.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Game extends PanacheEntity {

    private String name;

    @OneToMany
    @JoinColumn(name = "participant_id")
    private List<Participant> participants;

    public void addParticipant(Participant p) {
        if (participants == null) {
            participants = new ArrayList<>();
        }
        participants.add(p);
    }

}
