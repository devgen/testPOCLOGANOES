package kafkasync;

import fish.payara.cloud.connectors.kafka.api.KafkaListener;
import fish.payara.cloud.connectors.kafka.api.OnRecord;
import fish.payara.cloud.connectors.kafka.api.OnRecords;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "testClient"),
    @ActivationConfigProperty(propertyName = "groupIdConfig", propertyValue = "testGroup"),
    @ActivationConfigProperty(propertyName = "topics", propertyValue = "test"),
    @ActivationConfigProperty(propertyName = "bootstrapServersConfig", propertyValue = "localhost:9092"),    
    @ActivationConfigProperty(propertyName = "autoCommitInterval", propertyValue = "100"),    
    @ActivationConfigProperty(propertyName = "retryBackoff", propertyValue = "1000"),    
    @ActivationConfigProperty(propertyName = "keyDeserializer", propertyValue = "org.apache.kafka.common.serialization.StringDeserializer"),    
    @ActivationConfigProperty(propertyName = "valueDeserializer", propertyValue = "org.apache.kafka.common.serialization.StringDeserializer"),    
    @ActivationConfigProperty(propertyName = "pollInterval", propertyValue = "30000"),    
})
public class KafkaMDB implements KafkaListener {

	private Controller controller = new Controller();
	private static Logger LOGGER = LoggerFactory.getLogger(KafkaMDB.class);
    
    public KafkaMDB() {
        
    }
    
	@OnRecord( topics= {"test"})
    public void test(ConsumerRecord<Object,Object> record) {
    	//System.out.println("Payara Kafka MDB record " + record.value().toString());
    	LOGGER.info("a test message");
    	controller.saveText(record.value().toString());	
    }
}