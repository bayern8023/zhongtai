package com.cmcc.infrastructure.database.mapper;

import com.cmcc.domain.model.Template;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public interface TemplateMapper {

    void saveTemplate(Template template) throws Exception;

    List<Template> getTemplateByCreatPeople(@Param("create_people")String people, @Param("offset")int offset, @Param("size")int size) throws Exception;

    Template getTemplateByUuid(String uuid) throws Exception;

    void updateTemplate(Template template) throws Exception;

    int deleteTemplate(@Param("create_people")String creatPeople, @Param("uuid") String uuid, @Param("update_time") String updateTime) throws Exception;

    boolean ifOnlyName(@Param("create_people")String creatPeople, @Param("template_name")String name) throws Exception;

    List<String> tpNameUuid(@Param("create_people")String creatPeople, @Param("template_name")String name) throws Exception;

    int nameCount(@Param("create_people")String creatPeople, @Param("template_name")String name) throws Exception;

    int templateCount(@Param("create_people") String createPeople);
}
