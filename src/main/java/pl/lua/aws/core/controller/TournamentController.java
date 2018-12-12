package pl.lua.aws.core.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.lua.aws.core.domain.Tournament;
import pl.lua.aws.core.service.TournamentService;

import java.util.List;

@Controller
@Slf4j
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    @RequestMapping(value = "/create-tournament", method = RequestMethod.POST)
    public String createTournament(@ModelAttribute Tournament newTournament) {

        log.info("Receive form with new Tournament date {}", newTournament);
        tournamentService.createTournament(newTournament);
        return "redirect:/tournament";
    }

    @RequestMapping(value = "/tournament", method = RequestMethod.GET)
    public String createTournament(Model model) {
        List<Tournament> tournaments =tournamentService.getAllTournaments();
        model.addAttribute("tournaments", tournaments);
        model.addAttribute("newTournament", new Tournament());
        return "poker";
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
