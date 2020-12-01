package com.cmcc.application.convertor;

import com.cmcc.domain.model.Template;
import com.cmcc.representation.co.SaveTemplateCO;
import com.cmcc.representation.co.TemplateCO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.ReportingPolicy.IGNORE;

/**
 * @author suwei
 * @description 实体转换
 * @date 2020-09-12
 */
@Mapper(unmappedSourcePolicy = IGNORE)
public interface TemplateConvertorMapper {
    TemplateConvertorMapper MAPPER = Mappers.getMapper(TemplateConvertorMapper.class);

    /**
     * 转换成 Template实体
     * @return
     */
    Template toTemplate(TemplateCO templateCO);

    /**
     * 转化成 TemplateCO实体
     * @param saveTemplateCO
     * @return
     */
    TemplateCO toTemplateCO(SaveTemplateCO saveTemplateCO);

    /**
     * 转换成 toTemplateCO
     * @param Template
     * @return
     */
    TemplateCO toTemplateCO(Template Template);


}
