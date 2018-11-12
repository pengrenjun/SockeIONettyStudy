
package Netty.heartBeat;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

public class ServerHeartBeatHandler extends ChannelHandlerAdapter {
    
	/** key:ip value:auth */
	private static HashMap<String, String> AUTH_IP_MAP = new HashMap<String, String>();
	private static final String SUCCESS_KEY = "auth_success_key";
	
	static {
		try {
			InetAddress addr;
			addr = InetAddress.getLocalHost();
			String ip = addr.getHostAddress();
			AUTH_IP_MAP.put( ip, "1234");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	private boolean auth(ChannelHandlerContext ctx, Object msg){
			//System.out.println(msg);
			String [] ret = ((String) msg).split(",");
			String auth = AUTH_IP_MAP.get(ret[0]);
			if(auth != null && auth.equals(ret[1])){
				ctx.writeAndFlush(SUCCESS_KEY);
				return true;
			} else {
				ctx.writeAndFlush("auth failure !").addListener(ChannelFutureListener.CLOSE);
				return false;
			}
	}
	
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(msg instanceof String){
			auth(ctx, msg);
		} else if (msg instanceof RequestInfo) {
			
			RequestInfo info = (RequestInfo) msg;
			System.out.println("--------------------------------------------");
			System.out.println("???????ip?: " + info.getIp());
			System.out.println("???????cpu???: ");
			HashMap<String, Object> cpu = info.getCpuPercMap();
			System.out.println("???????: " + cpu.get("combined"));
			System.out.println("????????: " + cpu.get("user"));
			System.out.println("???????: " + cpu.get("sys"));
			System.out.println("?????: " + cpu.get("wait"));
			System.out.println("??????: " + cpu.get("idle"));
			
			System.out.println("???????memory???: ");
			HashMap<String, Object> memory = info.getMemoryMap();
			System.out.println("???????: " + memory.get("total"));
			System.out.println("???????????: " + memory.get("used"));
			System.out.println("???????????: " + memory.get("free"));
			System.out.println("--------------------------------------------");
			
			ctx.writeAndFlush("info received!");
		} else {
			ctx.writeAndFlush("connect failure!").addListener(ChannelFutureListener.CLOSE);
		}
    }


}
