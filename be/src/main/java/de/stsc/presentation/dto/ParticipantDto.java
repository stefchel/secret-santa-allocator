package de.stsc.presentation.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@RegisterForReflection
public class ParticipantDto {

    private Long id;
    private String name;
    private String matchedName;

}
