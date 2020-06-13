package ${basePackage}.web;

import ${basePackage}.core.BaseController;
import ${basePackage}.core.ApiResponse;
import ${basePackage}.model.${modelNameUpperCamel};
import ${basePackage}.service.${modelNameUpperCamel}Service;
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
* @author Created by ${author} on ${date}
* @since 1.0.0
*/
@RestController
@RequestMapping("${baseRequestMapping}")
public class ${modelNameUpperCamel}Controller extends BaseController{
    @Resource
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

    @PostMapping
    public ApiResponse add(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}Service.save(${modelNameLowerCamel});
        return ApiResponse.creatSuccess();
    }

    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        ${modelNameLowerCamel}Service.deleteById(id);
        return ApiResponse.creatSuccess();
    }

    @PutMapping
    public ApiResponse update(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}Service.update(${modelNameLowerCamel});
        return ApiResponse.creatSuccess();
    }

    @GetMapping("/{id}")
    public ApiResponse detail(@PathVariable Integer id) {
        ${modelNameUpperCamel} ${modelNameLowerCamel} = ${modelNameLowerCamel}Service.findById(id);
        return ApiResponse.creatSuccess(${modelNameLowerCamel});
    }

    @GetMapping
    public ApiResponse list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<${modelNameUpperCamel}> list = ${modelNameLowerCamel}Service.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ApiResponse.creatSuccess(pageInfo);
    }
}
