package com.cmcc.infrastructure.repository.impl;

import com.cmcc.domain.model.PeriodMeeting;
import com.cmcc.domain.model.PeriodQuery;
import com.cmcc.domain.repository.PeriodMeetingRepo;
import com.cmcc.infrastructure.database.mapper.PeriodMeetingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "periodMeetingRepo")
public class PeriodMeetingRepoImpl implements PeriodMeetingRepo {

	@Autowired
	private PeriodMeetingMapper periodMeetingMapper;
	

	/**
	 * 保存周期信息
	 * @return
	 * @throws Exception
	 */
	@Override
	public void savePeriodMeeting(PeriodMeeting periodMeeting) throws Exception {
		periodMeetingMapper.savePeriodMeeting(periodMeeting);
	}

	/**
	 * 获取周期列表
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<PeriodMeeting> getPeriodList(PeriodQuery periodQuery) throws Exception {
		return periodMeetingMapper.getPeriodList(periodQuery);
	}

	/**
	 * 获取周期列表总数
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getPeriodListCount(PeriodQuery periodQuery) throws Exception {
		return periodMeetingMapper.getPeriodListCount(periodQuery);
	}

	/**
	 * 获取周期详情
	 * @return
	 * @throws Exception
	 */
	@Override
	public PeriodMeeting getPeriodInfo(PeriodQuery periodQuery) throws Exception {
		return periodMeetingMapper.getPeriodInfo(periodQuery);
	}

	/**
	 * 获取周期详情
	 * @return
	 * @throws Exception
	 */
	@Override
	public PeriodMeeting checkPeriodInfo(PeriodQuery periodQuery) throws Exception {
		return periodMeetingMapper.checkPeriodInfo(periodQuery);
	}

	/**
	 * 获取周期列表
	 * @return
	 * @throws Exception
	 */
	@Override
	public void editPeriodMeeting(PeriodMeeting periodMeeting) throws Exception {
		periodMeetingMapper.editPeriodMeeting(periodMeeting);
	}

	/**
	 * 删除周期
	 * @return
	 * @throws Exception
	 */
	@Override
	public void deletePeriod(String uuid) throws Exception {
		periodMeetingMapper.deletePeriod(uuid);
	}
}

