package com.hanvon.survey.component.dao.i;

import com.hanvon.survey.entities.manager.Res;
import java.util.List;

public interface ResMapper {
    int deleteByPrimaryKey(Integer resId);

    int insert(Res record);

    Res selectByPrimaryKey(Integer resId);

    List<Res> selectAll();

    int updateByPrimaryKey(Res record);
}