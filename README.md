# Запуск модуля с REST API

<code>mvn spring-boot:run</code>

После локального запуска сервисный метод будет доступен по адресу localhost:8080/min принимающий POST-запросы в формате JSON:
<code>{"algorithm":"MIN", "data":[1, 2, 3, 4, 5]}</code>, где <code>algorithm</code> один из двух: 
* <code>MIN</code> - наименьшая сумма двух элементов массива;
* <code>MIN_NO_FIRLS_LAST_BESIDE</code> - наименьшая сумма 2-х элементов, не являющихся первым, последним или соседними элементами.

В случае успешого выполнения, в ответе будет JSON следующего формата: <code>{"status" : "OK", "result": 3}</code>,
если входные аргументы неприемлемы, ответ будет иным: <code>{"status" : "ERROR", "error": "сообщение об ошибке"}</code>.

# Веб страница для ввода данных

Страница запакована вместе с REST API модулем, при старте доступна по корневому пути.

# Запуск в Docker

## серверный модуль

Для работы серверного модуля создадим образ контейнера. Для этого билд (собранный с плагином spring-boot) разместим рядом с файлом Docker:
<pre>
FROM java:8
MAINTAINER ilya.shipilov@gmail.com
EXPOSE 8080

ADD ROOT.war app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container","-jar","/app.jar"]
</pre>

В терминале перейдем в папку с этими файлами и выполним: <code>docker build -t restapp .</code>, тем самым создав образ с нашим модулем.
Затем запустим контейнер: <code>docker run -d -p 8080:8080 restapp</code>

## клиентский модуль

Клиентский модуль будет представлен парой http-запросов через curl.
Узнаем IP-адрес серверного модуля при помощи команды <code>docker inspect 5c22bdedd042D</code>, где 5c22bdedd042D - id контейнера, и
напишем shell-скрипт:
<pre>
#!/usr/bin/env bash
curl -X POST -H 'Content-Type: application/json' -d '{"algorithm":"MIN","data":[1,2,5,3,4]}' http://172.17.0.2:8080/min
</pre>

Находим на docker hub образ с установленным curl, и делаем свой:
<pre>
FROM hiromasaono/curl
MAINTAINER ilya.shipilov@gmail.com
ADD script.sh script.sh
</pre>

Выполнив команду <code>docker run -ti curlapp2 sh ./script.sh</code> увидим результат, полученный от серверного модуля.
