package com.netease.vcloud.qa.tag;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.tag.AutoBuildTestService;
import com.netease.vcloud.qa.service.tag.data.TagAutoArgsDTO;
import com.netease.vcloud.qa.service.tag.data.TagAutoArgsRelationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/17 16:40
 */
@RestController
@RequestMapping("/tag/relation")
public class TagAutoRelationController {

    @Autowired
    private AutoBuildTestService autoBuildTestService ;

    /**
     * http://127.0.0.1:8788/g2-client/tag/relation/query?tagId=3
     * @param tagId
     * @return
     */
    @RequestMapping("/query")
    @ResponseBody
    public ResultVO queryAutoRelationInfo(@RequestParam(name = "tagId") long tagId){
        ResultVO resultVO = null;
        List<TagAutoArgsRelationVO> tagAutoArgsRelationVOList = autoBuildTestService.getTagAutoArgsRelationByTagId(tagId) ;
        if (tagAutoArgsRelationVOList!= null){
            resultVO = ResultUtils.buildSuccess(tagAutoArgsRelationVOList) ;
        }else {
            resultVO = ResultUtils.buildFail() ;
        }
        return resultVO ;
    }

    /**
     * http://127.0.0.1:8788/g2-client/tag/relation/add?tagId=3&args=test&operator=luqiuwei@corp.netease.com&condition={"type":"boolean","operate":"equals","value":"true"}
     *  http://127.0.0.1:8788/g2-client/tag/relation/add?tagId=3&args=test&operator=luqiuwei@corp.netease.com&condition=%7B%22type%22%3A%22boolean%22%2C%22operate%22%3A%22equals%22%2C%22value%22%3A%22true%22%7D%0A
     *  @param tagId
     * @param buildArgs
     * @param operator
     * @param condition
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResultVO addAutoRelation(@RequestParam(name = "tagId") long tagId,
                                    @RequestParam("args") String buildArgs ,
                                    @RequestParam("operator") String operator,
                                    @RequestParam("condition") String condition){
        ResultVO resultVO = null;
        TagAutoArgsDTO tagAutoArgsDTO = new TagAutoArgsDTO() ;
        try {
            tagAutoArgsDTO.setTagId(tagId);
            tagAutoArgsDTO.setBuildArgs(buildArgs);
            JSONObject jsonObject = JSONObject.parseObject(condition);
            tagAutoArgsDTO.setArgsCondition(jsonObject);
            tagAutoArgsDTO.setOperator(operator);
        }catch(Exception e){
            resultVO = ResultUtils.buildFail("参数构建异常") ;
            return resultVO ;
        }
        boolean flag = autoBuildTestService.insertTagAutoArgsRelation(tagAutoArgsDTO) ;
        resultVO = ResultUtils.build(flag) ;
        return resultVO ;
    }

    /**
     * http://127.0.0.1:8788/g2-client/tag/relation/update?id=1&tagId=3&args=disable_video&operator=luqiuwei@corp.netease.com&condition=%7B%22type%22%3A%22boolean%22%2C%22operate%22%3A%22equals%22%2C%22value%22%3A%22true%22%7D%0A
     * @param id
     * @param tagId
     * @param buildArgs
     * @param operator
     * @param condition
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public ResultVO updateAutoRelation(@RequestParam(name = "id") long id,
                                       @RequestParam(name = "tagId") long tagId,
                                    @RequestParam("args") String buildArgs ,
                                    @RequestParam("operator") String operator,
                                    @RequestParam("condition") String condition) {
        ResultVO resultVO = null;
        TagAutoArgsDTO tagAutoArgsDTO = new TagAutoArgsDTO();
        try {
            tagAutoArgsDTO.setId(id);
            tagAutoArgsDTO.setTagId(tagId);
            tagAutoArgsDTO.setBuildArgs(buildArgs);
            JSONObject jsonObject = JSONObject.parseObject(condition);
            tagAutoArgsDTO.setArgsCondition(jsonObject);
            tagAutoArgsDTO.setOperator(operator);
        } catch (Exception e) {
            resultVO = ResultUtils.buildFail("参数构建异常");
            return resultVO;
        }
        boolean flag = autoBuildTestService.updateTagAutoArgsRelation(tagAutoArgsDTO);
        resultVO = ResultUtils.build(flag);
        return resultVO;
    }

    /**
     * http://127.0.0.1:8788/g2-client/tag/relation/delete?id=2
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResultVO deleteAutoRelation(@RequestParam("id") Long id) {
        ResultVO resultVO = null;
        boolean flag = autoBuildTestService.deleteTagAutoArgsRelation(id);
        resultVO = ResultUtils.build(flag);
        return resultVO;
    }

}
