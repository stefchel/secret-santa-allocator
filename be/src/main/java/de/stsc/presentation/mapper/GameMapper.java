package de.stsc.presentation.mapper;

import de.stsc.persistance.entity.Game;
import de.stsc.presentation.dto.GameDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi", uses = ParticipantMapper.class)
public interface GameMapper {

    GameDto toDto(Game game);

    Game fromDto(GameDto dto);
}
