package com.jsf.mapper;

import com.jsf.database.model.manage.Log;
import com.jsf.database.model.custom.BaseVo;

import java.util.List;
import java.util.Map;

/**
 * LogMapper Interface
 * @date 2016年08月29日 上午 11:15:12
 * @author jfxu
 */
public interface LogMapper {

	List<Log> findByCondition(BaseVo baseVo);

	int findCountAll();

	List<Map<String, Object>> findOldLog(Integer monthAgo);

	int insert(Log bean);

	int deleteOldLog(Integer monthAgo);

}