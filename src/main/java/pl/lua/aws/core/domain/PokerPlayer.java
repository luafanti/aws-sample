package pl.lua.aws.core.domain;

import lombok.Data;

import javax.persistence.Column;


@Data
public class PokerPlayer {

    private Long id;
    private String nickName;
    private Integer points;
    private String place;
    private Double prize;

}
