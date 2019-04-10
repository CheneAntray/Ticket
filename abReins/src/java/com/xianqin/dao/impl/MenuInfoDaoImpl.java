package com.xianqin.dao.impl;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.base.utils.UUIDGenerator;
import com.xianqin.common.Page;
import com.xianqin.common.QueryRule;
import com.xianqin.dao.MenuInfoDao;
import com.xianqin.domain.MenuInfo;
/**
 * 菜单信息对象数据访问层接口实现类
 * 该接口实现了menuInfoDao接口常用的数据访问层方法
 * @author xianqin-atuoBuilder
 * @@version 1.0
 */
 
@Repository("menuInfoDao")
public class MenuInfoDaoImpl extends CommonDaoImpl<MenuInfo, String> implements MenuInfoDao{
	@Override
	public void saveMenuInfo(MenuInfo menuInfo) {
		menuInfo.setId(UUIDGenerator.getUUID()); //生成主键
//		menuInfo.setCreateTime(new Date());    //创建时间
		this.save(menuInfo);
	}

	@Override
	public void updateMenuInfo(MenuInfo menuInfo) {
		menuInfo.setUpdateTime(new Date());
		this.update(menuInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<MenuInfo> queryMenuInfoByPage(QueryRule queryRule, Integer pageIndex, Integer pageSize) {
		queryRule.addDescOrder("createTime");
		return  this.find(queryRule, pageIndex, pageSize);
	}

    @Override
	public void deleteMenuInfoById(String menuInfoId) {
		String hql="DELETE FROM MenuInfo WHERE ID=?";
		this.getSessionFactory().getCurrentSession().createQuery(hql)
		.setParameter(0, menuInfoId).executeUpdate();
	}
	
	@Override
	public MenuInfo getMenuInfoByCondition(QueryRule queryRule) {
		List<MenuInfo> menuInfos=this.find(queryRule);
		MenuInfo menuInfo=null;
		if (menuInfos!=null && !menuInfos.isEmpty()) {
			menuInfo=menuInfos.get(0);
		}
		return menuInfo;
	}

	@Override
	public List<MenuInfo> getMenuInfoListByCondition(QueryRule queryRule) {
		// TODO Auto-generated method stub
		return this.find(queryRule);
	}
}
