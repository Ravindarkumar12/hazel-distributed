package com.springhow.examples.springboot.cache.interceptor;

import io.opentelemetry.api.trace.*;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomTraceInterceptor implements HandlerInterceptor {

    private final Tracer tracer;

    public CustomTraceInterceptor(Tracer tracer) {
        this.tracer = tracer;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String customTraceId = request.getHeader("Custom-Trace-Id");
        String customSpanId = request.getHeader("Custom-Span-Id");

        Span span;
        if (customTraceId != null && customSpanId != null) {
            // Use custom TraceId and SpanId from headers

            span = tracer.spanBuilder("custom-span")
                    .setNoParent()
                    .setSpanKind(Span.Kind.SERVER)
                    .startSpan();
            span.getSpanContext().withTraceId(io.opentelemetry.api.trace.TraceId.fromLowerBase16(customTraceId));
            span.getSpanContext().withSpanId(io.opentelemetry.api.trace.SpanId.fromLowerBase16(customSpanId));
        } else {
            // No custom IDs provided, create a new span
            span = tracer.spanBuilder("default-span").startSpan();
        }

        // Activate the span for the current request
        Scope customScope = span.makeCurrent();
        request.setAttribute("customTraceScope", customScope);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Scope customScope = (Scope) request.getAttribute("customTraceScope");
        if (customScope != null) {
            customScope.close();
        }
    }

    private Span createNewSpan(String traceId) {
        SpanContext remoteContext = SpanContext.create(traceId,SpanId.fromLong(RandomStringUtils.nextLong()),
                TraceFlags.getSampled(),
                TraceState.getDefault());
        return openTelemetry.getTracer("").spanBuilder("TEST_SPAN")
                .setParent(Context.current().with(Span.wrap(remoteContext)))
                .startSpan();
    }
}