## Pasos para compilar y ejecutar el programa:
### Compilación:
`javac -d clases -cp libs/amqp-client-5.7.1.jar src/*`  

### Ejecución Cliente:
`java -cp "clases:libs/*" RPCClient`  

### Ejecución Servidor:
`java -cp "clases:libs/*" RPCServer`  

## Descripcion detallada:
El servicio recibirá un número que corresponde al nombre de un archivo almacenado en un subdirectorio de la aplicación denominado 'archivos' . Para ello, el servidor contará con un archivo de indices llamado indices.txt que deberá tener una estructura como la siguiente, donde el primer valor es el índice asociado a un documento y el segundo valor es el nombre del archivo asociado a dicho índice  

1, archivo1.txt  
2, archivo2.txt  
3, archivo3.txt  

La aplicación debe requerir un número al servidor y presentar el contenido del archivo asociado en el archivo de índices del servidor. Si se invoca el servicio con un número que no exista, debe devolver un indicador de error.  
