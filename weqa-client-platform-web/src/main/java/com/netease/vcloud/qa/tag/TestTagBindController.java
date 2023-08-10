package com.netease.vcloud.qa.tag;

import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.tag.AutoTestTagException;
import com.netease.vcloud.qa.service.tag.AutoTestTagService;
import com.netease.vcloud.qa.service.tag.data.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.*;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/10 14:04
 */
@RestController
@RequestMapping("/tag/bind")
public class TestTagBindController {

    @Autowired
    private AutoTestTagService autoTestTagService ;

    /**
     * http://127.0.0.1:8788/g2-client/tag/bind/test/add?testId=1&tagId=1&operator=luqiuwei@corp.netease.com
     * @param testId
     * @param tagId
     * @param operator
     * @return
     */
    @RequestMapping("/test/add")
    @ResponseBody
    public ResultVO testAddTag(@RequestParam(name = "testId") long testId ,
                               @RequestParam(name = "tagId") long tagId ,
                               @RequestParam(name = "operator") String operator){
        ResultVO resultVO = null ;
        try {
            boolean addResult = autoTestTagService.testCaseAddTag(testId, tagId, operator);
            resultVO = ResultUtils.build(addResult) ;
        }catch (AutoTestTagException e){
            resultVO = ResultUtils.buildFail(e.getExceptionInfo()) ;
        }
        return resultVO ;
    }

    /**
     * http://127.0.0.1:8788/g2-client/tag/bind/test/delete?testId=1&tagId=1
     * @param testId
     * @param tagId
     * @return
     */
    @RequestMapping("/test/delete")
    @ResponseBody
    public ResultVO testDeleteTag(@RequestParam(name = "testId") long testId ,@RequestParam(name = "tagId")long tagId){
        ResultVO resultVO = null ;
        try {
            boolean deleteFlag = autoTestTagService.testCaseDeleteTag(testId, tagId);
            resultVO = ResultUtils.build(deleteFlag) ;
        }catch (AutoTestTagException e){
            resultVO = ResultUtils.buildFail(e.getExceptionInfo());
        }
        return resultVO ;

    }

    /**
     * http://127.0.0.1:8788/g2-client/tag/bind/suit/add?suitId=1&tagId=1&operator=luqiuwei@corp.netease.com
     * @param suitId
     * @param tagId
     * @param operator
     * @return
     */
    @RequestMapping("/suit/add")
    @ResponseBody
    public ResultVO suitAddTag(@RequestParam(name = "suitId") long suitId ,
                               @RequestParam(name = "tagId") long tagId ,
                               @RequestParam(name = "operator") String operator){
        ResultVO resultVO = null ;
        try {
            boolean addResult = autoTestTagService.testSuitAddTag(suitId, tagId, operator);
            resultVO = ResultUtils.build(addResult) ;
        }catch (AutoTestTagException e){
            resultVO = ResultUtils.buildFail(e.getExceptionInfo()) ;
        }
        return resultVO ;
    }

    /**
     * http://127.0.0.1:8788/g2-client/tag/bind/suit/delete?suitId=1&tagId=1
     * @param suitId
     * @param tagId
     * @return
     */
    @RequestMapping("/suit/delete")
    @ResponseBody
    public ResultVO suitDeleteTag(@RequestParam(name = "suitId") long suitId ,
                                  @RequestParam(name = "tagId") long tagId ){
        ResultVO resultVO = null ;
        try{
            boolean deleteFlag = autoTestTagService.testSuitDeleteTag(suitId, tagId);
            resultVO = ResultUtils.build(deleteFlag) ;
        }catch (AutoTestTagException e){
            resultVO = ResultUtils.buildFail(e.getExceptionInfo()) ;
        }
        return resultVO ;
    }

    /**
     *http://127.0.0.1:8788/g2-client/tag/bind/tag/query?tag=1
     * @param tagId
     * @return
     */
    @RequestMapping("/tag/query")
    @ResponseBody
    public ResultVO queryTagBindId(@RequestParam(name = "tag") long tagId){
        ResultVO resultVO = null ;
        try{
            Set<Long> tags = new HashSet<Long>() ;
            tags.add(tagId) ;
            Map<TagRelationType , List<Long>> tagRelationTypeListMap = autoTestTagService.getTagsRelation(tags) ;
            if (tagRelationTypeListMap == null){
                resultVO = ResultUtils.buildFail() ;
            }else {
                resultVO = ResultUtils.buildSuccess(tagRelationTypeListMap) ;
            }
        }catch (AutoTestTagException e) {
            resultVO = ResultUtils.buildFail(e.getExceptionInfo()) ;
        }
        return resultVO ;
    }

    /**
     * http://127.0.0.1:8788/g2-client/tag/bind/relation/query?type=1&id=1
     * @param type
     * @param id
     * @return
     */
    @RequestMapping("/relation/query")
    @ResponseBody
    public ResultVO queryTaskTag(@RequestParam("type") byte type , @RequestParam("id") long id){
        ResultVO resultVO = null ;
        try{
           TagRelationType tagRelationType = TagRelationType.getTagRelationTypeByCode(type) ;
           Set<Long> idSet = new HashSet<>() ;
           idSet.add(id) ;
           Map<Long,List<TagVO>> tagVOListMap =  autoTestTagService.getTagListByRelation(tagRelationType,idSet) ;
           resultVO = ResultUtils.buildSuccess(tagVOListMap);
        }catch (AutoTestTagException e) {
            resultVO = ResultUtils.buildFail(e.getExceptionInfo()) ;
        }
        return resultVO ;
    }
}
