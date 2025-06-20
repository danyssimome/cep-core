package com.pgu.cepcore.config.producer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "spring.kafka.producer.properties")
public class KafkaProducerSpecificValue {

    /**
     * Объем в байтах для каждого отправляемого пакета сообщений
     */
    private Integer batchSize;

    /**
     * Максимальный размер отправляемого производителем запроса
     */
    private Integer maxRequestSize;

    /**
     * В отсутствии возможности отправки пакета сообщений брокеру Kafka (скажем, брокер не работает),
     * продюсер начинает накапливать сообщения в буферной памяти
     */
    private Long bufferMemory;

    /**
     * Параметр времени блокировки производителем функции send() при недоступном брокере
     * и переполненном буфере {@link #bufferMemory buffer.memory}
     */
    private Long maxBlockMs;

    /**
     * Длительность времени ожидания дополнительных сообщений перед отправкой текущего пакета, если он незаполнен до
     * {@link #batchSize batch.size}
     */
    private Long lingerMs;

    /**
     * Время ожидания ответа от сервера при отправке данных. Не включает ожидание повторных попыток
     */
    private Integer requestTimeoutMs;

    /**
     * Время между повторными попытками отправки пакета данных
     */
    private Long retryBackoffMs;

    /**
     * Ограничение времени, которое проходит с момента, когда запись готова к отправке
     * (функция send() возвращается успешно, и запись помещается в пакет), до момента,
     * пока брокер не ответит или клиент не откажется, включая время, затраченное на повторные попытки.
     * <I>deliveryTimeoutMs>={@link #lingerMs linger.ms} + {@link #retryBackoffMs retry.backoff.ms} +
     * {@link #requestTimeoutMs request.timeout.ms}</I>
     */
    private Integer deliveryTimeoutMs;

    /**
     * Количество повторных попыток отправки(может привести к дублированию и неупорядоченности сообщений)
     * Во время отправки двух пакетов в один раздел, при количестве одновременных отправок равных 1, если первый
     * завершается ошибкой и повторяется, а второй завершается успешно, то записи второго могут отображаться первыми,
     * а записи первого еще и могут задублироваться.
     */
    private Integer retries;

    /**
     * Определяет от скольки экземпляров брокера требуется подтверждение получения сообщения
     */
    private String acks;

    /**
     * Гарантирует семантику Exactly Once, ДЕДУБЛИКАЦИЮ.
     * Включенный режим идемпотентности назначает каждому производителю PID, а так же
     * Каждое сообщение получает возрастающий серийный номер (у каждого раздела своя последовательность)
     * <br>
     * <I>PID = number, Sequence number = number;</I>
     * <br>
     * На строне брокера отслеживается наибольшая успешно зафиксированная комбинация PID + SN, когда получен
     * меньший порядковый номер(Сообщение которое уже было записано брокером) он отбрасывается.
     */
    private Boolean enableIdempotence;

    /**
     * Количество пакетов сообщений, которые производитель может отправить брокеру(даже в нескольких потоках), не получая ответов.
     */
    private Integer maxInFlightRequestsPerConnection;
}
