package ActiveMQ.ActiveMqJmx;

//这里自定义一个Model来接收队列的信息
public class MqModel {

    private String name;//队列的名称
    private Long queueSize;//队列中剩余的消息数
    private Long consumerCount;//消费者数
    private Long enqueueCount;//进入队列的总数量
    private Long dequeueCount;//出队列的数量

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(Long queueSize) {
        this.queueSize = queueSize;
    }

    public Long getConsumerCount() {
        return consumerCount;
    }

    public void setConsumerCount(Long consumerCount) {
        this.consumerCount = consumerCount;
    }

    public Long getDequeueCount() {
        return dequeueCount;
    }

    public void setDequeueCount(Long dequeueCount) {
        this.dequeueCount = dequeueCount;
    }

    public Long getEnqueueCount() {
        return enqueueCount;
    }

    public void setEnqueueCount(Long enqueueCount) {
        this.enqueueCount = enqueueCount;
    }

    @Override
    public String toString() {
        return "MqModel{" +
                "name='" + name + '\'' +
                ", queueSize=" + queueSize +
                ", consumerCount=" + consumerCount +
                ", enqueueCount=" + enqueueCount +
                ", dequeueCount=" + dequeueCount +
                '}';
    }
}
