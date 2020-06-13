package com.rogge.web;

import com.rogge.PersonProperties;
import com.rogge.common.model.User;
import com.rogge.core.ApiResponse;
import com.rogge.core.BaseController;
import com.rogge.module.redis.RedisClient;
import com.rogge.mq.ProducerServiceImpl;
import com.rogge.mq.RocketMqConfig;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

    @Resource
    private ProducerServiceImpl producer;

    @Resource
    private RedisClient mRedisClient;

    @ApiOperation(value = "登录接口")
    @GetMapping({"/showHello", "/showHi"})
    public ApiResponse showHello(HttpSession session) {
        mRedisClient.set("aaaaa", "wulei");
        User lUser = new User();
        lUser.setId("111111");
        lUser.setName("rogge");
        mSessionUserInfo.setSessionUser(session, lUser);
        logger.info(lUser.getId());
        return ApiResponse.creatSuccess(mPersonProperties);
    }

    @ApiOperation(value = "发送消息")
    @GetMapping("/push/{id}")
    public ApiResponse sendMsg(@PathVariable("id") String id) {
        logger.info(mRedisClient.get("aaaaa"));
        for (int lI = 0; lI < 10; lI++) {
            User lUser = new User();
            lUser.setId(lI + "");
            lUser.setName("rogge");
            producer.send(RocketMqConfig.TOPIC_TEST1, RocketMqConfig.TAG_ORDER, lUser);
        }
        return ApiResponse.creatSuccess("发送消息成功" + id);
    }

}
