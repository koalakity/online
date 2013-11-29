package com.zendaimoney.online.web.footerDetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zendaimoney.online.entity.notice.IndexNotice;
import com.zendaimoney.online.service.IndexServiceBean;
import com.zendaimoney.online.vo.index.IndexLoanListVO;
import com.zendaimoney.online.vo.index.IndexLoanVO;

/**
 * 
 * @author yijc
 * 
 */
@Controller
@RequestMapping(value = "/footerDetail/footerDetail/")
public class FooterDetailController {
	private static final String SORT_1 = "/online/footerDetail/footerDetail/showTwoGradePage?id=2&category=首页&sort=使用帮助&c_sort=常见问题";
	private static final String SORT_2 = "/online/footerDetail/footerDetail/showTwoGradePage?id=7&category=首页&sort=安全保障&c_sort=风险金代偿";
	private static final String SORT_3 = "/online/footerDetail/footerDetail/showTwoGradePage?id=11&category=首页&sort=资费说明&c_sort=收费标准";
	private static final String SORT_4 = "/online/footerDetail/footerDetail/showTwoGradePage?id=14&category=首页&sort=关于我们&c_sort=公司简介";
	private static final String SORT_5 = "/online/footerDetail/footerDetail/showTwoGradePage?id=17&category=首页&sort=联系我们&c_sort=证大微博";

	@Autowired
	private IndexServiceBean indexservice;

	/**
	 * 设置页面的导航信息 把一，二，三级分类的名称设置到请求对象返回界面，在导航栏位显示
	 * 且返回每个分类名对应的URL（二级分类的URL默认为二级下对应的第一个三级分类） 2012-11-12 上午11:58:46 by HuYaHui
	 * 
	 * @param req
	 */
	private void setGuide(HttpServletRequest req, @RequestParam("id") String id) {

		Map<String, String> sortUrlParam = new HashMap<String, String>();
		// 使用帮助
		sortUrlParam.put("1", SORT_1);
		sortUrlParam.put("2", SORT_1);
		sortUrlParam.put("3", SORT_1);
		sortUrlParam.put("4", SORT_1);
		sortUrlParam.put("5", SORT_1);
		sortUrlParam.put("6", SORT_1);

		// 安全保障
		sortUrlParam.put("7", SORT_2);
		sortUrlParam.put("8", SORT_2);
		sortUrlParam.put("9", SORT_2);
		sortUrlParam.put("10", SORT_2);

		// 资费说明
		sortUrlParam.put("11", SORT_3);
		sortUrlParam.put("12", SORT_3);
		sortUrlParam.put("13", SORT_3);

		// 关于我们
		sortUrlParam.put("14", SORT_4);
		sortUrlParam.put("15", SORT_4);
		sortUrlParam.put("16", SORT_4);

		// 联系我们
		sortUrlParam.put("17", SORT_5);

		// 分类(首页)
		String category = req.getParameter("category");
		req.setAttribute("category", category == null ? "首页" : category);
		// 二级栏位(使用帮助)
		String sort = req.getParameter("sort");
		req.setAttribute("sort", sort == null ? "使用帮助" : sort);
		req.setAttribute("sort_url", sortUrlParam.get(id));
		// 二级子栏位(平台原理)
		String c_sort = req.getParameter("c_sort");
		req.setAttribute("c_sort", c_sort == null ? "平台原理" : c_sort);
	}

	@RequestMapping(value = "showTwoGradePage")
	public String show(@RequestParam("id") String id, HttpServletRequest req, HttpServletResponse rep, Model model) {
		// 设置导航信息(一级>二级>三级) 2012-11-12
		setGuide(req, id);
		model.addAttribute("twoGradePage", indexservice.getTwoGradePageA(id));
		return "/footerDetail/twoGradePage";
	}

	@RequestMapping(value = "loadTwoGradePage")
	public String load(@RequestParam("id") String id, HttpServletRequest req, HttpServletResponse rep, Model model) {
		// 设置导航信息(一级>二级>三级) 2012-11-12
		setGuide(req, id);
		model.addAttribute("twoGradePage", indexservice.getTwoGradePageA(id));
		return "/footerDetail/footerDetailChild";
	}

	@RequestMapping(value = "loadLoanList")
	public String getLoanList(@RequestParam("status") String status, HttpServletRequest req, HttpServletResponse rep, Model model) {

		String redirectUrl = "";
		if ("1".equals(status)) {
			model.addAttribute("inLoanList", indexservice.getLoanInfoList(status));
			redirectUrl = "/footerDetail/inLoanPage";
		} else {
			IndexLoanListVO loanListVo = indexservice.getLoanInfoList(status);
			List<IndexLoanVO> loanList = loanListVo.getIndexLoanList();
			// loanList.addAll(indexservice.getLoanInfoList("2").getIndexLoanList());
			loanList.addAll(indexservice.getLoanInfoList("4").getIndexLoanList());
			loanList.addAll(indexservice.getLoanInfoList("5").getIndexLoanList());
			loanList.addAll(indexservice.getLoanInfoList("6").getIndexLoanList());
			loanList.addAll(indexservice.getLoanInfoList("7").getIndexLoanList());
			loanListVo.setIndexLoanList(loanList);
			model.addAttribute("inLoanList", loanListVo);
			redirectUrl = "/footerDetail/successLoanPage";
		}
		return redirectUrl;
	}

	@RequestMapping(value = "showWordListPage")
	public String showWordPage(@RequestParam("id") String id, HttpServletRequest req, HttpServletResponse rep, Model model) {
		IndexNotice indexNotice = indexservice.getTwoGradePageB(id);
		model.addAttribute("wordPage", indexNotice);
		if (indexNotice.getType() == 19) {
			model.addAttribute("titleName", "最新公告");
		} else if (indexNotice.getType() == 20) {
			model.addAttribute("titleName", "行业新闻");
		} else if (indexNotice.getType() == 21) {
			model.addAttribute("titleName", "媒体报道");
		}
		return "/footerDetail/indexWordListPage";
	}

	@RequestMapping(value = "showStoryListPage")
	public String showStoryPage(@RequestParam("id") String id, HttpServletRequest req, HttpServletResponse rep, Model model) {
		model.addAttribute("storyPage", indexservice.getTwoGradePageB(id));
		return "/footerDetail/indexStoryListpage";
	}

	@RequestMapping(value = "showStoryList")
	public String showStory(@RequestParam("type") String type, HttpServletRequest req, HttpServletResponse rep, Model model) {
		model.addAttribute("storyList", indexservice.getNoticeList(type));
		return "/footerDetail/indexStoryList";
	}

	@RequestMapping(value = "showWordList")
	public String showWord(@RequestParam("type") String type, HttpServletRequest req, HttpServletResponse rep, Model model) {
		model.addAttribute("wordList", indexservice.getNoticeList(type));
		if ("20".equals(type)) {
			model.addAttribute("titleName", "行业新闻");
		} else if ("21".equals(type)) {
			model.addAttribute("titleName", "媒体报道");
		} else if ("19".equals(type)) {
			model.addAttribute("titleName", "最新公告");
		}
		return "/footerDetail/indexWordList";
	}

	@RequestMapping(value = "showBbs")
	public String showBbs() {
		return "/bbs/bbs";
	}

}
