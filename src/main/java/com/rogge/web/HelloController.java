package com.rogge.web;

import com.rogge.PersonProperties;
import com.rogge.common.model.User;
import com.rogge.core.ApiResponse;
import com.rogge.core.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * [Description]
 * <p>
 * [How to use]
 * <p>
 * [Tips]
 *
 * @author Created by Rogge on 2017/10/5 0005.
 * @since 1.0.0
 */
@RestController
public class HelloController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Resource
    private PersonProperties mPersonProperties;

    @GetMapping({"/showHello", "/showHi"})
    public ApiResponse showHello(HttpSession session) {
        User lUser = new User();
        lUser.setId("111111");
        lUser.setName("rogge");
        mSessionUserInfo.setSessionUser(session, lUser);
        logger.info(lUser.getId());
        return ApiResponse.creatSuccess("Hello World" + mPersonProperties.toString());
    }

}
