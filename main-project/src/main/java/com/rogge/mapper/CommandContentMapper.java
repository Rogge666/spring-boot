package com.rogge.mapper;

import com.rogge.core.Mapper;
import com.rogge.model.CommandContent;
import com.rogge.model.vo.CommentVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandContentMapper extends Mapper<CommandContent> {
    List<CommentVO> selectLeftJoinCommand();
}