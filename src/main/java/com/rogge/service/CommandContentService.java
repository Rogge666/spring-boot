package com.rogge.service;
import com.rogge.model.CommandContent;
import com.rogge.core.Service;
import com.rogge.model.vo.CommentVO;

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
public interface CommandContentService extends Service<CommandContent> {
    List<CommentVO> getCommandLeftJoin();
}
