package me.gmz.alter.security;

import me.gmz.alter.repositories.PlaylistRepository;
import me.gmz.alter.repositories.UserRepository;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterRegistrar {
    @Bean
    public AuthenticationFilter authenticationFilter(UserRepository userRepository) {
        return new AuthenticationFilter(userRepository);
    }

    @Bean
    public PlaylistOwnerAuthorizationFilter authorizationFilter(PlaylistRepository playlistRepository) {
        return new PlaylistOwnerAuthorizationFilter(playlistRepository);
    }

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticatorRegistration(AuthenticationFilter authenticationFilter) {
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(authenticationFilter);
        registrationBean.addUrlPatterns("/songs", "/playlists", "/playlists/**", "/users/signout", "/users/delete");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<PlaylistOwnerAuthorizationFilter> playlistAuthorizationRegistration(PlaylistOwnerAuthorizationFilter playlistAuthorizationFilter) {
        FilterRegistrationBean<PlaylistOwnerAuthorizationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(playlistAuthorizationFilter);
        registrationBean.addUrlPatterns("/playlists/**");
        registrationBean.setOrder(2);
        return registrationBean;
    }
}
