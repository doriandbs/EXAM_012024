package www.exam.janvier.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import www.exam.janvier.filter.JwtRequestFilter;
import www.exam.janvier.service.CustomUserDetailsService;
import www.exam.janvier.utils.JwtUtil;

import static org.mockito.Mockito.*;

class JwtRequestFilterTest {

    @InjectMocks
    private JwtRequestFilter jwtRequestFilter;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();

    }

    @Test
    void doFilterInternal_BypassCertainURIs() throws Exception {
        when(request.getRequestURI()).thenReturn("/register");

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        Assertions.assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilterInternal_ProcessSecuredURIs() throws Exception {
        when(request.getRequestURI()).thenReturn("/securedEndpoint");
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtUtil.extractNomSociete("token")).thenReturn("societe");
        when(userDetailsService.loadUserByUsername("societe")).thenReturn(userDetails);
        when(jwtUtil.validateToken("token", userDetails)).thenReturn(true);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        Assertions.assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        Assertions.assertTrue(SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken);
    }
}
