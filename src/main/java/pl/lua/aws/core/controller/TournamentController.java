package pl.lua.aws.core.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.lua.aws.core.domain.Tournament;
import pl.lua.aws.core.service.TournamentService;

import java.util.List;

@Controller
@Slf4j
public class TournamentController {

    @Autowired
    TournamentService tournamentService;

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
}
