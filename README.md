# Tracing logs in to kafka and stream them in ELK with JSON format
 
- run `./mvnw clean install` to build the two Spring Boot applications (`api-service` and `customer-service`)
- run and install `zookeeper`
- run and install `kafka`
- run and install `elastic search`
- install logstash and add this file in main installation path and run it with `..\logstash-8.4.3\bin\logstash.bat -f ..\logstash-8.4.3\logstash.conf`

```yml
input {
    kafka {
            bootstrap_servers => "localhost:9092"
            topics => ["tas-logs"]
    }
} 

filter {
  json {
    source => "message"
  }
}

output {
   elasticsearch {
      hosts => ["127.0.0.1:9200"]
      index => "tas-logs-%{+YYYY.MM.dd}"
      workers => 1
      ssl => true
      ssl_certificate_verification => false
      user => '{YOUR_USERNAME}'
      password => '{YOUR_PASSWORD}'
      codec => "json"
    }
}

```

- run and install `kibana` (optional)
- run services
