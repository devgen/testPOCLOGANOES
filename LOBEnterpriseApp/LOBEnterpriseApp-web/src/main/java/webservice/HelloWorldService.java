package webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.kafka.clients.producer.Callback;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

@Path("/hello")
public class HelloWorldService {
	
	KafkaProducer<String, String> producer;
    String topic = null;
    static Random rnd = new Random();	
	
	@GET
    @Path("/{name}")
    public Response getMsg(@PathParam("name") String name) {
		
		Properties consumerProps = new Properties();
        consumerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, System.getenv().getOrDefault("KAFKA_BROKER", "localhost:9092"));
        consumerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        consumerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<>(consumerProps);
        topic = "test";
        
        String key = "";
        producer.send(new ProducerRecord(topic, "key-" + rnd.nextInt(10), "val-" + rnd.nextInt(10)), new Callback() {
            @Override
            public void onCompletion(RecordMetadata rm, Exception excptn) {
                System.out.println("Sent data....");
            }
        });
  
        String output = "Welcome   : " + name;
  
        return Response.status(200).entity(output).build();  
    }
}
