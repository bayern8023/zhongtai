package com.cmcc.representation.co;

import com.cmcc.domain.model.Attendee;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TemplateCO {
    private static final long serialVersionUID = 1L;
    /*
        uuid
     */
    private String uuid;
    /*
       模版名称
     */
    private String templateName;
    /*
      创建人
     */
    private String createPeople;
    /*
        预设会议主持人，默认和创建人一致
     */
    private String meetingHost;
    /*
      创建时间
     */
    private String createTime;
    /*
        更新时间
     */
    private String updateTime;
    /*
      是否静音
     */
    private boolean meetingIfmute;
    /*
        画面设置
     */
    private int imageType;
    /*
        是否删除
     */
    private boolean ifDelete;
    /*
      参会人
     */
    private List<AttendeeCO> attendeeCOList;


}
