package pl.lua.aws.core.domain;

import lombok.Data;

@Data
public class PlayerScore {

    private Long playerId;
    private String nickName;
    private String avatarUrl;
    private Integer totalPoints;
    private Integer totalRebuys;
    private Integer place;
    private Integer wins;
    private Double totalPrize;

}
