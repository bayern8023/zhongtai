package com.cmcc.infrastructure.database.mapper;

import com.cmcc.domain.model.PeriodMeeting;
import com.cmcc.domain.model.PeriodQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PeriodMeetingMapper {


	public void savePeriodMeeting(PeriodMeeting periodMeeting) throws Exception;

	public List<PeriodMeeting> getPeriodList(PeriodQuery periodQuery) throws Exception;

	public int getPeriodListCount(PeriodQuery periodQuery) throws Exception;

	public PeriodMeeting getPeriodInfo(PeriodQuery periodQuery) throws Exception;

	public PeriodMeeting checkPeriodInfo(PeriodQuery periodQuery) throws Exception;

	public void editPeriodMeeting(PeriodMeeting periodMeeting) throws Exception;

	public void deletePeriod(String uuid);
}

