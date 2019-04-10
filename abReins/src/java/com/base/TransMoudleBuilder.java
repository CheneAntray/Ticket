package com.base;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.xianqin.domain.RoleResourceRel;
import com.xianqin.domain.UserRoleRel;
import com.xianqin.view.roleresourcerel.RoleResourceView;
import com.xianqin.view.userrolerel.UserRoleRelView;

/**
 * 该类用于生成视图对象(ViewObject)转换为业务对象(BusinessObject)的方法
 * 该类使用反射，对相同类型以及属性名的对象进行提取
 * 本类只适用于简单类型的javaBean,即：按照javaBean规范编写的扩展了序列化接口的简单类型javaBean属性相互拷贝方法的代码生成
 * @author xianqin-bill
 *
 */
public class TransMoudleBuilder {
	
	
	/**
	 * 对生成对象属性相互转换的程序代码进行生成
	 * 如果sourceObj具有的属性而targetObj不具有，则不会生成属性设置代码
	 * 如果targetObj具有，但sourceObj不具有的属性，会生成注释
	 * @param sourceObj 数据源对象class
	 * @param targetObj 目标对象class
	 * @param classNotesStr 类注释
	 * @return 对象属性拷贝的方法代码
	 */
	public static String builderValueSet(Class<?> sourceObj,Class<?> targetObj,String classNotesStr){
		StringBuffer stringBuffer = null;
		String souceBeanName = null;
		String targetBeanName = null;
		String sourceVariableName = null;
		String targetVariableName = null;
		String targetObjFieldName = null;
//		Map<String,Integer> sourceBeanFieldMap = null;//保存了属性以及属性类型修饰符的对应关系
		Map<String,Integer> targetBeanFieldMap = null;//保存了属性以及属性类型修饰符的对应关系
		Map<String,String> sourceGetModuleMap = null;//保存了属性以及get方法的对应关系，如果该属性没有get方法，则不讲该属性存储到数据结构中
		Map<String,String> targetSetModuleMap = null;//保存了属性以及set方法的对应关系，如果该属性没有set方法，则不讲该属性存储到数据结构中
		if(sourceObj!=null && sourceObj!=null){
			stringBuffer = new StringBuffer();
			souceBeanName = sourceObj.getSimpleName();
			targetBeanName = targetObj.getSimpleName();
			sourceVariableName = lastToLowerApp(souceBeanName);
			targetVariableName = lastToLowerApp(targetBeanName);
			stringBuffer.append("\t/**\n");
			stringBuffer.append("\t * "+classNotesStr+"\n");
			stringBuffer.append("\t * @param "+sourceVariableName+" 数据源对象实例\n");
			stringBuffer.append("\t * @param "+targetVariableName+" 数据目标对象实例\n");
			stringBuffer.append("\t * @return 具有源对象属性值的目标对象实例\n");
			stringBuffer.append("\t */\n");
			//获取class名称，构造方法头信息
			stringBuffer.append("\t public static "+targetBeanName+" process"+souceBeanName+"To"+targetBeanName+"("+souceBeanName+" "+sourceVariableName+","+targetBeanName+" "+targetVariableName+"){\n");
			//获取所有属性以及属性的修饰符
//			sourceBeanFieldMap = getFieldsAndModifiersByCalss(sourceObj);
			targetBeanFieldMap = getFieldsAndModifiersByCalss(targetObj);
			//获取所有targetObj的set方法
			targetSetModuleMap = processCalssFieldsAndSetModule(targetObj);
			//获取所有sourceObj的get方法
			sourceGetModuleMap = processCalssFieldsAndGetModule(sourceObj);
			//开始进行组装方法体  思路：进行targetSetModuleMap的key（属性名）迭代，如果target的属性在source的sourceGetModuleMap里有，就生成代码 否则，生成注释告诉用户，未匹配上
			for (Map.Entry<String, String> entry : targetSetModuleMap.entrySet()) {  
				targetObjFieldName = entry.getKey();
				if(sourceGetModuleMap.containsKey(targetObjFieldName) && targetBeanFieldMap.containsKey(targetObjFieldName)){
					stringBuffer.append("\t\t");
					stringBuffer.append(targetVariableName+"."+targetSetModuleMap.get(targetObjFieldName)+"(");
					stringBuffer.append(sourceVariableName+"."+sourceGetModuleMap.get(targetObjFieldName)+"());\n");
				}else{
					stringBuffer.append("\t\t//属性名:["+targetObjFieldName+"] 未找到映射关系,需要人工核对\n");
				}
			}
			stringBuffer.append("\t\treturn "+targetVariableName+";\n");
			stringBuffer.append("\t}\n");
			return stringBuffer.toString();
		}
		return null;
	}
	
	
	/**
	 * 获取所有具有getXXX方法的属性
	 * 该方法返回map结构
	 * key=属性名  value=get方法名
	 * get方法名必须严格按照javaBean规范进行命名
	 * 只有规范的get函数命名方法，才能被加入到数据结构中
	 * @param cls
	 * @return
	 */
	private static Map<String,String> processCalssFieldsAndGetModule(Class<?> cls){
		return getModuleByStringFilter(cls,"get");
	}
	
	/**
	 * 获取所有具有getXXX方法的属性
	 * 该方法返回map结构
	 * key=属性名  value=get方法名
	 * get方法名必须严格按照javaBean规范进行命名
	 * 只有规范的get函数命名方法，才能被加入到数据结构中
	 * @param cls
	 * @return
	 */
	private static Map<String,String> processCalssFieldsAndSetModule(Class<?> cls){
		return getModuleByStringFilter(cls,"set");
	}
	
	/**
	 * 根据关键字，将属性对应的bean方法找出来
	 * 比如  属性为name bean里面有两个方法，为getName和setName，关键字为'get'则查找getName方法
	 * 如果没有属性对应的get或set方法，返回的数据结构中不会包含该属性
	 * @param cls
	 * @param startStrFlag 用于过滤方法开头的字符串 一般为get或者set
	 * @return
	 */
	private static Map<String,String> getModuleByStringFilter(Class<?> cls,String startStrFlag){
		Map<String,String> moduleMap = null;//用于放置方法名的数据结构，目的是为了少进行数组迭代
		Map<String,String> reutrnMap = null;
		Method[] mths = null;
		Field[] fls = null;
		String methodName = null;
		String fieldName = null;
		Class<?> superClass = null;
		Method[] superMethods = null;
		//将所有以关键字开头的并且未public的方法放置到方法名数据结构中
		mths = cls.getMethods();
		if(mths!=null && mths.length>0){
			moduleMap = new HashMap<String,String>();
			for(Method method:mths){
				methodName = method.getName();
				//符合已过滤字符开头并且方法为public 则加入Map结构中
				if(methodName.startsWith(startStrFlag) && method.getModifiers()==1){
					moduleMap.put(firstToLowerCase(methodName.substring(3, methodName.length())), methodName);//放到map里以后，key直接就根据方法名转成了属性名
				}
			}
			if(cls.getSuperclass()!=null){
				superClass = cls.getSuperclass();
				superMethods = superClass.getDeclaredMethods();
				for(Method method:superMethods){
					methodName = method.getName();
					//符合已过滤字符开头并且方法为public 则加入Map结构中
					if(methodName.startsWith(startStrFlag) && method.getModifiers()==1){
						moduleMap.put(firstToLowerCase(methodName.substring(3, methodName.length())), methodName);//放到map里以后，key直接就根据方法名转成了属性名
					}
				}
			}
		
		}
		fls = cls.getDeclaredFields();
		if(fls!=null && fls.length>0 && moduleMap!=null && !moduleMap.isEmpty()){
			reutrnMap = new HashMap<String,String>();
			for(Field field:fls){
				fieldName = field.getName();
				if(moduleMap.containsKey(fieldName)){
					reutrnMap.put(fieldName, moduleMap.get(fieldName));
				}
			}
			if(superClass!=null){
				for(Field field:superClass.getDeclaredFields()){
					fieldName = field.getName();
					if(moduleMap.containsKey(fieldName)){
						reutrnMap.put(fieldName, moduleMap.get(fieldName));
					}
				}
			}
		}
		return reutrnMap;
	}
	
	/**
	 * 将类的所有属性与其属性类型修饰符保存到一个Map对象实例中
	 * 对应关系如下：
	 * PUBLIC: 1
	 * PRIVATE: 2
	 * PROTECTED: 4
	 * STATIC: 8
	 * FINAL: 16
	 * SYNCHRONIZED: 32
	 * VOLATILE: 64
	 * TRANSIENT: 128
	 * NATIVE: 256
	 * INTERFACE: 512
	 * ABSTRACT: 1024
	 * STRICT: 2048
	 * @param cla
	 * @return
	 */
	private static Map<String,Integer> getFieldsAndModifiersByCalss(Class<?> cla){
		Map<String,Integer> returnMap = null;
		Field[] flsArray = null;
		if(cla!=null){
			returnMap = new HashMap<String,Integer>();
			flsArray = cla.getDeclaredFields();
			for(Field field:flsArray){
				returnMap.put(field.getName(), field.getModifiers());
			}
		}
		return returnMap;
	}
	/**
	 * 将字符串首字母小写，其余保持不变然后输出
	 * 如：NaMe --> naMe
	 * @param str
	 * @return
	 */
	private static String lastToLowerApp(String str){
		if(str.length()>1){
			return str.substring(0,1).toLowerCase()+str.substring(1,str.length());
		}else{
			return str.toUpperCase();
		}
		
	}
	
	/**
	 * 将字符串首字母小写，其余保持不变然后输出
	 * 如：NaMe --> naMe
	 * @param str
	 * @return
	 */
	private static String firstToLowerCase(String str){
		if(str.length()>1){
			return str.substring(0,1).toLowerCase()+str.substring(1,str.length());
		}else{
			return str.toUpperCase();
		}
		
	}
	
	
	public static void main(String[] args){
		System.out.println(builderValueSet(RoleResourceRel.class,RoleResourceView.class,"将视图对象与业务对象具有相同属性名称的属性值进行拷贝，将视图对象属性值复制给业务对象实例"));
	}
}
