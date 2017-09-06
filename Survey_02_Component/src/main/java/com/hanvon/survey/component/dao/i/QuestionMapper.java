package com.hanvon.survey.component.dao.i;

import com.hanvon.survey.entities.guest.Question;
import java.util.List;

public interface QuestionMapper {
    int deleteByPrimaryKey(Integer questionId);

    int insert(Question record);

    Question selectByPrimaryKey(Integer questionId);

    List<Question> selectAll();

    int updateByPrimaryKey(Question record);
    
}