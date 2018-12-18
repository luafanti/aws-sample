package pl.lua.aws.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.lua.aws.core.domain.PlayerScore;
import pl.lua.aws.core.service.RankingService;

import java.util.List;

@Controller
public class RankingController {


    @Autowired
    private RankingService rankingService;


    @RequestMapping(value = "/ranking", method = RequestMethod.GET)
    public String tournamentList(Model model) {
        List<PlayerScore> scores = rankingService.getRankingOfAllPlayers();
        model.addAttribute("scores",scores);
        return "ranking";
    }
}
