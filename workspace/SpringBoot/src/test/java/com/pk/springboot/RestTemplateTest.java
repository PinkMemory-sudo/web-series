package com.pk.springboot;

import com.pk.springboot.model.Area;
import com.pk.springboot.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.xml.ws.soap.Addressing;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * restTemplate测试
 * 四种不同请求的使用
 * 参数的传递
 * 返回值解析
 * 请求头设置
 */
@SpringBootTest
public class RestTemplateTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void simpleGetTest() {
        String url = "http://localhost:8080/rest_template/get_test";
        Map userEntity = restTemplate.getForObject(url, Map.class, "2020-01-01");
        System.out.println(userEntity);
    }

    @Test
    public void getTest() {
        String url = "http://localhost:8080/rest_template/get_test";
        ResponseEntity<UserEntity> responseEntity = restTemplate.getForEntity(url, UserEntity.class, "2020-01-01");
        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void postTest() {
        double zq = 4772.47;
        double xny = 1364.87 + 1242.51 + 663.86 + 437.7;
        double hh = 1048.73 + 843.03 + 129.53;
        double bj = 690.21 + 180.83;
        double yl = 508.79 + 506.66;
        double sum = bj + yl + xny + hh;
//        System.out.println(zq + ", " +zq/sum);
        System.out.println(xny + ", " + xny / sum);
        System.out.println(bj + ", " + bj / sum);
        System.out.println(yl + ", " + yl / sum);
        System.out.println(hh + ", " + hh / sum);
    }

    @Test
    public void getMoney() {
        String str = restTemplate.getForObject("http://hq.sinajs.cn/list=sz300750", String.class);
        String[] split = str.split(",");
        String yes = split[2];
        String now = split[3];
        double i = Double.parseDouble(yes);
        double j = Double.parseDouble(now);
        System.out.println(yes + "," + now + ":" + String.format("%.2f",(j - i)*100 / i)+"%");

    }

    @Test
    public void test() throws Exception {
        try {
            List<Map<String, Object>> openAccountDetail = new ArrayList<>();
            // 构造请求的头信息
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer 5e2d7522-c310-46f3-95f8-3c7437d28301");
            // 构造请求的HTTP实体
            HttpEntity<String> requestEntity = new HttpEntity(headers);
            // 根据请求参数，构造构造GET方法的请求URI.
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://10.208.25.24:8301"
                    + "/mingjing_admin/get_high_risk_account_edition?localDate="+"2021-05-24");
            // 向明镜API发起请求，获得返回
            ResponseEntity<?> responseEntity =
                    restTemplate.exchange(builder.toUriString(), HttpMethod.GET, requestEntity, Object.class);
            // 根据API文档说明的返回结果的类型，获得构造返回的结果
            Map<String, Object> body = (Map<String, Object>) responseEntity.getBody();
            openAccountDetail = (List<Map<String, Object>>) body.get("data");
            List<Area> list=(List<Area>)openAccountDetail.get(0).get("areaList");
            System.out.println(list);
            System.out.println(openAccountDetail);
        } catch (Exception ex) {
            throw new Exception("获得需要推送的客户信息失败：" + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("hello");
        ArrayList<String> strings1 = new ArrayList<>();
        strings1.addAll(strings);
        strings.clear();
        System.out.println(strings1);
        System.out.println(strings);
    }
}
