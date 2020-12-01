package com.cmcc.infrastructure.repository.impl;

import com.cmcc.domain.model.Attendee;
import com.cmcc.domain.model.Meeting;
import com.cmcc.domain.repository.AttendeeRepo;
import com.cmcc.domain.repository.MeetingRepo;
import com.cmcc.infrastructure.database.mapper.AttendeeMapper;
import com.cmcc.infrastructure.database.mapper.MeetingMapper;
import com.cmcc.representation.co.JoinUserCO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "attendeeRepo")
public class AttendeeRepoImpl implements AttendeeRepo {

	@Autowired
	private AttendeeMapper attendeeMapper;

	/**
	 * 查询周期的参会人
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Attendee> getAttendeeForPeriod(String uuid) throws Exception {
		return attendeeMapper.getAttendeeForPeriod(uuid);
	}

	@Override
	public List<Attendee> getPeriodListForMobile(String mobile) throws Exception {
		return attendeeMapper.getPeriodListForMobile(mobile);
	}

	/**
	 * 删除参会人
	 * @return
	 * @throws Exception
	 */
	@Override
	public void deleteAttendees(String uuid) throws Exception {
		attendeeMapper.deleteAttendees(uuid);
	}

	/**
	 * 更新会议id
	 * @return
	 * @throws Exception
	 */
	@Override
	public void addBatchAttendees(List<JoinUserCO> joinUserCOList,String uuid,String type) throws Exception {
		attendeeMapper.deleteAttendees(uuid);
		List<Attendee> attendeeList = new ArrayList<>();
		for (JoinUserCO joinUserCO:joinUserCOList) {
			Attendee attendee = new Attendee();
			attendee.setMobile(joinUserCO.getMobile());
			attendee.setUuid(uuid);
			attendee.setType(type);
			attendee.setName(joinUserCO.getName());
			attendee.setBusiness(joinUserCO.getBusiness());
			attendeeList.add(attendee);
		}
		attendeeMapper.addBatchAttendees(attendeeList);
	}

}

