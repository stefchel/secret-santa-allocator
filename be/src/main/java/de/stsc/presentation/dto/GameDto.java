package de.stsc.presentation.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RegisterForReflection
public class GameDto {

    private Long id;
    private String name;

    private List<ParticipantDto> participants;
}
