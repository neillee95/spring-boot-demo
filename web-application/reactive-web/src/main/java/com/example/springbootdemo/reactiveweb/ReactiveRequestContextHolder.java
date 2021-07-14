package com.example.springbootdemo.reactiveweb;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.example.springbootdemo.reactiveweb.ReactiveContextWebFilter.KEY;

/**
 * Spring webflux {@link ServerWebExchange} holder.
 * <p>
 * See {@link org.springframework.security.core.context.ReactiveSecurityContextHolder#getContext()}.
 *
 * @author lee
 */
public class ReactiveRequestContextHolder {

    public static Mono<ServerWebExchange> getWebExchange() {
        return Mono.subscriberContext()
            .filter(ctx -> ctx.hasKey(KEY))
            .flatMap(ctx -> Mono.just(ctx.get(KEY)));
    }

}
