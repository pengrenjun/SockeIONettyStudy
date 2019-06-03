package ActiveMQ.PubSubModel;
import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消息的第一个订阅者
 */
public class JMSConsumer {

    private static final String USERNAME=ActiveMQConnection.DEFAULT_USER; // 默认的连接用户名
    private static final String PASSWORD=ActiveMQConnection.DEFAULT_PASSWORD; // 默认的连接密码
    private static final String BROKEURL=ActiveMQConnection.DEFAULT_BROKER_URL; // 默认的连接地址

    public static void main(String[] args) {

        ConnectionFactory connectionFactory; // 连接工厂
        Connection connection = null; // 连接
        Session session; // 会话 接受或者发送消息的线程
        Destination destination; // 消息的目的地
        MessageConsumer consumer; //创建消费者

        // 实例化连接工厂
        connectionFactory=new ActiveMQConnectionFactory(JMSConsumer.USERNAME, JMSConsumer.PASSWORD, JMSConsumer.BROKEURL);

        try {
            connection=connectionFactory.createConnection(); // 通过连接工厂获取连接
            connection.start(); // 启动连接
            /**
             * 这里的最好使用Boolean.FALSE，如果是用true则必须commit才能生效，且http://127.0.0.1:8161/admin管理页面才会更新消息队列的变化情况。
             */
            session=connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE); // 创建Session
//            destination=session.createQueue("FirstQueue1"); // 创建消息队列
            destination=session.createTopic("firstTopic");
            consumer=session.createConsumer(destination);
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    System.out.println(message.toString());
                }
            }); // 注册消息监听
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }




}
