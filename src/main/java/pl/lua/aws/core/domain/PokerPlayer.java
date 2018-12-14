package pl.lua.aws.core.domain;

import lombok.Data;


@Data
public class PokerPlayer {

    private Long id;
    private String nickName;
    private String points;
    private String place;
    private String prize;

}
