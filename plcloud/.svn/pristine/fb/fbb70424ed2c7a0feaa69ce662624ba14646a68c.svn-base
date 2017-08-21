package com.routon.pmax.admin.privilege.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.routon.pmax.admin.privilege.model.TreeBean;
import com.routon.pmax.admin.privilege.service.GroupService;
import com.routon.pmax.admin.privilege.service.UserService;
import com.routon.pmax.common.PmaxException;
import com.routon.pmax.common.UserProfile;
import com.routon.pmax.common.constant.CVal;
import com.routon.pmax.common.model.Group;
import com.routon.pmax.common.utils.JsonMsgBean;

@Controller
@SessionAttributes(value = { "userPrivilege", "userProfile" })
public class GroupController {

	private Logger logger = LoggerFactory.getLogger(GroupController.class);

	@Resource(name = "userServiceImpl")
	private UserService userService;
	
	@Resource(name = "groupServiceImpl")
	private GroupService groupService;
	
	private final String RMPATH = "/group/";

	@RequestMapping(value = RMPATH + "list")
	public String list(HttpServletRequest request, Group queryCondition,
			@ModelAttribute("userProfile")
			UserProfile user, Model model, HttpSession session) {

		Long loginUserId = user.getCurrentUserId();
		List<TreeBean> groupTreeBeans = groupService.getGroupTreeByUserId(loginUserId, queryCondition, null);
		
		if(StringUtils.isNotBlank(queryCondition.getName())){
			model.addAttribute("name", queryCondition.getName());
		}
		
		model.addAttribute("groupTreeBeans", JSONArray.fromObject(groupTreeBeans).toString());
		return "group/list";
	}
	
	
	@RequestMapping(value = "unauthen/groups/list")
	public @ResponseBody List<TreeBean> grouplist(HttpServletRequest request, Group queryCondition,
			@ModelAttribute("userProfile")
			UserProfile user, Model model, HttpSession session,Boolean onlyleafcheck) {

		Long loginUserId = user.getCurrentUserId();
		List<TreeBean> groupTreeBeans = groupService.getGroupTreeByUserId(loginUserId, queryCondition, null,onlyleafcheck,false);
		
		
		return groupTreeBeans;
	}
	
	
	/**
	 * 新建分组
	 * 
	 * @param Group
	 *            group
	 * @param optUser
	 *            当前操作用户
	 * @return ControllerResult 处理结果
	 */
	@RequestMapping(value = "group/save", method = RequestMethod.POST)
	public String addGroup(Group group, @ModelAttribute("userProfile") UserProfile optUser, Model model) {
		JsonMsgBean jsonMsg = null;
		do {
			try {
				Long id = null;
				if(group.getId()>0){
					id = groupService.edit(group, optUser);
				}else {
					id = groupService.add(group, optUser);
				}
				
				if(id>0){
					group.setId(id);
					jsonMsg = new JsonMsgBean(0, CVal.Success, JSONObject.fromObject(group).toString());			
				}
				else if(id==-2){
					jsonMsg = new JsonMsgBean(0, CVal.Fail, "分组名已经存在!");			
				}
				else {
					jsonMsg = new JsonMsgBean(0, CVal.Fail, "保存分组失败!");
				}
	
			} 
			catch (PmaxException e) {
				
				jsonMsg = new JsonMsgBean(0, CVal.Fail, e.getMessage());
				
			}
			catch (Exception e) {
				logger.error("保存失败，请稍候重试!", e);
				jsonMsg = new JsonMsgBean(0, CVal.Exception, "保存失败，请稍候重试!");
				
			}
		}
		while(false);
		
		model.addAttribute("jsonMsg", jsonMsg);	
		return "common/jsonTextHtml";	
	}
	
	/**
	 * 删除分组
	 * 
	 * @param id
	 *            
	 * @param optUser
	 *            当前操作用户
	 * @return ControllerResult 处理结果
	 */
	@RequestMapping(value = "group/delete", method = RequestMethod.POST)
	public String delGroup(String id, @ModelAttribute("userProfile") UserProfile optUser, Model model) {
		JsonMsgBean jsonMsg = null;
		try {
			int i = groupService.delete(id, optUser);

			if (i>0) {
				
				jsonMsg = new JsonMsgBean(0, CVal.Success, "");		
				
			} else {
				jsonMsg = new JsonMsgBean(0, CVal.Fail, "删除失败!");
			}

		}
		catch (PmaxException e) {
			
			jsonMsg = new JsonMsgBean(0, CVal.Fail, e.getMessage());
			
		}
		catch (Exception e) {
			logger.error("删除失败，请稍候重试!", e);
			jsonMsg = new JsonMsgBean(0, CVal.Exception, "删除失败，请稍候重试!");
			
		}
		
		model.addAttribute("jsonMsg", jsonMsg);	
		return "common/jsonTextHtml";	
		
	}
}
