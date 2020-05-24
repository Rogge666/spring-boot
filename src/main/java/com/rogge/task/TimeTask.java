package com.rogge.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * [Description]
 * <p>
 * [How to use]
 * <p>
 * [Tips]
 *
 * @author Created by Rogge on 2017/10/7 0007.
 * @since 1.0.0
 */
@Component
public class TimeTask {
    private final Logger logger = LoggerFactory.getLogger(TimeTask.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

//    @Scheduled(cron="0/10 * *  * * ? ")
    public void reportCurrentTime() {
        logger.info("现在时间：" + dateFormat.format(new Date()));
    }

//    @Scheduled(fixedRate = 10000)
    public void fixedRate() {
        logger.info("fixedRate：" + dateFormat.format(new Date()));
    }
}
