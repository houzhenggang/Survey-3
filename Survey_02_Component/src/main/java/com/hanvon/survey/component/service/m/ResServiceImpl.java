package com.hanvon.survey.component.service.m;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanvon.survey.component.dao.i.ResMapper;
import com.hanvon.survey.component.service.i.ResService;
import com.hanvon.survey.e.RemoveResFailedException;
import com.hanvon.survey.entities.manager.Res;
import com.hanvon.survey.utils.GlobalSettings;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Service
public class ResServiceImpl implements ResService {

	@Autowired
	private ResMapper resMapper;
	
	@Override
	public List<Res> getAll() {
		return resMapper.selectAll();
	}

	@Override
	public boolean servletPathExists(String servletPath) {
		int count = resMapper.servletPathExists(servletPath);
		return count > 0;
	}

	@Override
	public Integer getMaxPos() {		
		return resMapper.getMaxPos();
	}

	@Override
	public Integer getMaxCode(int maxPos) {
		return resMapper.getMaxCode(maxPos);
	}

	@Override
	public void saveRes(Res res) {
		resMapper.insert(res);
	}

	@Override
	public void deleteBatch(List<Integer> resIdList) {
		
		
		try {
			resMapper.deleteBatch(resIdList);
		} catch (Exception e) {
			e.printStackTrace();
			
			Throwable cause = e.getCause();
			if(cause instanceof MySQLIntegrityConstraintViolationException){				
				throw new RemoveResFailedException(GlobalSettings.MESSAGE_REMOVE_RES_FAILED);
			}
		}
				
		
	}

	@Override
	public boolean updateStatus(Integer resId) {
		//1.获取res对象
		Res res = resMapper.selectByPrimaryKey(resId);
		
		//2.获取res对象的资源状态
		boolean publicStatus = res.getPublicStatus();
		
		boolean finalStatus = !publicStatus ;
		
		//3.更新资源状态
		resMapper.updateStatus(finalStatus,resId);
		
		return finalStatus;
	}

	@Override
	public Res getResByServletPath(String servletPath) {
		return resMapper.getResByServletPath(servletPath);
	}
}
