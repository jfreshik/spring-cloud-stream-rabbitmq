# Spring Cloud Stream example apps

## using RabbitMQ

* docker-compose.yml
```yaml
version: '3'
services:
  rabbitmq:
    image: rabbitmq:3-management-alpine
    container_name: rabbitmq-stream
    ports:
      - 5672:5672
      - 15672:15672
    environment:
      RABBITMQ_DEFAULT_USER: admin
      RABBITMQ_DEFAULT_PASS: admin

```

* 실행
```shell script
$ docker-compose up -d
```

* RabbitMQ 관리자 화면
> http://localhost:15672

## application.yml 바인더 설정
```yaml
spring:
  cloud:
    stream:
      bindings:
        input:
          destination: queue.log.messages
          binder: local_rabbit
        output:
          destination: queue.pretty.log.messages
          binder: local_rabbit
      binders:
        local_rabbit:
          type: rabbit
          environment:
            spring:
              rabbitmq:
                host: localhost
                port: 5672
                username: admin
                password: admin
                virtual-host: /
```

* `spring.cloud.stream.binders` 에 바인딩 하는 미들웨어 정보 기술
* `spring.cloud.stream.bindings` 에 채널 바인딩 정보 기술

## Logging Consumer application
[Spring Cloud Stream QuickStart](https://docs.spring.io/spring-cloud-stream/docs/3.0.8.RELEASE/reference/html/spring-cloud-stream.html#_quick_start)

* Consumer 를 이용한 단일 메시지 핸들러 구현 (framework convention)
* RabbitMQ 관리자 화면에서 Application 실행 시 생성된 큐로 Person 클래스 JSON 데이터를 publish 하면 메시지 수신 로그 출력

```shell scriptj
{"name":"sam smith"}
```


## MyLoggerService application
[Introduction to Spring Cloud Stream](https://www.baeldung.com/spring-cloud-stream)

* Processor binding sample
* listens to _input_ binding and sends a response to _output_ binding
* RabbitMQ admin 에서 publish message
```shell script
{"message":"test message"}
```

* TextPlainMessageConvert 로 메시지 변환 처리
  * MimeType 으로 text/plain 메시지만 converter 로 처리 
  * RabbitMQ 메시지 발송 시 헤더에 `contentType` 을 `text/plain` 으로 지정
   
```java
    public TextPlainMessageConverter() {
        super(new MimeType("text", "plain"));
    }
```


## Multiple Outputs application
[Introduction to Spring Cloud Stream](https://www.baeldung.com/spring-cloud-stream)

* Custom channel sample


```java
public interface MyProcessor {
    String INPUT = "myInput";
    
    @Input                  // #1
    SubscribableChannel myInput();

    @Output("myOutput")     // #2
    MessageChannel anOutput();

    @Output                 // #3
    MessageChannel anotherOutput();
}
```

### 바인딩 채널의 이름 명명 규칙
* 어노테이션으로 이름 지정, 아니면 메소드 이름
    1. input channel 의 이름은 `myInput`
    2. output channel 의 이름은 `myOutput`
    3. output channel 의 이름은 `anotherOutput`