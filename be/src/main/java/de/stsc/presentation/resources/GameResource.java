package de.stsc.presentation.resources;

import de.stsc.persistance.entity.Game;
import de.stsc.persistance.entity.Participant;
import de.stsc.presentation.dto.GameDto;
import de.stsc.presentation.dto.ParticipantDto;
import de.stsc.presentation.mapper.GameMapper;
import de.stsc.presentation.mapper.ParticipantMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Path("/game")
@ApplicationScoped
public class GameResource {

    @Inject
    GameMapper gameMapper;
    @Inject
    ParticipantMapper participantMapper;

    @GET
    public List<GameDto> getAll() {
        return Game.<Game>listAll().stream()
                .map(gameMapper::toDto)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{gid}")
    public GameDto getById(long gid) {
        return gameMapper.toDto(Game.findById(gid));
    }

    @POST
    @Transactional
    public GameDto newGame(GameDto dto) {
        Game game = gameMapper.fromDto(dto);
        game.persist();
        return gameMapper.toDto(game);
    }

    @POST
    @Path("/{gid}/participant")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public GameDto addParticipant(long gid, ParticipantDto participantDto) {
        Optional<Game> g = Game.findByIdOptional(gid);
        if (g.isEmpty()) {
            throw new IllegalArgumentException("id is null");
        }

        // ToDo unique name
        Participant participant = participantMapper.fromDto(participantDto);
        participant.persist();
        g.get().addParticipant(participant);
        g.get().persist();
        return gameMapper.toDto(g.get());
    }

    @GET
    @Path("/{gid}/matches")
    @Transactional
    public Map<String, String> getMatches(long gid) {
        Optional<Game> g = Game.findByIdOptional(gid);
        if (g.isEmpty()) {
            throw new IllegalArgumentException("id is null");
        } else {
            List<Participant> participants = g.get().getParticipants().stream()
                    .collect(Collectors
                            .collectingAndThen(
                                    Collectors.toCollection(ArrayList::new),
                                    list -> {
                                        Collections.shuffle(list);
                                        return list;
                                    }
                                    )
                    );
            if (participants.size() < 2) {
                throw new IllegalArgumentException("there need to be at least 2 participants");
            }


            Map<Participant, Participant> matches = participants.stream()
                    .collect(Collectors
                            .toMap(
                                    Function.identity(),
                                    p ->
                                            participants.get((participants.indexOf(p) + 1) % participants.size())));

            matches.forEach((key, value) -> {
                key.setMatch(value);
                key.persist();
            });

            return matches.entrySet().stream()
                    .collect(Collectors
                            .toMap(
                                    entry -> entry.getKey().getName(),
                                    entry -> entry.getValue().getName()));

            // ToDo add test and evaluate that every match has the same odds
        }
    }

    @DELETE
    @Path("/{gid}/matches")
    @Transactional
    public void deleteMatches(long gid) {
        Optional<Game> g = Game.findByIdOptional(gid);
        if (g.isEmpty()) {
            throw new IllegalArgumentException("id is null");
        } else {
            Game game = g.get();
            game.getParticipants().forEach(p -> p.setMatch(null));
        }
    }
}
