package com.zendaimoney.online.web.personal;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zendaimoney.online.entity.common.Area;
import com.zendaimoney.online.entity.personal.PersonalPrivateProprietor;
import com.zendaimoney.online.entity.personal.PersonalUserInfoJob;
import com.zendaimoney.online.entity.personal.PersonalUserInfoPerson;
import com.zendaimoney.online.oii.id5.common.Des2;
import com.zendaimoney.online.service.personal.PersonalManager;
import com.zendaimoney.online.vo.WebAjaxResult;
import com.zendaimoney.online.vo.personal.ModifyPwdVo;
import com.zendaimoney.online.vo.personal.PersonalBaseVO;
import com.zendaimoney.online.vo.personal.PrivateProprietorVo;

@Controller
@RequestMapping(value = "/personal/personal/")
public class PersonalController {
	@Autowired
	PersonalManager personalManager;

	@RequestMapping(value = "personalHead")
	public String showPersonalHead(HttpServletRequest req, Model model) {
		PersonalBaseVO baseVO = personalManager.queryPersonalBase(getUserId(req));
		List shengList = personalManager.getAreaList("1");// 省级列表(含直辖市)
		List shiList;// 市级列表 (含直辖市下设区)
		if (!StringUtils.isEmpty(baseVO.getHometownArea())) {
			shiList = personalManager.getAreaList(baseVO.getHometownArea());
		} else {
			// 默认选中北京
			baseVO.setHometownArea("2");
			shiList = personalManager.getAreaList("2");
		}
		model.addAttribute("baseVO", baseVO);
		model.addAttribute("shengList", shengList);
		model.addAttribute("shiList", shiList);
		return "personal/personalHead";
	}

	@RequestMapping(value = "personalBase")
	public String showPersonalBase(HttpServletRequest req, Model model) {
		PersonalBaseVO baseVO = personalManager.queryPersonalBase(getUserId(req));
		List shengList = personalManager.getAreaList("1");// 省级列表(含直辖市)
		List shiList;// 市级列表 (含直辖市下设区)
		if (!StringUtils.isEmpty(baseVO.getHometownArea())) {
			shiList = personalManager.getAreaList(baseVO.getHometownArea());
		} else {
			// 默认选中北京
			baseVO.setHometownArea("2");
			shiList = personalManager.getAreaList("2");
		}
		model.addAttribute("baseVO", baseVO);
		model.addAttribute("shengList", shengList);
		model.addAttribute("shiList", shiList);
		return "personal/personalBase";
	}

	@RequestMapping(value = "personalFinance")
	public String showPersonalFinance(HttpServletRequest req, Model model) {
		BigDecimal userId = getUserId(req);
		PersonalUserInfoPerson personal = personalManager.queryPersonalUserInfoPerson(userId);
		model.addAttribute("entity", personal);
		return "personal/personalFinance";
	}

	@RequestMapping(value = "savePersonalBase")
	public String savePersonalBase(@ModelAttribute("baseVO") PersonalBaseVO baseVO, Model model, HttpServletRequest req) {
		String sessionToken = (String) req.getSession().getAttribute("token");
		// session令牌解密
		sessionToken = Des2.decodeValue("LIE33LEI343ZDIKFJ", sessionToken);
		String token = Des2.decodeValue("LIE33LEI343ZDIKFJ", baseVO.getToken());
		if (sessionToken.equals(token)) {
			personalManager.savePersonalBase(baseVO, getUserId(req));
			model.addAttribute("baseVO", baseVO);
			model.addAttribute("show", "true");
			List shengList = personalManager.getAreaList("1");// 省级列表(含直辖市)
			List shiList;// 市级列表 (含直辖市下设区)
			if (!StringUtils.isEmpty(baseVO.getHometownArea())) {
				shiList = personalManager.getAreaList(baseVO.getHometownArea());
			} else {
				// 默认选中北京
				shiList = personalManager.getAreaList("2");
			}
			model.addAttribute("shengList", shengList);
			model.addAttribute("shiList", shiList);
		}
		return "personal/personalBase";
	}

	@RequestMapping(value = "savePersonalFinance")
	public String savePersonalFinance(@ModelAttribute("entity") PersonalUserInfoPerson entity, Model model, HttpServletRequest req) {
		personalManager.savePersonalFinance(entity, getUserId(req));
		model.addAttribute("show", "true");
		return "personal/personalFinance";
	}

	@RequestMapping(value = "saveIcon")
	public void saveIcon(HttpServletRequest request) {
		personalManager.savePersonalBase(request);
	}

	// 显示私营业主页面
	@RequestMapping(value = "showPersonalPrivate")
	public String showPersonalPrivate(Model model, HttpServletRequest req) {
		PrivateProprietorVo pp = personalManager.showPersonalPrivate(getUserId(req));
		model.addAttribute("pp", pp);
		return "personal/personalPrivate";
	}

	@ResponseBody
	@RequestMapping(value = "queryArea")
	public List<Area> queryArea(@ModelAttribute("code") String code) {
		return (List<Area>) personalManager.getAreaList(code);
	}

	// 私营业主资料保存
	@RequestMapping(value = "saveSyyz")
	public String saveSyyz(@ModelAttribute("privateProprietor") PersonalPrivateProprietor privateProprietor, Model model, HttpServletRequest req) {
		personalManager.saveSyyz(privateProprietor, req);
		PersonalPrivateProprietor pp = personalManager.showPersonalPrivate(getUserId(req));
		model.addAttribute("pp", pp);
		model.addAttribute("show", "true");
		return "personal/personalPrivate";
	}

	// show工作信息
	@RequestMapping(value = "showJob")
	public String showJob(Model model, HttpServletRequest req) {
		PersonalUserInfoJob job = personalManager.showJob(getUserId(req));
		List shengList = personalManager.getAreaList("1");// 省级列表(含直辖市)
		List shiList;// 市级列表 (含直辖市下设区)
		if (null != job) {
			if (null != job.getJobProvince()) {
				shiList = personalManager.getAreaList(String.valueOf(job.getJobProvince()));
			} else {
				// 默认选中北京
				job.setJobProvince(new BigDecimal(2));
				shiList = personalManager.getAreaList("2");
			}
			model.addAttribute("job", job);
		} else {
			// 默认选中北京
			PersonalUserInfoJob userJob = new PersonalUserInfoJob();
			userJob.setUserId(getUserId(req));
			userJob.setJobProvince(new BigDecimal(2));
			model.addAttribute("job", userJob);
			shiList = personalManager.getAreaList("2");
		}

		// model.addAttribute("baseVO", baseVO);
		model.addAttribute("shengList", shengList);
		model.addAttribute("shiList", shiList);

		return "personal/personalJob";
	}

	// 工作信息
	@RequestMapping(value = "saveJobInfo")
	public String saveJobInfo(@ModelAttribute("userInfoJob") PersonalUserInfoJob userInfoJob, Model model, HttpServletRequest req) {
		personalManager.saveJobInfo(userInfoJob, req);
		PersonalUserInfoJob job = personalManager.showJob(getUserId(req));
		List shengList = personalManager.getAreaList("1");// 省级列表(含直辖市)
		List shiList;// 市级列表 (含直辖市下设区)
		if (null != job) {
			if (null != job.getJobProvince()) {
				shiList = personalManager.getAreaList(String.valueOf(job.getJobProvince()));
			} else {
				// 默认选中北京
				shiList = personalManager.getAreaList("2");
			}
		} else {
			// 默认选中北京
			shiList = personalManager.getAreaList("2");
		}

		// model.addAttribute("baseVO", baseVO);
		model.addAttribute("shengList", shengList);
		model.addAttribute("shiList", shiList);
		model.addAttribute("job", job);
		return "personal/personalJob";
	}

	// 显示修改密码
	@RequestMapping(value = "showChangePassword")
	public String showChangePassword() {
		return "personal/modifyPassWord";
	}

	// 保存新密码
	@RequestMapping(value = "saveNewPwd")
	public String saveNewPwd(@ModelAttribute("modifyPwdVo") ModifyPwdVo modifyPwdVo, Model model, HttpServletRequest req) {
		String flg = personalManager.savePassword(modifyPwdVo, req);
		if (flg.equals("same")) {
			model.addAttribute("show", "true");
			model.addAttribute("modifyPwdVo", modifyPwdVo);
		} else {
			model.addAttribute("show", "pwdFalse");
		}
		return "personal/modifyPassWord";
	}

	// 手机绑定
	@RequestMapping(value = "personalPhoneNoBind")
	private String phoneBind(HttpServletRequest req, HttpServletRequest rep) {
		return "personal/personalPhoneNoBind";
	}

	private BigDecimal getUserId(HttpServletRequest req) {
		HttpSession session = req.getSession();
		BigDecimal userId = ((BigDecimal) session.getAttribute("curr_login_user_id"));
		return userId;
	}

	@RequestMapping(value = "notifyMsgSet")
	public String notifyMsgSet() {
		return "personal/notifySet";
	}

	@RequestMapping("saveUserMessageSet")
	@ResponseBody
	public boolean saveUserMessageSet(String checkId, HttpServletRequest request) {
		try {
			personalManager.saveUserMessageSet(checkId, request);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@RequestMapping("initNotifySet")
	@ResponseBody
	public String initNotifySet(HttpServletRequest request) {
		return personalManager.initNotifySet(request);
	}

	// 手机绑定
	@RequestMapping(value = "showPhoneBind")
	private String phoneBind() {
		return "personal/personalPhoneNoBind";
	}

	// show固定资产信息
	@RequestMapping(value = "showFixedAssets")
	public String showFixed(Model model, HttpServletRequest req) {
		PersonalUserInfoPerson fixedAssets = personalManager.showFixed(getUserId(req));
		model.addAttribute("fixedAssets", fixedAssets);
		return "personal/personalFixed";
	}

	// 固定资产信息
	@ResponseBody
	@RequestMapping(value = "saveFixedAssets")
	public WebAjaxResult saveFixed(@ModelAttribute("userInfoPerson") PersonalUserInfoPerson userInfoPerson, HttpServletRequest req) {
		personalManager.saveNewFixedAssets(userInfoPerson, req);
		return new WebAjaxResult();
	}

	// show教育职称信息
	@RequestMapping(value = "showEdu")
	public String showEduInfo(Model model, HttpServletRequest req) {
		PersonalUserInfoPerson eduInfo = personalManager.showFixed(getUserId(req));
		model.addAttribute("eduInfo", eduInfo);
		return "personal/personalEdu";
	}

	// save教育职称信息
	@ResponseBody
	@RequestMapping(value = "saveEduInfo")
	public WebAjaxResult saveEduInfo(@ModelAttribute("userInfoPerson") PersonalUserInfoPerson userInfoPerson, HttpServletRequest req) {
		personalManager.saveNewEduInfo(userInfoPerson, req);
		return new WebAjaxResult();
	}

	@ExceptionHandler
	@ResponseBody
	public WebAjaxResult handleException(RuntimeException e) {
		e.printStackTrace();
		WebAjaxResult ajaxResult = new WebAjaxResult();
		ajaxResult.setSuccess(Boolean.FALSE);
		ajaxResult.setMsg(e.getMessage());
		return ajaxResult;
	}
}
