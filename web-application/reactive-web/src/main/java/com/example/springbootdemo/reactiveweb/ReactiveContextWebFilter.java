package com.example.springbootdemo.reactiveweb;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * The web filter to put {@link org.springframework.web.server.ServerWebExchange} into subscriber context,
 * in order to get web exchange from {@link ReactiveRequestContextHolder#getWebExchange()} ()}.
 * <p>
 * See also {@link org.springframework.security.web.server.context.ReactorContextWebFilter}
 *
 * @author lee
 */
@Component
public class ReactiveContextWebFilter implements WebFilter {

    public static final Class<ServerWebExchange> KEY = ServerWebExchange.class;

    @Override
    @NonNull
    public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        return chain.filter(exchange)
            .subscriberContext(ctx -> ctx.put(KEY, exchange));
    }

}
