package com.netease.vcloud.qa.tag;

import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.auto.AutoTestRunException;
import com.netease.vcloud.qa.service.tag.AutoTestTagException;
import com.netease.vcloud.qa.service.tag.TagManagerService;
import com.netease.vcloud.qa.service.tag.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/9 13:53
 */
@RestController
@RequestMapping("/tag/manager")
public class TagManagerController {
    @Autowired
    private TagManagerService tagManagerService;

    /**
     * http://127.0.0.1:8788/g2-client/tag/manager/add?name=test&operator=luqiuwei@corp.netease.com
     * @param tagName
     * @param tagType
     * @param operator
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResultVO addTag(@RequestParam("name") String tagName ,
                           @RequestParam(name = "type",required = false) String tagType,
                           @RequestParam("operator") String operator) {
        ResultVO resultVO = null ;
        TagDTO tagDTO = new TagDTO();
        tagDTO.setName(tagName);
        tagDTO.setType(tagType);
        tagDTO.setCreator(operator);
        try {
            Long tagID = tagManagerService.addTag(tagDTO);
            if (tagID != null){
                resultVO = ResultUtils.buildSuccess(tagID);
            }else{
                resultVO = ResultUtils.buildFail() ;
            }
        }catch (AutoTestTagException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }

    /**
     * http://127.0.0.1:8788/g2-client/tag/manager/update?id=1&name=test&type=test_scene
     * @param id
     * @param tagName
     * @param tagType
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public ResultVO updateTag(@RequestParam("id") long id ,
                              @RequestParam("name") String tagName ,
                              @RequestParam(name = "type",required = false) String tagType) {
        ResultVO resultVO = null ;
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(id);
        tagDTO.setName(tagName);
        tagDTO.setType(tagType);
        try {
            boolean flag = tagManagerService.updateTag(tagDTO);
            if (flag){
                resultVO = ResultUtils.buildSuccess();
            }else{
                resultVO = ResultUtils.buildFail() ;
            }
        }catch (AutoTestTagException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }

    /**
     * http://127.0.0.1:8788/g2-client/tag/manager/delete?id=2
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResultVO deleteTag(@RequestParam("id") long id) {
        boolean flag = tagManagerService.deleteTag(id) ;
        return ResultUtils.build(flag);
    }

    /**
     * http://127.0.0.1:8788/g2-client/tag/manager/query?key=es
     * @param queryKey
     * @return
     */
    @RequestMapping("/query")
    @ResponseBody
    public ResultVO queryTag(@RequestParam("key") String queryKey) {
        ResultVO resultVO = null ;
        try {
            List<TagVO> tagVOList = tagManagerService.queryTagListByKey(queryKey);
            if(tagVOList!=null) {
                resultVO = ResultUtils.buildSuccess(tagVOList);
            }else{
                resultVO = ResultUtils.buildFail() ;
            }
        }catch (AutoTestTagException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }

    /**
     * http://127.0.0.1:8788/g2-client/tag/manager/type/query?type=default
     * @param type
     * @return
     */
    @RequestMapping("/type/query")
    @ResponseBody
    public  ResultVO queryTagByType(@RequestParam("type") String type) {
        ResultVO resultVO = null ;
        try {
            List<TagVO> tagVOList = tagManagerService.getTagListByType(type);
            if (tagVOList!= null){
                resultVO = ResultUtils.buildSuccess(tagVOList) ;
            }else{
                resultVO = ResultUtils.buildFail() ;
            }
        }catch (AutoTestTagException e) {
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }


    /**
     * http://127.0.0.1:8788/g2-client/tag/manager/type/get
     * @return
     */
    @RequestMapping("/type/get")
    @ResponseBody
    public ResultVO getAllTagType(){
        ResultVO resultVO = null ;
        List<TagTypeVO> tagTypeVOList = tagManagerService.getTypeList() ;
        resultVO = ResultUtils.buildSuccess(tagTypeVOList) ;
        return resultVO ;
    }

    /**
     * http://127.0.0.1:8788/g2-client/tag/manager/tag/getAll
     * @return
     */
    @RequestMapping("/tag/getAll")
    @ResponseBody
    public ResultVO queryAllTag(){
        ResultVO resultVO = null ;
        List<TagSelectVO> tagSelectVOList = tagManagerService.getTageSelect() ;
        if (tagSelectVOList!= null){
            resultVO = ResultUtils.buildSuccess(tagSelectVOList) ;
        }else {
            resultVO = ResultUtils.buildFail() ;
        }
        return resultVO ;
    }

//    @RequestMapping("/tag/query")
//    @ResponseBody
//    public ResultVO queryLeafTag(@RequestParam("key") String queryKey){
//        ResultVO resultVO = null ;
//        List<TagSelectVO> tagSelectVOList = tagManagerService.getTageLeafSelect(queryKey) ;
//        if (tagSelectVOList!= null){
//            resultVO = ResultUtils.buildSuccess(tagSelectVOList) ;
//        }else {
//            resultVO = ResultUtils.buildFail() ;
//        }
//        return resultVO ;
//    }

    /**
     * http://127.0.0.1:8788/g2-client/tag/manager/tag/detail?id=1
     * @param id
     * @return
     */
    @RequestMapping("/tag/detail")
    @ResponseBody
    public ResultVO getTagDetailInfo(long  id){
        ResultVO resultVO = null ;
        try{
            TagDetailVO tagDetailVO = tagManagerService.getTagDetail(id) ;
            if (tagDetailVO != null){
                resultVO = ResultUtils.buildSuccess(tagDetailVO) ;
            }else {
                resultVO = ResultUtils.buildFail() ;
            }
        }catch (AutoTestTagException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }

}
