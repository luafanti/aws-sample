package pl.lua.aws.core.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.lua.aws.core.domain.Tournament;
import pl.lua.aws.core.domain.TournamentForm;
import pl.lua.aws.core.helper.UserHelper;
import pl.lua.aws.core.service.ScoresService;
import pl.lua.aws.core.service.TournamentService;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
public class ScoreController {

    @Autowired
    private TournamentService tournamentService;
    @Autowired
    UserHelper userHelper;
    @Autowired
    private ScoresService scoresService;

    @RequestMapping(value = "/scores/{tournamentId}", method = RequestMethod.GET)
    public String tournamentList(@PathVariable String tournamentId, Model model) {

        if(userHelper.isAdminUser()){
            Long id;
            try{
                id = Long.valueOf(tournamentId);
                Tournament tournament = tournamentService.getTournament(id);
                model.addAttribute("tournament",tournament);
                return "editScores";

            }catch (NumberFormatException e){
                log.warn("Tournament id wrong format. Exception: {}", e.getMessage());
                return "redirect:/tournament";
            }catch (Exception e){
                log.warn("Unexpected exception. Exception: {}", e.getMessage());
                return "redirect:/tournament";
            }
        }else {
            log.warn("User {} haven't permission to edit players scores");
            return "poker";
        }
    }

    @RequestMapping(value = "/scores", method = RequestMethod.POST)
    public String createTournament(@Valid Tournament tournament, BindingResult bindingResult,Model model) {

        log.info("Receive user score with {}", tournament.toString());

        //TODO throw and catch exceptin (invalid form values)
        if (bindingResult.hasErrors()) {
            log.info("Scores form is invalidate");
            Tournament existingTournament = tournamentService.getTournament(tournament.getId());
            model.addAttribute("tournament", existingTournament);
            return "editScores";
        }else{
            scoresService.saveScores(tournament.getParticipants(),tournament.getId());
        }
        return "redirect:/tournament/"+tournament.getId();
    }
}
