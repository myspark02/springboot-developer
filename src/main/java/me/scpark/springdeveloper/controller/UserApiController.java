package me.scpark.springdeveloper.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.scpark.springdeveloper.dto.AddUserRequest;
import me.scpark.springdeveloper.repository.RefreshTokenRepository;
import me.scpark.springdeveloper.service.UserService;
import me.scpark.springdeveloper.util.CookieUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.WebUtils;

@RequiredArgsConstructor
@Controller
public class UserApiController {
    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/user")
    public String signup(AddUserRequest request) {
        userService.save(request);
        return "redirect:/login";
    }

//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        new SecurityContextLogoutHandler().logout(request, response,
//                SecurityContextHolder.getContext().getAuthentication());
//        return "redirect:/login";
//    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        // 1. 브라우저가 보낸 쿠키에서 'refresh_token'을 찾습니다.
        Cookie cookie = WebUtils.getCookie(request, "refresh_token");

        if (cookie != null) {
            String refreshToken = cookie.getValue();

            // 2. DB에서 해당 리프레시 토큰을 찾아 삭제합니다.
            refreshTokenRepository.findByRefreshToken(refreshToken)
                    .ifPresent(refreshTokenRepository::delete);

            // 3. 브라우저의 리프레시 토큰 쿠키의 수명을 0으로 만들어 삭제(무효화)시킵니다.
            CookieUtil.deleteCookie(request, response, "refresh_token");
        }

        // 4. (선택사항) 현재 스프링 시큐리티에 저장된 로그인 정보를 비웁니다.
        SecurityContextHolder.clearContext();

        // 5. 성공적으로 로그아웃 되었음을 프론트엔드에 알립니다.
        return ResponseEntity.ok().build();
    }
}
