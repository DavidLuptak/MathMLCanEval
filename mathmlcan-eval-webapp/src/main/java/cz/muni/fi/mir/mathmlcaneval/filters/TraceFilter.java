package cz.muni.fi.mir.mathmlcaneval.filters;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Order(-500)
@Log4j2
@Component
public class TraceFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain fc)
    throws ServletException, IOException {

    final String trace = UUID.randomUUID().toString();
    log.trace("Generating trace id {}", () -> trace);

    ThreadContext.put("trace-id", trace);
    fc.doFilter(req, res);
    ThreadContext.clearAll();

    log.trace("Trace {} removed from context", () -> trace);
  }
}
