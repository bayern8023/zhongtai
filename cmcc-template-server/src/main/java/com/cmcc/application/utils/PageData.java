package com.cmcc.application.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PageData<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int count;

    private List<T> data;
}
