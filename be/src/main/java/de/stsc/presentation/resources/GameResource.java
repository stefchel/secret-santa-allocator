package de.stsc.presentation.resources;

import de.stsc.persistance.entity.Game;
import de.stsc.persistance.entity.Participant;
import de.stsc.presentation.dto.GameDto;
import de.stsc.presentation.mapper.GameMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Path("/game")
@ApplicationScoped
public class GameResource {

    @Inject
    private GameMapper mapper;

    @GET
    public List<GameDto> getAll() {
        return Game.<Game>listAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{gid}")
    public GameDto getById(long gid) {
        return mapper.toDto(Game.findById(gid));
    }

    @POST
    @Transactional
    public GameDto newGame(GameDto dto) {
        Game game = mapper.fromDto(dto);
        game.persist();
        return mapper.toDto(game);
    }

    @PUT
    @Path("/{gid}/participant/{pid}")
    @Transactional
    public GameDto addParticipant(long gid, long pid) {
        Optional<Participant> p = Participant.findByIdOptional(pid);
        Optional<Game> g = Game.findByIdOptional(gid);
        if (p.isEmpty() || g.isEmpty()) {
            throw new IllegalArgumentException("id is null");
        } else {
            g.get().addParticipant(p.get());
            g.get().persist();
            return mapper.toDto(g.get());
        }
    }

    @GET
    @Path("/{gid}/matches")
    public Map<String, String> getMatches(long gid) {
        Optional<Game> g = Game.findByIdOptional(gid);
        if (g.isEmpty()) {
            throw new IllegalArgumentException("id is null");
        } else {
            List<String> participants = g.get().getParticipants().stream()
                    .map(Participant::getName)
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

            return participants.stream()
                    .collect(Collectors
                            .toMap(
                                    Function.identity(),
                                    p ->
                                            participants.get((participants.indexOf(p) + 1) % participants.size())));

            // ToDo add test and evaluate that every match has the same odds
            // ToDo Store the match to the participants --> maybe with possibility to reset match
        }
    }
}
