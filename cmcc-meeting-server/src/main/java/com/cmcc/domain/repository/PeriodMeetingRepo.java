package com.cmcc.domain.repository;

import com.cmcc.domain.model.PeriodMeeting;
import com.cmcc.domain.model.PeriodQuery;

import java.util.List;

public interface PeriodMeetingRepo {


	/**
	 * 保存周期会议
	 * @return
	 * @throws Exception
	 */
	public void savePeriodMeeting(PeriodMeeting periodMeeting) throws Exception;

	/**
	 * 查询周期列表
	 * @return
	 * @throws Exception
	 */
	public List<PeriodMeeting> getPeriodList(PeriodQuery periodQuery) throws Exception;

	/**
	 * 查询周期列表总数
	 * @return
	 * @throws Exception
	 */
	public int getPeriodListCount(PeriodQuery periodQuery) throws Exception;

	/**
	 * 查询周期详情
	 * @return
	 * @throws Exception
	 */
	public PeriodMeeting getPeriodInfo(PeriodQuery periodQuery) throws Exception;

	/**
	 * 查询周期详情
	 * @return
	 * @throws Exception
	 */
	public PeriodMeeting checkPeriodInfo(PeriodQuery periodQuery) throws Exception;

	/**
	 * 编辑周期会议
	 * @return
	 * @throws Exception
	 */
	public void editPeriodMeeting(PeriodMeeting periodMeeting) throws Exception;

	/**
	 * 删除周期
	 * @return
	 * @throws Exception
	 */
	public void deletePeriod(String uuid) throws Exception;

}

