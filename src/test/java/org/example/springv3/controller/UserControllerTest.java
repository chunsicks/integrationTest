package org.example.springv3.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springv3.user.UserRequest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    //매번 띄우기 귀찮으니까 만들어두자
    private ObjectMapper om =  new ObjectMapper();

    @Test
    public void join_test() throws Exception {
        //given
        UserRequest.JoinDTO joinDTO = new UserRequest.JoinDTO();
        joinDTO.setUsername("haha");
        joinDTO.setPassword("1234");
        joinDTO.setEmail("haha@nate.com");

        //json으로 변경
        String requestBody = om.writeValueAsString(joinDTO);
        //System.out.println(requestBody);

        //when
        //실재 테스트 코드   호출해야함 리퀘스트 빌더 타입이 ()안에 들어가야 한다! 리퀘스트 빌더는 인터페이스
        //사용하려면 MockHttpServletRequest   MockMvcReqeustBuilders라고 추상클래스 있다
        //content가 모델 바디데이터 넣으면 된다 통신은 다 스트일
        //말 폼 url  말폼 (더럽다)  그래서 익샙션 처리해라는 거임"/join"dlfjsrj
        ResultActions actions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );


        //eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        //System.out.println(responseBody);

        //then
        actions.andExpect(jsonPath("$.status").value(200));
        actions.andExpect(jsonPath("$.msg").value("성공"));
        actions.andExpect(jsonPath("$.body.id").value(4));
        actions.andExpect(jsonPath("$.body.username").value("haha"));
        actions.andExpect(jsonPath("$.body.email").value("haha@nate.com"));
        actions.andExpect(jsonPath("$.body.profile").isEmpty());

    }
    //로그인 검증
    @Test
    public void login_test() throws Exception {
        //given
        UserRequest.LoginDTO loginDTO = new UserRequest.LoginDTO();
        loginDTO.setUsername("ssar");
        loginDTO.setPassword("1234");

        //json으로 변경
        String requestBody = om.writeValueAsString(loginDTO);
        System.out.println(requestBody);

        //when
        //실재 테스트 코드   호출해야함 리퀘스트 빌더 타입이 ()안에 들어가야 한다! 리퀘스트 빌더는 인터페이스
        //사용하려면 MockHttpServletRequest   MockMvcReqeustBuilders라고 추상클래스 있다
        //content가 모델 바디데이터 넣으면 된다 통신은 다 스트일
        //말 폼 url  말폼 (더럽다)  그래서 익샙션 처리해라는 거임"/join"dlfjsrj
        ResultActions actions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        //우리는 바디 필요 없음 해더에 토큰 있는지
        String responsJwt = actions.andReturn().getResponse().getHeader("Authorization");
        System.out.println(responsJwt);
        //null이 아니라고만 하자! 토큰은
        actions.andExpect(header().string("Authorization", Matchers.notNullValue()));
        actions.andExpect(jsonPath("$.status").value(200));
        actions.andExpect(jsonPath("$.msg").value("성공"));
        actions.andExpect(jsonPath("$.body").isEmpty());
    }
}
