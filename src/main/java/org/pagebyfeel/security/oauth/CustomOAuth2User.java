package org.pagebyfeel.security.oauth;

import lombok.Getter;
import org.pagebyfeel.entity.Provider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@Getter
public class CustomOAuth2User implements OAuth2User {

    private final UUID userId;
    private final String email;
    private final String nickname;
    private final String provider;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Map<String, Object> attributes;

    public CustomOAuth2User(UUID userId, String email, String nickname, Provider provider,
                            Collection<? extends GrantedAuthority> authorities,
                            Map<String, Object> attributes) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.provider = provider;
        this.authorities = authorities;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return userId.toString();
    }
}
