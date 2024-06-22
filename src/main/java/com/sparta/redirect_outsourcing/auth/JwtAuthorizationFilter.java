package com.sparta.redirect_outsourcing.auth;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import com.sparta.redirect_outsourcing.domain.user.repository.UserAdapter;
import com.sparta.redirect_outsourcing.exception.custom.user.TokensException;
import com.sparta.redirect_outsourcing.exception.custom.user.UserException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserAdapter userAdapter;

    public JwtAuthorizationFilter(JwtProvider jwtProvider, UserDetailsServiceImpl userDetailsService,
                                  UserAdapter userAdapter) {
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
        this.userAdapter = userAdapter;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest req, @NonNull HttpServletResponse res,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String uri = req.getRequestURI();
        log.info("Requested URI: {}", uri);

        // 회원가입과 로그인 엔드포인트는 필터링하지 않음
        if (uri.equals("/users/signup") || uri.equals("/users/login")) {
            filterChain.doFilter(req, res);
            return;
        }

        // HTTP 요청 헤더에서 JWT 토큰 값을 가져옴. 요청헤더에서 토큰 추출
        String accessToken = jwtProvider.getAccessTokenFromHeader(req);

        // GET 요청에 대해서는 인증을 요구하지 않음
        if (req.getMethod().equals(HttpMethod.GET.name()) && uri.startsWith("/users/")) {
            filterChain.doFilter(req, res);
            return;
        }

        if (StringUtils.hasText(accessToken)) {
            // 액세스 토큰에서 클레임(사용자 정보)을 추출
            Claims accessTokenClaims = jwtProvider.getUserInfoFromToken(accessToken);
            String username = accessTokenClaims.getSubject();
            User user = userAdapter.findByUsername(username);

            // 유저의 리프레쉬 토큰이 null인 경우
            if (user == null || user.getRefreshToken() == null) {
                handleInvalidTokens();
                return;
            }

            boolean accessTokenValid = jwtProvider.validateToken(accessToken);
            if (accessTokenValid) {
                log.info("handleValidAccessToken");
                handleValidAccessToken(req, res, filterChain, accessToken); // 엑세스 토큰의 유효성을 검증합니다.
            } else {
                // 액세스 토큰이 유효하지 않다면 리프레시 토큰을 통해 액세스 토큰을 재발급 시도
                log.info("handleExpiredAccessToken");
                handleExpiredAccessToken(req, res, filterChain);
            }
        } else {
            handleInvalidTokens();
        }
    }

    // 유효한 Access Token 처리
    private void handleValidAccessToken(HttpServletRequest req, HttpServletResponse res,
                                        FilterChain filterChain, String accessToken)
            throws IOException, ServletException {
        // 액세스 토큰에서 클레임(사용자 정보)을 추출
        Claims accessTokenClaims = jwtProvider.getUserInfoFromToken(accessToken);
        String username = accessTokenClaims.getSubject();

        // 사용자 인증 설정
        setAuthentication(username);
        // 요청 필터링 수행
        filterChain.doFilter(req, res);
    }

    // 액세스 토큰이 만료된 경우 리프레시 토큰을 통해 액세스 토큰을 재발급
    private void handleExpiredAccessToken(HttpServletRequest req, HttpServletResponse res,
                                          FilterChain filterChain) throws IOException, ServletException {
        String refreshToken = jwtProvider.getRefreshTokenFromHeader(req);
        // refreshToken이 null이 아니고 비어 있지 않으며 유효한 텍스트를 포함하고 있는지 확인, 유효성 확인
        if (StringUtils.hasText(refreshToken) && jwtProvider.validateToken(refreshToken)) {
            Claims refreshTokenClaims = jwtProvider.getUserInfoFromToken(refreshToken);
            String username = refreshTokenClaims.getSubject();

            // 데이터베이스에서 리프레시 토큰 확인
            User user = userAdapter.findByUsername(username);
            if (user != null && refreshToken.equals(user.getRefreshToken())) {
                String newAccessToken = jwtProvider.createAccessToken(username);
                res.addHeader(JwtProvider.AUTHORIZATION_HEADER, newAccessToken);
                setAuthentication(username);
                filterChain.doFilter(req, res);
            } else {
                handleInvalidTokens();
            }
        } else {
            handleExpiredRefreshToken();
        }
    }

    // Refresh Token 만료 처리
    private void handleExpiredRefreshToken() {
        log.error("Expired Refresh Token");
        throw new TokensException(ResponseCodeEnum.REFRESH_TOKEN_EXPIRED);
    }

    // 유효하지 않은 토큰 처리
    private void handleInvalidTokens() {
        log.error("Invalid Tokens");
        throw new TokensException(ResponseCodeEnum.INVALID_TOKENS);
    }

    //  인증 객체를 생성하여 SecurityContext에 설정하기 위한 메서드
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성 매서드
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
    }
}
