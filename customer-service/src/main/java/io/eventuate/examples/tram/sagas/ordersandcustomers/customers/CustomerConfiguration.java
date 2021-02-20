package io.eventuate.examples.tram.sagas.ordersandcustomers.customers;

import io.eventuate.examples.tram.sagas.ordersandcustomers.customers.service.CustomerCommandHandler;
import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;

import javax.inject.Singleton;

@Singleton
public class CustomerConfiguration {

  // TODO Exception handler for CustomerCreditLimitExceededException

  @Singleton
  public CommandDispatcher consumerCommandDispatcher(CustomerCommandHandler target,
                                                     SagaCommandDispatcherFactory sagaCommandDispatcherFactory) {

    return sagaCommandDispatcherFactory.make("customerCommandDispatcher", target.commandHandlerDefinitions());
  }

}
