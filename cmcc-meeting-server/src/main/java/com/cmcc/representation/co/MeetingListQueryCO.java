package com.cmcc.representation.co;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 会议列表查询实体
 * @author baiyanmin
 * @since 2020-09-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MeetingListQueryCO implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * 会议状态
     */
    @JsonProperty("meeting_filter")
    private String meetingFilter;

    /*
     * 页数
     */
    @JsonProperty("page")
    private int page;

    /*
     * 分页大小
     */
    @JsonProperty("page_size")
    private int pageSize;
}
