package io.eventuate.examples.tram.sagas.ordersandcustomers.orders;

import io.eventuate.examples.tram.sagas.ordersandcustomers.messaging.createorder.CreateOrderSagaData;
import io.eventuate.tram.commands.producer.CommandProducer;
import io.eventuate.tram.messaging.consumer.MessageConsumer;
import io.eventuate.tram.sagas.common.SagaLockManager;
import io.eventuate.tram.sagas.orchestration.*;

import javax.inject.Singleton;

@Singleton
public class OrderConfiguration {

  @Singleton
  public SagaManager<CreateOrderSagaData> createOrderSagaManager(Saga<CreateOrderSagaData> saga,
                                                                 SagaInstanceRepository sagaInstanceRepository,
                                                                 CommandProducer commandProducer,
                                                                 MessageConsumer messageConsumer,
                                                                 SagaLockManager sagaLockManager,
                                                                 SagaCommandProducer sagaCommandProducer) {

    return new SagaManagerImpl<>(saga,
            sagaInstanceRepository,
            commandProducer,
            messageConsumer,
            sagaLockManager,
            sagaCommandProducer);
  }
}
