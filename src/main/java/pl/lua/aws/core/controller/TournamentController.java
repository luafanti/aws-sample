package pl.lua.aws.core.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.lua.aws.core.domain.Tournament;
import pl.lua.aws.core.domain.TournamentForm;
import pl.lua.aws.core.service.TournamentService;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;



    @RequestMapping(value = "/tournament/{tournamentId}", method = RequestMethod.GET)
    public String tournamentInfo(@PathVariable String tournamentId, Model model) {
        Long id;
        try{
            id = Long.valueOf(tournamentId);
            Tournament tournament = tournamentService.getTouranment(id);
            model.addAttribute("tournament", tournament);
            model.addAttribute("players",tournament.getParticipants());
            return "tournamentInfo";

        }catch (NumberFormatException e){
            log.warn("Tournament id wrong format. Exception: {}", e.getMessage());
            return "redirect:/tournament";
        }catch (Exception e){
            log.warn("Unexpected exception. Exception: {}", e.getMessage());
            return "redirect:/tournament";
        }

    }

    @RequestMapping(value = "/tournament", method = RequestMethod.GET)
    public String tournamentList(Model model) {

        List<Tournament> tournaments =tournamentService.getAllTournaments();
        model.addAttribute("tournaments", tournaments);
        model.addAttribute("newTournament", new Tournament());
        return "poker";
    }

    @RequestMapping(value = "/create-tournament", method = RequestMethod.POST)
    public String createTournament(@Valid TournamentForm newTournament, BindingResult bindingResult,Model model) {

        log.info("Receive form with new Tournament data {}", newTournament);

        if (bindingResult.hasErrors()) {
            log.info("Tournament form is invalidate");
            List<Tournament> tournaments =tournamentService.getAllTournaments();
            model.addAttribute("tournaments", tournaments);
            model.addAttribute("newTournament", new Tournament());
            return "poker";
        }

        tournamentService.createTournament(newTournament);
        return "redirect:/tournament";
    }

    @RequestMapping(value = "/tournament/register/{tournamentId}", method = RequestMethod.GET)
    public String registerPlayer(@PathVariable String tournamentId) {

        tournamentService.registerPlayer(Long.valueOf(tournamentId));
        return "redirect:/tournament";
    }

    @RequestMapping(value = "/tournament/unregister/{tournamentId}", method = RequestMethod.GET)
    public String unregisterPlayer(@PathVariable String tournamentId) {

        tournamentService.unregisterPlayer(Long.valueOf(tournamentId));
        return "redirect:/tournament";
    }
}
