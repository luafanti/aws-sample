package pl.lua.aws.core.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.lua.aws.core.domain.PokerPlayer;
import pl.lua.aws.core.service.PlayerService;

@Controller
@Slf4j
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @RequestMapping(value = "/edit-player", method = RequestMethod.GET)
    public String tournamentList(Model model) {
        PokerPlayer player = playerService.getPlayerData();
        model.addAttribute("player",player);
        log.info("Reurn url {}",player.getAvatarUrl());
        return "editPlayer";
    }

    @RequestMapping(value = "/edit-player", method = RequestMethod.POST)
    public String createTournament(PokerPlayer pokerPlayer, Model model) {

        if(pokerPlayer.getNickName()!=null && !pokerPlayer.getNickName().trim().isEmpty()){
            playerService.savePlayerData(pokerPlayer);
        }else{
            log.warn("Wrong edit-player form data");
        }
        return "redirect:/edit-player";
    }

    @RequestMapping(value = "/player-avatar", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            if(file!=null && file.getSize()!=0){
                playerService.savePlayerAvatar(file);
            }else{
                log.warn("File is empty");
            }
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return "redirect:/edit-player";
        }

        return "redirect:/edit-player";
    }
}
