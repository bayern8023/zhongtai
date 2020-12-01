package com.cmcc.domain.repository;

import com.cmcc.domain.model.Attendee;
import com.cmcc.domain.model.Meeting;
import com.cmcc.representation.co.JoinUserCO;

import java.util.List;

public interface AttendeeRepo {

	/**
	 * 删除参会人
	 * @return
	 * @throws Exception
	 */
	public List<Attendee> getAttendeeForPeriod(String uuid) throws Exception;

	/**
	 * 查询用户所属周期会议
	 * @return
	 * @throws Exception
	 */
	public List<Attendee> getPeriodListForMobile(String mobile) throws Exception;

	/**
	 * 删除参会人
	 * @return
	 * @throws Exception
	 */
	public void deleteAttendees(String uuid) throws Exception;

	/**
	 * 批量新增参会人
	 * @return
	 * @throws Exception
	 */
	public void addBatchAttendees(List<JoinUserCO> joinUserCOList, String uuid, String type) throws Exception;

}

