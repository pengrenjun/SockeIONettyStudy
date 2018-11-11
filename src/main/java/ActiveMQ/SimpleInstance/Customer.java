package ActiveMQ.SimpleInstance;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.*;
import java.sql.Date;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;

/**
 * @Description: ActiveMq简单实例:消费者
 * @Author：pengrj
 * @Date : 2018/11/10 0010 19:20
 * @version:1.0
 */
public class Customer {

    //从ActiveMq消息中间件平台读取消费数据
    public  void startCustom() throws JMSException, InterruptedException {

        //1.建立ConnectionFactory工厂对象

        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://localhost:61616");

        //2.通过ConnectionFactory创建一个连接 指定认证的用户名和密码,并且启动连接
        Connection connection=connectionFactory.createConnection(ActiveMQConnectionFactory.DEFAULT_USER,ActiveMQConnectionFactory.DEFAULT_PASSWORD);
        connection.start();

        //3.通过建立的连接connection创建session会话(连接的上下文环境),用于接受消息,参数1：是否启用事务 参数2：设定签收模式 一般设为自动签收
                /*AUTO_ACKNOWLEDGE
                CLIENT_ACKNOWLEDGE
                DUPS_OK_ACKNOWLEDGE*/

        Session session=connection.createSession(true,AUTO_ACKNOWLEDGE);

        //4.通过Session创建 Destination 对象，用来指定生产消息目标和消费来源目标
        //  在点对点PTP中  Destination称为Queue 即队列  在Pub/Sub中 被称为主题Topic 可创建指定多个队列和主题

        Destination destination=session.createQueue("TextQueue");

        Destination destination1=session.createQueue("MapQueue");


        //5.通过Session对象创建消息的生产和消费者

        MessageConsumer messageConsumer=session.createConsumer(destination);
        MessageConsumer messageConsumer1=session.createConsumer(destination1);

        //6.设置生产者的生产数据的持久化特性

        //7.创建数据 生产者发送数据 消费者接收数据 关闭connection 连接



           TextMessage textMessage= (TextMessage) messageConsumer.receive();

           MapMessage mapMessage= (MapMessage) messageConsumer1.receive();

           Enumeration mapNames = mapMessage.getMapNames();

           System.out.println(textMessage.toString());


           System.out.println("TextQueue>"+textMessage.getText());



        session.commit();

        connection.close();

        System.out.println("Customer has customed Messages ok!");


    }

    public static void main(String[] args) throws JMSException, InterruptedException {
        Customer customer=new Customer();
        customer.startCustom();
    }
}
