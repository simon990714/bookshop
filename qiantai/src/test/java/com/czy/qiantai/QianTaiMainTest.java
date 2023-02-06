package com.czy.qiantai;

import com.czy.qiantai.entity.Book;
import com.czy.qiantai.entity.Booktype;
import com.czy.qiantai.es.EsBookRepository;
import com.czy.qiantai.mapper.BookMapper;
import com.czy.qiantai.mapper.BooktypeMapper;
import com.czy.qiantai.utils.JwtUtils;
import com.czy.qiantai.vo.EsBook;
import io.jsonwebtoken.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class QianTaiMainTest {

    @Autowired
    private EsBookRepository esBookRepository;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BooktypeMapper booktypeMapper;


    @Test
    void test(){

    }

    String secret = "321sdadsad**)9jh66YHkO)(09JY6%Thgh" ;
    @Test
    void testJWTEncode(){

        JwtBuilder builder = Jwts.builder();

        //header
        builder.setHeaderParam("alg","HS256"); //加密算法
        builder.setHeaderParam("typ","JWT");

        //payload载荷
        builder.setIssuer("CZY");//签发人
        builder.setSubject("蜗牛学员");//面向对象
        long expire = 1*60*1000;
        builder.setExpiration(new Date(new Date().getTime() + expire));//到期时间

        //自定义信息
        builder.claim("currentAccount","czy svp");

        //设置签名
        builder.signWith(SignatureAlgorithm.HS256,secret);

        //输出token
        String token = builder.compact();
        System.out.println(token);
    }

    @Test

    void testParseToken(){
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJDWlkiLCJzdWIiOiLonJfniZvlrablkZgiLCJleHAiOjE2NzUxNjM5MjAsImN1cnJlbnRBY2NvdW50IjoiY3p5IHN2cCJ9.k4N3QUQBVxGFUwUTnHosGLTABOmhe1LO1aZE_qG2kc0";
        JwtParser parser = Jwts.parser();
        Jws<Claims> claimsJws = parser.setSigningKey(secret).parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        Object account = body.get("currentAccount");
        System.out.println(account);
    }

    @Test
    void testJwtUtils(){
        String token = JwtUtils.createToken("123", 1);
        System.out.println(token);
        System.out.println(JwtUtils.parseClaims(token));
        System.out.println(JwtUtils.getAccountWithoutException(token));

    }


    @Autowired
    private JavaMailSender javaMailSender;
    @Test
    void testMail(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("1107064862@qq.com");
        simpleMailMessage.setTo("gfdedjbg@163.com");
        simpleMailMessage.setSubject("这是主题");
        simpleMailMessage.setText("这是内容");
        javaMailSender.send(simpleMailMessage);
    }


    @Test
    void initEsBookList(){
        //读取数据库的book数据
        List<Book> bookList = bookMapper.selectList(null);
        List<EsBook> esBookList = bookList.stream().map(book -> {
            EsBook esBook = new EsBook();
            esBook.setId(book.getId());
            esBook.setName(book.getName());
            esBook.setTypeId(book.getTypeId());

            // 补充bookName
            Booktype booktype = booktypeMapper.selectById(book.getTypeId());
            esBook.setTypeName(booktype.getName());

            esBook.setProvider(book.getProvider());
            esBook.setAuthor(book.getAuthor());
            esBook.setPrice(book.getPrice());
            esBook.setDetail(book.getDetail());
            esBook.setImgsrc(book.getImgsrc());
            esBook.setCollectioncount(book.getCollectioncount());
            esBook.setStorecount(book.getStorecount());
            esBook.setBuycount(book.getBuycount());
            esBook.setReadcount(book.getReadcount());
            esBook.setCreatetime(book.getCreatetime());
            esBook.setUpdatetime(book.getUpdatetime());
            esBook.setState(book.getState());

            return esBook;
        }).collect(Collectors.toList());

        esBookRepository.saveAll(esBookList);
    }


}
