package pl.lua.aws.core.domain;

import lombok.Data;


@Data
public class PokerPlayer {

    private Long id;
    private String nickName;
    private String avatarUrl;
    private String avatarFileName;
    private Integer points;
    private String place;
    private Double prize;

}
