import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import java.util.UUID;


/**
 * 项目的主测试类
 */
@SpringBootTest
class XiangApplicationTests {




    /**
     * 通用测试
     */
    @Test
    public void common() {

    }

    /**
     * 测试文件上传相关
     */
    @Test
    public void upload() {
        String fileName = "hello.jpg";
        String prefix = UUID.randomUUID().toString();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        fileName = prefix + suffix;
        System.out.print("随机生成文件名：" + fileName);
    }

    /**
     * 测试 jedis
     */
    @Test
    public void jedis() {

        // 1. 获取连接
        Jedis jedis = new Jedis("localhost", 6379);

        // 2. 执行操作
        jedis.set("name", "one");
        System.out.println(jedis.get("name"));

        // 3. 关闭连接
        jedis.close();

    }




}




