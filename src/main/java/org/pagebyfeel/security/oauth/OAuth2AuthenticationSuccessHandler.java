package org.pagebyfeel.security.oauth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pagebyfeel.security.JwtTokenProvider;
import org.pagebyfeel.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;

    @Value("${app.oauth2.authorized-redirect-uri}")
    private String redirectUri;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${jwt.access-token-expiration-minutes}")
    private long accessTokenExpirationMinutes;

    @Value("${jwt.refresh-token-expiration-days}")
    private long refreshTokenExpirationDays;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        if (response.isCommitted()) {
            log.debug("Response has already been committed. Unable to redirect");
            return;
        }

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        // Access Token 생성 (userId와 role만 포함)
        String accessToken = jwtTokenProvider.generateAccessToken(
                oAuth2User.getUserId(),
                oAuth2User.getAuthorities().iterator().next()
                        .getAuthority().replace("ROLE_", "")
        );
        String refreshToken = jwtTokenProvider.generateRefreshToken(oAuth2User.getUserId());

        authService.saveRefreshToken(oAuth2User.getUserId(), refreshToken);

        // 쿠키 만료 시간을 설정값에서 가져와서 사용
        int accessTokenMaxAge = (int) (accessTokenExpirationMinutes * 60);  // 분 → 초
        int refreshTokenMaxAge = (int) (refreshTokenExpirationDays * 24 * 60 * 60);  // 일 → 초
        
        addTokenCookie(response, "accessToken", accessToken, accessTokenMaxAge);
        addTokenCookie(response, "refreshToken", refreshToken, refreshTokenMaxAge);

        log.info("OAuth2 login success for user: {}", oAuth2User.getUserId());

        clearAuthenticationAttributes(request);

        getRedirectStrategy().sendRedirect(request, response, redirectUri);
    }

    private void addTokenCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);

        boolean isProduction = "prod".equals(activeProfile);
        cookie.setSecure(isProduction);

        cookie.setPath("/");
        cookie.setMaxAge(maxAge);

        if (isProduction) {
            cookie.setAttribute("SameSite", "None");
        } else {
            cookie.setAttribute("SameSite", "Lax");
        }

        response.addCookie(cookie);
    }
}