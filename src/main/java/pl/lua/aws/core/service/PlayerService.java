package pl.lua.aws.core.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.lua.aws.core.domain.PokerPlayer;
import pl.lua.aws.core.domain.UploadS3Object;
import pl.lua.aws.core.helper.UserHelper;
import pl.lua.aws.core.model.PokerPlayerEntity;
import pl.lua.aws.core.repository.PokerPlayerRepository;
import pl.lua.aws.core.util.Mapper;

@Service
@Slf4j
public class PlayerService {

    private  static final String URL_PREFIX = "https://s3-us-west-2.amazonaws.com/poker-player-avatars/";
    @Autowired
    private PokerPlayerRepository pokerPlayerRepository;

    @Autowired
    private UserHelper userHelper;

    @Autowired
    S3Services s3Service;

    public PokerPlayer getPlayerData(){
        PokerPlayerEntity pokerPlayerEntity = pokerPlayerRepository.getOne(userHelper.getPlayerId());
        PokerPlayer playerData = Mapper.map(pokerPlayerEntity,PokerPlayer.class);
        return playerData;
    }

    public void savePlayerData(PokerPlayer pokerPlayer){
        PokerPlayerEntity pokerPlayerEntity = pokerPlayerRepository.getOne(userHelper.getPlayerId());
        pokerPlayerEntity.setNickName(pokerPlayer.getNickName());
        pokerPlayerRepository.save(pokerPlayerEntity);
        log.info("Player nickName {} updated ", pokerPlayer.getNickName());
    }

    public void savePlayerAvatar(MultipartFile avatarImage){
        PokerPlayerEntity pokerPlayerEntity = pokerPlayerRepository.getOne(userHelper.getPlayerId());
        s3Service.deletePlayerAvatar(pokerPlayerEntity.getAvatarFileName());
        UploadS3Object avatar  = s3Service.uploadPlayerAvatar(avatarImage);
        pokerPlayerEntity.setAvatarUrl(URL_PREFIX+avatar.getFileName());
        pokerPlayerEntity.setAvatarFileName(avatar.getFileName());
        pokerPlayerRepository.save(pokerPlayerEntity);
        log.info("Player {} upload avatar ", pokerPlayerEntity.getNickName());
    }
}
