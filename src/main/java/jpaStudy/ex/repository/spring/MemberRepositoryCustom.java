package jpaStudy.ex.repository.spring;

import jpaStudy.ex.dto.MemberSearchCondition;
import jpaStudy.ex.dto.MemberTeamDto;

import java.util.List;

public interface MemberRepositoryCustom {
    List<MemberTeamDto> search(MemberSearchCondition condition);
}
