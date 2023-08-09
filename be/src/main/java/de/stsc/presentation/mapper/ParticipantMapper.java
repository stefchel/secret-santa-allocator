package de.stsc.presentation.mapper;

import de.stsc.persistance.entity.Participant;
import de.stsc.presentation.dto.ParticipantDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface ParticipantMapper {

    @Mapping(target = "matchedName", source = "match.name")
    ParticipantDto toDto(Participant participant);

    Participant fromDto(ParticipantDto participantDto);
}
