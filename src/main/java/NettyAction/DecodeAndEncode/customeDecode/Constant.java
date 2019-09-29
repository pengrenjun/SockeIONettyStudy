package NettyAction.DecodeAndEncode.customeDecode;

/**
 * Constant
 * 
 * @author <a href="mailto:zhaol@emrubik.com">zhao lei</a>
 * @version $Revision 1.0 $ Dec 19, 2017 3:44:39 PM
 */
public final class Constant {

	/**
	 * Create a new instance Constant
	 */
	private Constant() {

	}

	/** 消息认证码 */
	public static final byte[] MESSAGE_AUTHENTICATIONCODE = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	/** 事件 */
	public static final String SESSION_EVENT = "SESSION_EVENT";

	/** 异常原因 */
	public static final String SESSION_EXCEPTION_CAUSE = "SESSION_EXCEPTION_CAUSE";

	/** 事件类型：建立链接 */
	public static final int SESSION_OPEN = 1;

	/** 事件类型：关闭连接 */
	public static final int SESSION_CLOSED = 0;

	/** 事件类型：链接异常 */
	public static final int SESSION_EXCEPTION = 2;

	/** 事件类型：接收数据 */
	public static final int MESSAGE_RECEIVED = 4;

	/** 报文起始字符 */
	public static final int START_VALUE = 104;

	/** 常量255 */
	public static final int BYTE_MAX = 0xff;


	/** 报文结束字符 */
	public static final int END_VALUE = 22;

}

