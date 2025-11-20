package org.pagebyfeel.security.oauth;

import lombok.RequiredArgsConstructor;
import org.pagebyfeel.entity.user.Provider;
import org.pagebyfeel.entity.user.Role;
import org.pagebyfeel.entity.user.User;
import org.pagebyfeel.repository.UserRepository;
import org.pagebyfeel.security.JwtTokenProvider;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String provider = userRequest.getClientRegistration().getRegistrationId().toUpperCase();

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    assert email != null;
                    return userRepository.save(
                            User.builder()
                                    .email(email)
                                    .provider(Provider.valueOf(provider))
                                    .role(Role.USER)
                                    .nickname(email.split("@")[0])
                                    .build()
                    );
                });

        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());

        return new CustomOAuth2User(
                user.getUserId(),
                user.getEmail(),
                user.getNickname(),
                user.getProvider(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())),
                attributes
        );
    }
}
