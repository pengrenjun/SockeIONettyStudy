package NettyAction.DecodeAndEncode.jboosMarshelling;

import Netty.Serial.MarshallingCodeCFactory;
import NettyAction.DecodeAndEncode.JavaObjectCode.RtuMeterClientHandler;
import NettyAction.DecodeAndEncode.JavaObjectCode.RtuMeterServerHandler;
import NettyAction.TimeServerAndClient.democlient.NettyClient;
import NettyAction.TimeServerAndClient.demoserver.NettyServer;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.junit.Test;

/**
 * 测试基于jboss marsheling序列化的数据的编码解码
 */
public class TestMarshellingCode {

    //启动服务端
    @Test
    public void startMshellingMeterServer(){

      //测试时 现把CommonMsg implements Serializable先注释了 不需要implements Serializable
        //但在测试的时候 实体bean不实现序列化接口 是接收不到消息的

        new NettyServer()
                .initServerAndBindPort(8005)
                .addChildHandlers(MarshallingCodeCFactory.buildMarshallingDecoder(),
                        MarshallingCodeCFactory.buildMarshallingEncoder(),new RtuMeterServerHandler())
                .start();

    }

    //启动设备
    @Test
    public void startMeterClient(){

        new NettyClient()
                .connect("localhost",8005)
                .addHandlers(MarshallingCodeCFactory.buildMarshallingDecoder(),MarshallingCodeCFactory.buildMarshallingEncoder(),new RtuMeterClientHandler())
                .start();
    }
}
