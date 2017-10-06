package com.rogge.web;
import com.rogge.core.Result;
import com.rogge.core.ResultGenerator;
import com.rogge.model.CommandContent;
import com.rogge.model.vo.CommentVO;
import com.rogge.service.CommandContentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

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
@RestController
@RequestMapping("/command/content")
public class CommandContentController {
    @Resource
    private CommandContentService commandContentService;

    @PostMapping("/add")
    public Result add(CommandContent commandContent) {
        commandContentService.save(commandContent);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        commandContentService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(CommandContent commandContent) {
        commandContentService.update(commandContent);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        CommandContent commandContent = commandContentService.findById(id);
        return ResultGenerator.genSuccessResult(commandContent);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<CommandContent> list = commandContentService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @GetMapping("/getLeftJoin")
    public Result getLeftJoin() {
        List<CommentVO> list = commandContentService.getCommandLeftJoin();
        return ResultGenerator.genSuccessResult(list);
    }
}
