package www.exam.janvier.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import www.exam.janvier.filter.SimpleCorsFilter;

import static org.mockito.Mockito.*;

class SimpleCorsFilterTest {

    @InjectMocks
    private SimpleCorsFilter simpleCorsFilter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void doFilter_SetsCorsHeaders() throws Exception {
        when(request.getHeader("origin")).thenReturn("http://example.com");
        when(request.getMethod()).thenReturn("GET");

        simpleCorsFilter.doFilter(request, response, filterChain);

        verify(response).setHeader("Access-Control-Allow-Origin", "http://example.com");
        verify(response).setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        verify(response).setHeader("Access-Control-Max-Age", "3600");
        verify(response).setHeader("Access-Control-Allow-Headers", "*");
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilter_OptionsRequest() throws Exception {
        when(request.getMethod()).thenReturn("OPTIONS");

        simpleCorsFilter.doFilter(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        verifyNoMoreInteractions(filterChain);
    }


}
