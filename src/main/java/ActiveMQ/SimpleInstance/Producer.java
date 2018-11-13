package ActiveMQ.SimpleInstance;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.*;


import java.sql.Date;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;
import static javax.jms.Session.CLIENT_ACKNOWLEDGE;

/**
 * @Description: ActiveMq简单实例:生产者
 * @Author：pengrj
 * @Date : 2018/11/10 0010 19:20
 * @version:1.0
 */
public class Producer {

    //向ActiveMq消息中间件平台传送数据
    public  void startProduce() throws JMSException {

       //1.建立ConnectionFactory工厂对象
        /*<transportConnectors>
            <!-- DOS protection, limit concurrent connections to 1000 and frame size to 100MB -->
            <transportConnector name="openwire" uri="tcp://0.0.0.0:61616?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
            <transportConnector name="amqp" uri="amqp://0.0.0.0:5672?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
            <transportConnector name="stomp" uri="stomp://0.0.0.0:61613?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
            <transportConnector name="mqtt" uri="mqtt://0.0.0.0:1883?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
            <transportConnector name="ws" uri="ws://0.0.0.0:61614?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
        </transportConnectors>
*/
        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://localhost:61616");

        //2.通过ConnectionFactory创建一个连接 指定认证的用户名和密码,并且启动连接
        Connection connection=connectionFactory.createConnection(ActiveMQConnectionFactory.DEFAULT_USER,ActiveMQConnectionFactory.DEFAULT_PASSWORD);
        connection.start();

        //3.通过建立的连接connection创建session会话(连接的上下文环境),用于接受消息,参数1：是否启用事务 参数2：设定签收模式 一般设为自动签收
                /* 签收模式:
                AUTO_ACKNOWLEDGE   自动签收 客户端调用receive()方法就会自动向Mq服务器发送已经接收消息的信息 更新Mq服务器的数据
                CLIENT_ACKNOWLEDGE 推荐:手动确认签收 需要在处理完数据之后 调用 textMessage.acknowledge();方法才会真正的更新MQ服务器的数据 并启动线程向MQ服务器发送已处理完毕的信息
                DUPS_OK_ACKNOWLEDGE
                */

        //使用事务的方式创建session 手动确认签收
        Session textSession=connection.createSession(true,CLIENT_ACKNOWLEDGE);
        //使用事务的方式创建session 需要使用session.commit()才能将数据发送 自动签收
        Session mapSession=connection.createSession(true,AUTO_ACKNOWLEDGE);

        //4.通过Session创建 Destination 对象，用来指定生产消息目标和消费来源目标
        //  在点对点PTP中  Destination称为Queue 即队列  在Pub/Sub中 被称为主题Topic 可创建指定多个队列和主题

        Destination destination=textSession.createQueue("TextQueue");

        Destination destination1=mapSession.createQueue("MapQueue");


        //5.通过Session对象创建消息的生产和消费者

        MessageProducer messageProducer=textSession.createProducer(destination);

        ActiveMQMessageProducer messageProducer1= (ActiveMQMessageProducer) mapSession.createProducer(null);

        //MessageConsumer messageConsumer=session.createConsumer(destination);

        //6.设置生产者的生产数据的持久化特性
        /*
        * @see javax.jms.DeliveryMode#NON_PERSISTENT
        * @see javax.jms.DeliveryMode#PERSISTENT
         * @see javax.jms.Message#DEFAULT_DELIVERY_MODE*/
        messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        //messageProducer1.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        //7.创建数据 生产者发送数据 消费者接收数据 关闭connection 连接

        TextMessage textMessage=new ActiveMQTextMessage();
        for(int i=0;i<2;i++){
            textMessage.setText("Message>"+i+":"+String.valueOf(new Date(System.currentTimeMillis())));
            messageProducer.send(textMessage);
        }

        MapMessage mapMessage=new ActiveMQMapMessage();
        for(int i=0;i<10;i++){

            mapMessage.setString(String.valueOf(i), String.valueOf(System.currentTimeMillis()));
            mapMessage.setIntProperty("num",i);


           // send(Destination destination, Message message, int deliveryMode, int priority, long timeToLive)
            messageProducer1.send(destination1, mapMessage, DeliveryMode.NON_PERSISTENT, i, 1000 * 60 * 10, new AsyncCallback() {
                @Override
                public void onSuccess() {
                    System.out.println("mapQueue send data ok! ");
                }
                @Override
                public void onException(JMSException e) {
            }
            });
        }


        textSession.commit();
        mapSession.commit();

        connection.close();

        System.out.println("Producers has sended Messages ok!");

    }

    public static void main(String[] args) throws JMSException {
        Producer producer=new Producer();
        producer.startProduce();
    }
}
