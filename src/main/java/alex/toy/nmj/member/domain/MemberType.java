package alex.toy.nmj.member.domain;

import alex.toy.nmj.common.exception.InvalidMemberTypeException;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum MemberType {
    USER, STORE, ADMIN;

    @JsonCreator
    public static MemberType from(String type) {
        return Arrays.stream(values())
                .filter(memberType -> memberType.toString().equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(InvalidMemberTypeException::new);
    }
}
