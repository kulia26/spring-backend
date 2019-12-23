package lab.security;

import lab.OAuth2AuthenticationProcessingException;
import lab.entity.AuthProvider;
import lab.util.GithubOAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {

  public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) throws OAuth2AuthenticationProcessingException {
//    if(registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
//      return new GoogleOAuth2UserInfo(attributes);
//    } else if (registrationId.equalsIgnoreCase(AuthProvider.facebook.toString())) {
//      return new FacebookOAuth2UserInfo(attributes);
//    } else
    if (registrationId.equalsIgnoreCase(AuthProvider.github.toString())) {
      return new GithubOAuth2UserInfo(attributes);
    } else {
      throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
    }
  }
}