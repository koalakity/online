package com.zendaimoney.online.vo.index;

import java.util.List;

import com.zendaimoney.online.entity.notice.IndexNotice;

public class IndexPageVO {

	List<IndexNotice> footerNoticeList;
	List<IndexNotice> websiteList;
	List<IndexNotice> industryNews;
	List<IndexNotice> mediaReports;
	List<IndexNotice> microFinancialStory;
	public List<IndexNotice> getFooterNoticeList() {
		return footerNoticeList;
	}
	public void setFooterNoticeList(List<IndexNotice> footerNoticeList) {
		this.footerNoticeList = footerNoticeList;
	}
	public List<IndexNotice> getWebsiteList() {
		return websiteList;
	}
	public void setWebsiteList(List<IndexNotice> websiteList) {
		this.websiteList = websiteList;
	}
	public List<IndexNotice> getIndustryNews() {
		return industryNews;
	}
	public void setIndustryNews(List<IndexNotice> industryNews) {
		this.industryNews = industryNews;
	}
	public List<IndexNotice> getMediaReports() {
		return mediaReports;
	}
	public void setMediaReports(List<IndexNotice> mediaReports) {
		this.mediaReports = mediaReports;
	}
	public List<IndexNotice> getMicroFinancialStory() {
		return microFinancialStory;
	}
	public void setMicroFinancialStory(List<IndexNotice> microFinancialStory) {
		this.microFinancialStory = microFinancialStory;
	}
}
