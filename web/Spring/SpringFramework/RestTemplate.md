用来发送同步HTTP请求，提供了常见的请求方式，额外添加了exchange和execute方法。

将要过时，org.springframework.web.reactive.client.WebClient提供了响应式，非阻塞的方法。





httpClient，不支持get的body传参

GET请求

getForObject，getForEntity，响应转为Object或者ResponseEntity



将请求头，参数等封装到HttpEntity



都是调用了execute