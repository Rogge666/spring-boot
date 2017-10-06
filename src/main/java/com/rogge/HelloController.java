package com.rogge;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
public class HelloController {

    @Resource
    private PersonProperties mPersonProperties;

    @GetMapping({"/showHello","/showHi"})
    public String showHello() {
        return "Hello World" + mPersonProperties.toString();
    }

}
