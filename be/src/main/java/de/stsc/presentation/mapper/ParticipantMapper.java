package de.stsc.presentation.mapper;

import de.stsc.persistance.entity.Participant;
import de.stsc.presentation.dto.ParticipantDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface ParticipantMapper {

    ParticipantDto toDto(Participant participant);

    Participant fromDto(ParticipantDto participantDto);
}
