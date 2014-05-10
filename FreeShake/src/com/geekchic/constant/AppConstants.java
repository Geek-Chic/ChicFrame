package com.geekchic.constant;

public class AppConstants {
	public static final int LOG_LEVEL_ERROR = 4;
	public static final int LOG_LEVEL_WARN = 3;
	public static final int LOG_LEVEL_INFO = 2;
	public static final int LOG_LEVEL_DEBUG = 1;
	public static final int Log_LEVEL_VERBOSE = 0;

	/**
	 * 开发版本 debug 属性设置为TRUE
	 */
	public static final boolean ISDEBUG_DEBUG_BUILD_VALUE = true;

	/**
	 * 发布版本 debug 属性设置为FALSE
	 */
	public static final boolean ISDEBUG_RELEASE_BUILD_VALUE = false;
	/**
	 * 表情页数
	 */
	public static final int NUM_PAGE = 6;
	/**
	 * 每页表情数量，加最后一个删除
	 */
	public static int NUM = 20;

	/**
	 * 一些公共信息
	 */
	public interface Common {
		/**
		 * 程序保存shared preferences的名字
		 */
		String SHARED_PREFERENCE_NAME = "Preferences";

		/**
		 * shared preference 键 存储当前登录userId
		 */
		String KEY_USER_ID = "userId";

		/**
		 * shared preference 键 标识是否登录状态
		 */
		String KEY_SESSION_ID = "sessionId";

		/**
		 * 推送绑定flag，避免重复绑定
		 */
		String PUSH_BIND_FLAG = "bind_flag";
		/**
		 * 是否显示导航标志
		 */
		String NAV_HAS_SHOW = "nav_has_show";
	}

	public interface REQUESTCODE {
		/**
		 * 请求结果
		 */
		String REQUEST_RESULT = "request_result";
		/**
		 * 返回码
		 */
		String REQUEST_CODE = "request_code";
		/**
		 * 密码请求
		 */
		String REQUEST_PASSWORD = "password";
		/**
		 * 电话
		 */
		String REQUEST_PHONE = "phone";
	}

	/**
	 * @ClassName: SERVICEWORK
	 * @Descritpion: 操作标识，交予工作线程处理
	 * @author evil
	 * @date May 8, 2014
	 */
	public interface SERVICEWORK {
		/**
		 * 登录服务
		 */
		int WORKER_LOGIN = 1;
		/**
		 * 注册服务
		 */
		int WORKER_REGISTER = 2;
		/**
		 * 从ContentProvicer获取联系人
		 */
		int WORKER_CONTACTS_FROM_PROVIDER = 3;

	}
	/**
	 * @ClassName: QUICKACTION
	 * @Descritpion: 快速工具栏标识
	 * @author evil
	 * @date May 10, 2014
	 */
    public interface QUICKACTION{
    	/**
    	 * 创建项目
    	 */
    	int ACTION_CREATEPROJECT=0;
    	/**
    	 * 二维码
    	 */
    	int ACTION_QR=1;
    }
	/**
	 * @ClassName: ChatMessageType
	 * @Descritpion: 聊天消息类型
	 * @author evil
	 * @date May 10, 2014
	 */
	public interface ChatMessageType {
		/**
		 * 文字消息
		 */
		int MESSAGE_TYPE_TEXT = 1;
		/**
		 * 图片消息
		 */
		int MESSAGE_TYPE_IMG = 2;
		/**
		 * 文件消息
		 */
		int MESSAGE_TYPE_FILE = 3;
	}
	// public LogConstants(){
	//
	// }
	// public static void openLog(Context context){
	// mContext=context;
	// }
	// public static void startCal(String title){
	// curTime=System.currentTimeMillis();
	// }
	// public static void calSpan(String log){
	// long time=System.currentTimeMillis();
	// curTime=time;
	// }
	// public static void v(String msg){
	// v(LOG_TAG,msg);
	// }
	// public static void v(String logTag, String msg)
	// {
	// if(Log_Level_verbose>=logLevel){
	// if(Log_WITH_POSTION){
	// msg=msg+"on"+new Throwable().getStackTrace()[1].toString();
	// }
	// Log.v(logTag, msg);
	// if(Log_IN_FILE){
	// writeIntoFile(msg);
	// }
	// }
	// }
	// public static void d(String msg){
	// d(LOG_TAG,msg);
	// }
	// public static void d(String logTag, String debugInfo)
	// {
	// if(Log_Level_debug>=logLevel){
	// debugInfo=debugInfo+"on "+new Throwable().getStackTrace()[1].toString();
	// }
	// Log.d(logTag, debugInfo);
	// if(Log_IN_FILE){
	// writeIntoFile(debugInfo);
	// }
	//
	// }
	// public static void i(String infoMsg){
	// i(LOG_TAG,infoMsg);
	// }
	// public static void i(String logTag,String infoMsg){
	// if(Log_Level_info>=logLevel){
	// infoMsg=infoMsg+"on "+new Throwable().getStackTrace()[1].toString();
	// }
	// Log.i(logTag, infoMsg);
	// if(Log_IN_FILE){
	// writeIntoFile(infoMsg);
	// }
	// }
	// public static void e(String errorMsg){
	// e(LOG_TAG,errorMsg);
	// }
	// public static void e(String logTag,String errorMsg){
	// if(Log_Level_error>=logLevel){
	// errorMsg=errorMsg+"on "+new Throwable().getStackTrace()[1].toString();
	// }
	// Log.e(logTag, errorMsg);
	// if(Log_IN_FILE){
	// writeIntoFile(errorMsg);
	// }
	// }
	//
	// public static void e(String title,Exception e) {
	//
	// String msg=null;
	// if (e==null) {
	// msg = title+": "+"null";
	// } else {
	// msg = title+": "+e.toString();
	// }
	// e(msg);
	// }
	// public static boolean writeIntoFile(String log){
	// log=log+"\n";
	// boolean res=false;
	// try
	// {
	// FileOutputStream fileOutputStream=mContext.openFileOutput(LOG_FILE,
	// Context.MODE_APPEND);
	// try
	// {
	// fileOutputStream.write(log.getBytes());
	// fileOutputStream.flush();
	// res=true;
	// }
	// catch (IOException e)
	// {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// LogConstants.e(e.toString());
	// }
	// }
	// catch (FileNotFoundException e)
	// {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// LogConstants.e(e.toString());
	// }
	// return res;
	//
	// }
}
