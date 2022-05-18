# JACOCO

Inserire la variabile JVM
```
-javaagent:<path_progetto>/javacommandinjection/jacoco/jacocoagent.jar=output=tcpserver,address=*,port=6300
```

All'avvio del programma si ha un agente che analizza il codice binario. 
Rimane in ascolto sulla porta `6300` e tramite la `jacococli` è possibile scaricare il dump dell'analisi nel file binario `jacoco.exec` con il comando:
```shell
 java -jar jacoco/jacococli.jar dump --destfile jacoco.exec --reset
```
N.B.: 
 - l'opzione `--reset` è utile per resettare l'analisi ogni volta che si fa il dump
 - è utile prima di eseguire il dump cancellare il file `jacoco.exec` vecchio oppure salvare il risultato in un file diverso

Per convertire il risultato del dump eseguire il comando 
```shell
java -jar jacoco/jacococli.jar report jacoco.exec --xml jacoco_report.xml --sourcefiles src/main/java/ --classfiles target/classes/
```
