### Docker compose

Add user and password for rabbit mq in environment section:

```
    environment:
      - RABBITMQ_DEFAULT_USER=********
      - RABBITMQ_DEFAULT_PASS=********
```

#### Docker compose a file

```
version: "2.1"
services:
  rabbitmq:
    image: rabbitmq:4.1.0-management-alpine
    hostname: rabbitmq
    restart: always
    environment:
      - RABBITMQ_DEFAULT_USER=********
      - RABBITMQ_DEFAULT_PASS=********
      - RABBITMQ_SERVER_ADDITIONAL_ERL_ARGS=-rabbit log_levels [{connection,error},{default,error}] disk_free_limit 147483648
    volumes:
      - ./rabbitmq:/var/lib/rabbitmq
    ports:
      - 15672:15672
      - 5672:5672
```

* disk_free_limit 147483648 - Disk space: 147 Mb
* volumes: - ./rabbitmq:/var/lib/rabbitmq
