package com.hanvon.survey.component.dao.i;

import com.hanvon.survey.entities.manager.Auth;
import java.util.List;

public interface AuthMapper {
    int deleteByPrimaryKey(Integer authId);

    int insert(Auth record);

    Auth selectByPrimaryKey(Integer authId);

    List<Auth> selectAll();

    int updateByPrimaryKey(Auth record);
}