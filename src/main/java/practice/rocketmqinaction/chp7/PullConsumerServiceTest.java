package practice.rocketmqinaction.chp7;

import org.apache.rocketmq.client.consumer.*;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

public class PullConsumerServiceTest {
    public static void main(String[] args) throws Exception {
        final MQPullConsumerScheduleService scheduleService = new MQPullConsumerScheduleService("PullConsumerService");
        scheduleService.getDefaultMQPullConsumer().setNamesrvAddr("localhost:9876");
        scheduleService.setMessageModel(MessageModel.CLUSTERING);
        scheduleService.registerPullTaskCallback("pullCallBack", new PullTaskCallback() {
            @Override
            public void doPullTask(MessageQueue mq, PullTaskContext context) {
                MQPullConsumer consumer = context.getPullConsumer();
                try {
                    long offset = consumer.fetchConsumeOffset(mq, false);
                    if (offset < 0) {
                        offset = 0;
                    }
                    PullResult pullResult = consumer.pull(mq, "*", offset, 32);
                    System.out.printf("%s%n", offset + "\t" + mq + "\t" + pullResult);
                    switch (pullResult.getPullStatus()) {
                        case FOUND:
                            break;
                        case NO_MATCHED_MSG:
                            break;
                        case NO_NEW_MSG:
                        case OFFSET_ILLEGAL:
                            break;
                            default:
                                break;
                    }
                    consumer.updateConsumeOffset(mq, getNextBeginOffset());
                    context.setPullNextDelayTimeMillis(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        scheduleService.start();
    }

    private static long getNextBeginOffset() {
        return 0L;
    }
}
