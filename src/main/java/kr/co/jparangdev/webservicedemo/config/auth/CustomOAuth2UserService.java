package kr.co.jparangdev.webservicedemo.config.auth;

import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import kr.co.jparangdev.webservicedemo.config.auth.dto.OAuthAttributes;
import kr.co.jparangdev.webservicedemo.config.auth.dto.SessionUser;
import kr.co.jparangdev.webservicedemo.domain.user.User;
import kr.co.jparangdev.webservicedemo.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final UserRepository userRepository;
	private final HttpSession httpSession;


	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();

		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

		OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

		User user = saveOrUpdate(attributes);
		httpSession.setAttribute("user", new SessionUser(user));

		return new DefaultOAuth2User(
			Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey()))
			, attributes.getAttributes()
			, attributes.getNameAttributeKey());
	}

	// 구글에서 받아온 유저의 정보가 변경되면 같이 변경되도록 처리
	private User saveOrUpdate(OAuthAttributes attributes) {
		User user = userRepository.findByEmail(attributes.getEmail())
			.map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
			.orElse(attributes.toEntity());

		return userRepository.save(user);
	}
}
