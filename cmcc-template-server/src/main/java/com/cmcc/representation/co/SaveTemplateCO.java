package com.cmcc.representation.co;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SaveTemplateCO {
    private static final long serialVersionUID = 1L;
    /*
       模版名称
     */
    private String templateName;
    /*
      创建人
     */
    private String createPeople;
    /*
      是否静音
     */
    private boolean meetingIfmute;
    /*
      参会人
     */
    private List<AttendeeCO> attendeeCOList;


}
