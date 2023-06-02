package de.stsc.presentation.resources;

import de.stsc.persistance.entity.Participant;
import de.stsc.presentation.mapper.ParticipantMapper;
import de.stsc.presentation.dto.ParticipantDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/participant")
@ApplicationScoped
public class ParticipantResource {

    @Inject
    private ParticipantMapper mapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ParticipantDto> getParticipants() {
        return Participant.<Participant>listAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public ParticipantDto addParticipant(ParticipantDto participantDto) {
        // ToDo unique name, move to game resource
        Participant participant = mapper.fromDto(participantDto);
        participant.persist();
        return mapper.toDto(participant);
    }
}