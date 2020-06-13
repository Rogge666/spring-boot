package com.rogge.service.impl;

import com.rogge.mapper.CommandContentMapper;
import com.rogge.model.CommandContent;
import com.rogge.model.vo.CommentVO;
import com.rogge.service.CommandContentService;
import com.rogge.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
* [Description]
* <p>
* [How to use]
* <p>
* [Tips]
*
* @author Created by Rogge on 2017/10/06
* @since 1.0.0
*/
@Service
@Transactional
public class CommandContentServiceImpl extends AbstractService<CommandContent> implements CommandContentService {
    @Resource
    private CommandContentMapper commandContentMapper;

    @Override
    public List<CommentVO> getCommandLeftJoin() {
        return commandContentMapper.selectLeftJoinCommand();
    }
}
