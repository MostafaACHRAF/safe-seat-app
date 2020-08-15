package com.sqli.safeSeat.converters;

import com.sqli.safeSeat.enums.Team;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TeamConverter implements AttributeConverter<Team, Integer> {

    @Override public Integer convertToDatabaseColumn(Team team) {
        if (team == null) return null;
        return switch (team) {
            case SEB -> 118;
            case VOO -> 119;
            case CHANNEL -> 120;
            case NESPRESSO -> 121;
        };
    }

    @Override public Team convertToEntityAttribute(Integer integer) {
        if (integer == null) return null;
        return switch (integer) {
            case 118 -> Team.SEB;
            case 119 -> Team.VOO;
            case 120 -> Team.CHANNEL;
            case 121 -> Team.NESPRESSO;
            default -> throw new IllegalStateException("Unexpected value: " + integer);
        };
    }
}
