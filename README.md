# java-proxy
Simple Proxy server and client

Building:
```
git clone https://github.com/vadrx/java-proxy
mkdir build
javac -g -d build src/ru/novsu/povt/vas/*/*.java
```
Running:

Server:
```
java -cp build ru.novsu.povt.vas.server.Server
```

Client:
```
java -cp build ru.novsu.povt.vas.client.Client http://www.novsu.ru/ 127.0.0.1 3305
```