package com.base;

/**
 * 定义整个系统中持久层需要进行说明的字段值
 * 
 * @author xianqin-bill
 *
 */
public class CodeDefined {
	public static class A {
		public static class B {
			/**
			 * B字段的删除状态
			 */
			public static final String IS_DEL = "0";

			/**
			 * B字段的新增状态
			 */
			public static final String IS_NEW = "0";
		}
	}

	/**
	 * 资产变更申报
	 * 
	 * @author Yr
	 */
	public static class ASSET_CHANGE_DECLARE {

		/**
		 * 申报类型
		 */
		public static class DECLARE_TYPE {

			/**
			 * 报废
			 */
			public static final Integer SCRAP = 0;

			/**
			 * 退回 
			 */
			public static final Integer BACK = 1;

			/**
			 * 丢失
			 */
			public static final Integer LOSE = 2;

			/**
			 * 其他
			 */
			public static final Integer OTHER = 3;

		}

		/**
		 * 申报状态
		 */
		public static class DECLARE_STATE {

			/**
			 * 未处理
			 */
			public static final Integer UNTREATED = 0;

			/**
			 * 已处理
			 */
			public static final Integer HANDLING = 1;

		}

		/**
		 * 审批类型
		 */
		public static class APPROVE_STATE {

			/**
			 * 已审批
			 */
			public static final Integer APPROVED = 0;

			/**
			 * 未审批
			 */
			public static final Integer NOTAPPROVED = 1;
			
			/**
			 * 驳回
			 */
			public static final Integer BACK = 2;

		}
		/**
		 * 接受状态
		 */
		public static class ACCEPT_STATE {

			/**
			 * 已接收
			 */
			public static final Integer ISACCEPT = 0;

			/**
			 * 未接受
			 */
			public static final Integer UNACCEPT = 1;
			

		}
	}

	/**
	 * 资产信息
	 * 
	 * @author Yr
	 */
	public static class ASSET_INFORMATION {

		/**
		 * 申报类型
		 */
		public static class GET_TYPE {

			/**
			 * 购置
			 */
			public static final Integer PURCHASE = 0;

			/**
			 * 转入
			 */
			public static final Integer SHIFT_TO = 1;

		}

		/**
		 * 是否在账
		 */
		public static class IS_ACCOUNT {

			/**
			 * 未在账
			 */
			public static final Integer NOTACCOUNT = 0;

			/**
			 * 在账
			 */
			public static final Integer INACCOUNT = 1;
		}

		/**
		 * 使用状态
		 */
		public static class USESTATE_ID {
			/**
			 * 再用
			 */
			public static final Integer USED = 0;

			/**
			 * 报废
			 */
			public static final Integer Discard = 1;

			/**
			 * 丢失
			 */
			public static final Integer LOSE = 2;

			/**
			 * 转移
			 */
			public static final Integer TRANSFER = 3;

			/**
			 * 闲置
			 */
			public static final Integer UNUSED = 4;

			/**
			 * 待分配
			 */
			public static final Integer UnAssign = 5;
			
			/**
			 * 待接收
			 */
			public static final Integer UnAccept = 6;
			
		}
		
		/**
		 * 所在层级
		 */
		public static class ASSET_LEVEL {
			/**
			 * 一级
			 */
			public static final Integer FIRST = 1;

			/**
			 * 二级
			 */
			public static final Integer SECOND = 2;

			/**
			 * 三级
			 */
			public static final Integer THIRD = 3;		
					
		}
	}

	/**
	 * 资产流转日志
	 * 
	 * @author Yr
	 *
	 */
	public static class ASSET_FLOW_LOG {

		/**
		 * 转出状态
		 */
		public static class ROLL_OUT_STATE {

			/**
			 * 再用
			 */
			public static final Integer USED = 0;

			/**
			 * 报废
			 */
			public static final Integer Discard = 1;

			/**
			 * 丢失
			 */
			public static final Integer LOSE = 2;

			/**
			 * 转移
			 */
			public static final Integer TRANSFER = 3;

			/**
			 * 闲置
			 */
			public static final Integer UNUSED = 4;

			/**
			 * 待分配
			 */
			public static final Integer UnAssign = 5;
			
			/**
			 * 待接收
			 */
			public static final Integer UnAccept = 6;

		}

	}

	/**
	 * 盘点任务信息表
	 * 
	 * @author Yr
	 *
	 */
	public static class INVENTORY_TASKSINFO {

		/**
		 * 发布周期
		 */
		public static class RELEASE_CYCLE {

			/**
			 * 按周发布
			 */
			public static final Integer WEEK = 0;

			/**
			 * 按月发布
			 */
			public static final Integer MONTH = 1;

			/**
			 * 按季度发布
			 */
			public static final Integer QUARTER = 2;

			/**
			 * 按半年发布
			 */
			public static final Integer HALF = 3;

			/**
			 * 按年发布
			 */
			public static final Integer YEAR = 4;

		}

		/**
		 * 发布状态
		 */
		public static class RELEASE_STATES {

			/**
			 * 未发布
			 */
			public static final Integer NOTRELEASE = 0;

			/**
			 * 发布
			 */
			public static final Integer RELEASE = 1;
		}

		/**
		 * 任务性质
		 */
		public static class TASK_PROERTIES {

			/**
			 * 自动
			 */
			public static final Integer AUTOMATIC = 0;

			/**
			 * 手动
			 */
			public static final Integer MANUAL = 1;
		}
	}

	/**
	 * 盘点任务清单
	 * 
	 * @author Yr
	 *
	 */
	public static class INVENTORY_TASKLIST {

		/**
		 * 状态
		 */
		public static class RELEASE_STATE {

			/**
			 * 已完成
			 */
			public static final Integer COMPLETE = 0;

			/**
			 * 未完成
			 */
			public static final Integer NOTCOMPLETE = 1;
		}

	}

	/**
	 * 盘点清单
	 * 
	 * @author Yr
	 *
	 */
	public static class INVENTORY_LIST {

		/**
		 * 差错方向
		 */
		public static class ERROR_DIRECTION {

			/**
			 * 初始状态
			 */
			public static final Integer DEFAULT = 0;
			/**
			 * 一致
			 */
			public static final Integer UNANIMOUS = 1;

			/**
			 * 资产增加
			 */
			public static final Integer ASSET_ADDING = 2;
		}

	}

	/**
	 * 人员轨迹
	 * 
	 * @author ly
	 *
	 */
	public static class PERSON_TRACK {
		/**
		 * 操作类型
		 */
		public static class OPERATE_TYPE {
			/**
			 * 新增
			 */
			public static final Integer IS_NEW = 0;
			/**
			 * 修改
			 */
			public static final Integer IS_UPDATE = 1;
			/**
			 * 删除
			 */
			public static final Integer IS_REMOVE = 2;
		}

	}

	/**
	 * 功能信息
	 * 
	 * @author ly
	 *
	 */
	public static class FUNCTION_INFO {
		/**
		 * 是否有效
		 */
		public static class ISVALID {

			/**
			 * 有效
			 */
			public static final Integer TRUE_VALID = 0;
			/**
			 * 无效
			 */
			public static final Integer FALSE_VALID = 1;
		}
	}

	/**
	 * 用户信息
	 * 
	 * @author ly
	 *
	 */
	public static class USER_INFO {
		/**
		 * 用户状态
		 */
		public static class USER_STATE {
			/**
			 * 正常
			 */
			public static final Integer NORMAL = 0;
			/**
			 * 锁定
			 */
			public static final Integer LOCK = 1;
		}
	}
	
	/**
	 * 所有表
	 * 
	 * @author ly
	 *
	 */
	public static class ALL_TABLE {
		/**
		 * 状态列
		 */
		public static class STATUS {
			/**
			 * 未删除
			 */
			public static final Integer NOT_DELETE = 0;
			/**
			 * 已删除
			 */
			public static final Integer IS_DELETE = -1;
		}
	}
	
	/**
	 * 安全设备信息
	 * 
	 * @author ly
	 *
	 */
	public static class SAFETY_INFO {
		/**
		 * 安全设备状态
		 */
		public static class SAFETY_STATE {
			/**
			 * 使用中
			 */
			public static final Integer INUSE = 0;
		}
	}
	
	

}
