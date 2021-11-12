Compilar:
javac -d clases -cp libs/amqp-client-5.7.1.jar src/RPCClient.java src/RPCServer.java

Ejecutar Productor
java -cp "clases:libs/*" RPCClient

Ejecutar Consumidor
java -cp "clases:libs/*" RPCServer
