/* Copyright©2020-2020 China Mobile Communications Group Co.,Ltd. All rights reserved. */
package com.cmcc.domain.event;


import com.cmcc.application.utils.Result;
import org.springframework.stereotype.Component;

@Component
public interface TemplateEvent<T> {

    Result saveTemplate(String token, T template) throws Exception;

    Result updateTemplate(String token, T template) throws Exception;

    Result deleteTemplate(String token, String uuid) throws Exception;

    Result getTemplateByUuid(String token, String uuid) throws Exception;

    Result getTemplateByOwner(String token, int page, int size)throws Exception;



}
